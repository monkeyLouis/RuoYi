package com.ruoyi.framework.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import com.ruoyi.framework.shiro.realm.UserRealm;

/**
 * 用戶授權資訊
 * 
 * @author ruoyi
 */
public class AuthorizationUtils
{
    /**
     * 清理所有用戶授權資訊快取
     */
    public static void clearAllCachedAuthorizationInfo()
    {
        getUserRealm().clearAllCachedAuthorizationInfo();
    }

    /**
     * 獲取自訂Realm
     */
    public static UserRealm getUserRealm()
    {
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        return (UserRealm) rsm.getRealms().iterator().next();
    }
}
