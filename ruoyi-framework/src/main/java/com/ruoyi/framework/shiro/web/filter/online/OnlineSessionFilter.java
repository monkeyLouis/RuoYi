package com.ruoyi.framework.shiro.web.filter.online;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import com.ruoyi.common.constant.ShiroConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.OnlineStatus;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.framework.shiro.session.OnlineSession;
import com.ruoyi.framework.shiro.session.OnlineSessionDAO;

/**
 * 自訂訪問控制
 * 
 * @author ruoyi
 */
public class OnlineSessionFilter extends AccessControlFilter
{
    /**
     * 強制退出後重定向的地址
     */
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    private OnlineSessionDAO onlineSessionDAO;

    /**
     * 表示是否允許訪問；mappedValue就是[urls]配置中攔截器參數部分，如果允許訪問返回true，否則false；
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception
    {
        Subject subject = getSubject(request, response);
        if (subject == null || subject.getSession() == null)
        {
            return true;
        }
        Session session = onlineSessionDAO.readSession(subject.getSession().getId());
        if (session != null && session instanceof OnlineSession)
        {
            OnlineSession onlineSession = (OnlineSession) session;
            request.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
            // 把user對象設置進去
            boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == 0L;
            if (isGuest == true)
            {
                SysUser user = ShiroUtils.getSysUser();
                if (user != null)
                {
                    onlineSession.setUserId(user.getUserId());
                    onlineSession.setLoginName(user.getLoginName());
					onlineSession.setAvatar(user.getAvatar());
                    onlineSession.setDeptName(user.getDept().getDeptName());
                    onlineSession.markAttributeChanged();
                }
            }

            if (onlineSession.getStatus() == OnlineStatus.off_line)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 表示當訪問拒絕時是否已經處理了；如果返回true表示需要繼續處理；如果返回false表示該攔截器實例已經處理了，將直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
    {
        Subject subject = getSubject(request, response);
        if (subject != null)
        {
            subject.logout();
        }
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    // 跳轉到登入頁
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException
    {
        WebUtils.issueRedirect(request, response, loginUrl);
    }

    public void setOnlineSessionDAO(OnlineSessionDAO onlineSessionDAO)
    {
        this.onlineSessionDAO = onlineSessionDAO;
    }
}
