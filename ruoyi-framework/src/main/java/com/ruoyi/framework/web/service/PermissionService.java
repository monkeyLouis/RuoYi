package com.ruoyi.framework.web.service;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;

/**
 * RuoYi首創 js調用 thymeleaf 實現按鈕權限可見性
 * 
 * @author ruoyi
 */
@Service("permission")
public class PermissionService
{
    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);

    /** 沒有權限，hidden用於前端隱藏按鈕 */
    public static final String NOACCESS = "hidden";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    /**
     * 驗證用戶是否具備某權限，無權限返回hidden用於前端隱藏（如需返回Boolean使用isPermitted）
     * 
     * @param permission 權限字串
     * @return 用戶是否具備某權限
     */
    public String hasPermi(String permission)
    {
        return isPermitted(permission) ? StringUtils.EMPTY : NOACCESS;
    }

    /**
     * 驗證用戶是否不具備某權限，與 hasPermi邏輯相反。無權限返回hidden用於前端隱藏（如需返回Boolean使用isLacksPermitted）
     *
     * @param permission 權限字串
     * @return 用戶是否不具備某權限
     */
    public String lacksPermi(String permission)
    {
        return isLacksPermitted(permission) ? StringUtils.EMPTY : NOACCESS;
    }

    /**
     * 驗證用戶是否具有以下任意一個權限，無權限返回hidden用於隱藏（如需返回Boolean使用hasAnyPermissions）
     *
     * @param permissions 以 PERMISSION_DELIMETER 為分隔符號的權限列表
     * @return 用戶是否具有以下任意一個權限
     */
    public String hasAnyPermi(String permissions)
    {
        return hasAnyPermissions(permissions, PERMISSION_DELIMETER) ? StringUtils.EMPTY : NOACCESS;
    }

    /**
     * 驗證用戶是否具備某角色，無權限返回hidden用於隱藏（如需返回Boolean使用isRole）
     * 
     * @param role 角色字串
     * @return 用戶是否具備某角色
     */
    public String hasRole(String role)
    {
        return isRole(role) ? StringUtils.EMPTY : NOACCESS;
    }

    /**
     * 驗證用戶是否不具備某角色，與hasRole邏輯相反。無權限返回hidden用於隱藏（如需返回Boolean使用isLacksRole）
     * 
     * @param role 角色字串
     * @return 用戶是否不具備某角色
     */
    public String lacksRole(String role)
    {
        return isLacksRole(role) ? StringUtils.EMPTY : NOACCESS;
    }

    /**
     * 驗證用戶是否具有以下任意一個角色，無權限返回hidden用於隱藏（如需返回Boolean使用isAnyRoles）
     *
     * @param roles 以 ROLE_NAMES_DELIMETER 為分隔符號的角色列表
     * @return 用戶是否具有以下任意一個角色
     */
    public String hasAnyRoles(String roles)
    {
        return isAnyRoles(roles, ROLE_DELIMETER) ? StringUtils.EMPTY : NOACCESS;
    }

    /**
     * 驗證用戶是否認證透過或已記住的用戶。
     *
     * @return 用戶是否認證透過或已記住的用戶
     */
    public boolean isUser()
    {
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.getPrincipal() != null;
    }

    /**
     * 判斷用戶是否擁有某個權限
     * 
     * @param permission 權限字串
     * @return 用戶是否具備某權限
     */
    public boolean isPermitted(String permission)
    {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    /**
     * 判斷用戶是否不具備某權限，與 isPermitted邏輯相反。
     *
     * @param permission 權限名稱
     * @return 用戶是否不具備某權限
     */
    public boolean isLacksPermitted(String permission)
    {
        return isPermitted(permission) != true;
    }

    /**
     * 驗證用戶是否具有以下任意一個權限。
     *
     * @param permissions 以 PERMISSION_DELIMETER 為分隔符號的權限列表
     * @return 用戶是否具有以下任意一個權限
     */
    public boolean hasAnyPermissions(String permissions)
    {
        return hasAnyPermissions(permissions, PERMISSION_DELIMETER);
    }

    /**
     * 驗證用戶是否具有以下任意一個權限。
     *
     * @param permissions 以 delimeter 為分隔符號的權限列表
     * @param delimeter 權限列表分隔符號
     * @return 用戶是否具有以下任意一個權限
     */
    public boolean hasAnyPermissions(String permissions, String delimeter)
    {
        Subject subject = SecurityUtils.getSubject();

        if (subject != null)
        {
            if (delimeter == null || delimeter.length() == 0)
            {
                delimeter = PERMISSION_DELIMETER;
            }

            for (String permission : permissions.split(delimeter))
            {
                if (permission != null && subject.isPermitted(permission.trim()) == true)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 判斷用戶是否擁有某個角色
     * 
     * @param role 角色字串
     * @return 用戶是否具備某角色
     */
    public boolean isRole(String role)
    {
        return SecurityUtils.getSubject().hasRole(role);
    }

    /**
     * 驗證用戶是否不具備某角色，與 isRole邏輯相反。
     *
     * @param role 角色名稱
     * @return 用戶是否不具備某角色
     */
    public boolean isLacksRole(String role)
    {
        return isRole(role) != true;
    }

    /**
     * 驗證用戶是否具有以下任意一個角色。
     *
     * @param roles 以 ROLE_NAMES_DELIMETER 為分隔符號的角色列表
     * @return 用戶是否具有以下任意一個角色
     */
    public boolean isAnyRoles(String roles)
    {
        return isAnyRoles(roles, ROLE_DELIMETER);
    }

    /**
     * 驗證用戶是否具有以下任意一個角色。
     *
     * @param roles 以 delimeter 為分隔符號的角色列表
     * @param delimeter 角色列表分隔符號
     * @return 用戶是否具有以下任意一個角色
     */
    public boolean isAnyRoles(String roles, String delimeter)
    {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null)
        {
            if (delimeter == null || delimeter.length() == 0)
            {
                delimeter = ROLE_DELIMETER;
            }

            for (String role : roles.split(delimeter))
            {
                if (subject.hasRole(role.trim()) == true)
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 返回用戶屬性值
     *
     * @param property 屬性名稱
     * @return 用戶屬性值
     */
    public Object getPrincipalProperty(String property)
    {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null)
        {
            Object principal = subject.getPrincipal();
            try
            {
                BeanInfo bi = Introspector.getBeanInfo(principal.getClass());
                for (PropertyDescriptor pd : bi.getPropertyDescriptors())
                {
                    if (pd.getName().equals(property) == true)
                    {
                        return pd.getReadMethod().invoke(principal, (Object[]) null);
                    }
                }
            }
            catch (Exception e)
            {
                log.error("Error reading property [{}] from principal of type [{}]", property, principal.getClass().getName());
            }
        }
        return null;
    }
}
