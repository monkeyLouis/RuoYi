package com.ruoyi.framework.shiro.web.filter.kickout;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.constant.ShiroConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ShiroUtils;

/**
 * 登入帳號控制過濾器
 * 
 * @author ruoyi
 */
public class KickoutSessionFilter extends AccessControlFilter
{
    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 同一個用戶最大會話數
     **/
    private int maxSession = -1;

    /**
     * 踢出之前登錄的/之後登錄的用戶 默認false踢出之前登錄的用戶
     **/
    private Boolean kickoutAfter = false;

    /**
     * 踢出後到的地址
     **/
    private String kickoutUrl;

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o)
            throws Exception
    {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered() || maxSession == -1)
        {
            // 如果沒有登錄或用戶最大會話數為-1，直接進行之後的流程
            return true;
        }
        try
        {
            Session session = subject.getSession();
            // 當前登錄用戶
            SysUser user = ShiroUtils.getSysUser();
            String loginName = user.getLoginName();
            Serializable sessionId = session.getId();

            // 讀取快取用戶 沒有就存入
            Deque<Serializable> deque = cache.get(loginName);
            if (deque == null)
            {
                // 初始化隊列
                deque = new ArrayDeque<Serializable>();
            }

            // 如果隊列裡沒有此sessionId，且用戶沒有被踢出；放入隊列
            if (!deque.contains(sessionId) && session.getAttribute("kickout") == null)
            {
                // 將sessionId存入隊列
                deque.push(sessionId);
                // 將用戶的sessionId隊列快取
                cache.put(loginName, deque);
            }

            // 如果隊列裡的sessionId數超出最大會話數，開始踢人
            while (deque.size() > maxSession)
            {
                // 是否踢出後來登錄的，預設是false；即後者登錄的用戶踢出前者登錄的用戶；
                Serializable kickoutSessionId = kickoutAfter ? deque.removeFirst() : deque.removeLast();
                // 踢出後再更新下快取隊列
                cache.put(loginName, deque);

                try
                {
                    // 獲取被踢出的sessionId的session對象
                    Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
                    if (null != kickoutSession)
                    {
                        // 設置會話的kickout屬性表示踢出了
                        kickoutSession.setAttribute("kickout", true);
                    }
                }
                catch (Exception e)
                {
                    // 面對異常，我們選擇忽略
                }
            }

            // 如果被踢出了，(前者或後者)直接退出，重定向到踢出後的地址
            if (session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout") == true)
            {
                // 退出登錄
                subject.logout();
                saveRequest(request);
                return isAjaxResponse(request, response);
            }
            return true;
        }
        catch (Exception e)
        {
            return isAjaxResponse(request, response);
        }
    }

    private boolean isAjaxResponse(ServletRequest request, ServletResponse response) throws IOException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (ServletUtils.isAjaxRequest(req))
        {
            AjaxResult ajaxResult = AjaxResult.error("您已在別處登錄，請您修改密碼或重新登入");
            ServletUtils.renderString(res, objectMapper.writeValueAsString(ajaxResult));
        }
        else
        {
            WebUtils.issueRedirect(request, response, kickoutUrl);
        }
        return false;
    }

    public void setMaxSession(int maxSession)
    {
        this.maxSession = maxSession;
    }

    public void setKickoutAfter(boolean kickoutAfter)
    {
        this.kickoutAfter = kickoutAfter;
    }

    public void setKickoutUrl(String kickoutUrl)
    {
        this.kickoutUrl = kickoutUrl;
    }

    public void setSessionManager(SessionManager sessionManager)
    {
        this.sessionManager = sessionManager;
    }

    // 設置Cache的key的前綴
    public void setCacheManager(CacheManager cacheManager)
    {
        // 必須和ehcache快取配置中的快取name一致
        this.cache = cacheManager.getCache(ShiroConstants.SYS_USERCACHE);
    }
}