package com.ruoyi.framework.shiro.web.filter.sync;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.filter.PathMatchingFilter;
import com.ruoyi.common.constant.ShiroConstants;
import com.ruoyi.framework.shiro.session.OnlineSession;
import com.ruoyi.framework.shiro.session.OnlineSessionDAO;

/**
 * 同步Session數據到Db
 * 
 * @author ruoyi
 */
public class SyncOnlineSessionFilter extends PathMatchingFilter
{
    private OnlineSessionDAO onlineSessionDAO;

    /**
     * 同步會話數據到DB 一次請求最多同步一次 防止過多處理 需要放到Shiro過濾器之前
     */
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception
    {
        OnlineSession session = (OnlineSession) request.getAttribute(ShiroConstants.ONLINE_SESSION);
        // 如果session stop了 也不同步
        // session停止時間，如果stopTimestamp不為null，則代表已停止
        if (session != null && session.getUserId() != null && session.getStopTimestamp() == null)
        {
            onlineSessionDAO.syncToDb(session);
        }
        return true;
    }

    public void setOnlineSessionDAO(OnlineSessionDAO onlineSessionDAO)
    {
        this.onlineSessionDAO = onlineSessionDAO;
    }
}
