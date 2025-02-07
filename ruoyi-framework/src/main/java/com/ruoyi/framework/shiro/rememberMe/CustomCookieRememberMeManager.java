package com.ruoyi.framework.shiro.rememberMe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.shiro.service.SysLoginService;

/**
 * 自訂CookieRememberMeManager
 *
 * @author ruoyi
 */
public class CustomCookieRememberMeManager extends CookieRememberMeManager
{
    /**
     * 記住我時去掉角色的permissions權限字串，防止http請求頭過大。
     */
    @Override
    protected void rememberIdentity(Subject subject, PrincipalCollection principalCollection)
    {
        Map<SysRole, Set<String>> rolePermissions = new HashMap<>();
        // 清除角色的permissions權限字串
        for (Object principal : principalCollection)
        {
            if (principal instanceof SysUser)
            {
                List<SysRole> roles = ((SysUser) principal).getRoles();
                for (SysRole role : roles)
                {
                    rolePermissions.put(role, role.getPermissions());
                    role.setPermissions(null);
                }
            }
        }
        byte[] bytes = convertPrincipalsToBytes(principalCollection);
        // 恢復角色的permissions權限字串
        for (Object principal : principalCollection)
        {
            if (principal instanceof SysUser)
            {
                List<SysRole> roles = ((SysUser) principal).getRoles();
                for (SysRole role : roles)
                {
                    role.setPermissions(rolePermissions.get(role));
                }
            }
        }
        rememberSerializedIdentity(subject, bytes);
    }

    /**
     * 取記住我身份時恢復角色permissions權限字串。
     */
    @Override
    public PrincipalCollection getRememberedPrincipals(SubjectContext subjectContext)
    {
        PrincipalCollection principals = super.getRememberedPrincipals(subjectContext);
        if (principals == null || principals.isEmpty())
        {
            return principals;
        }
        for (Object principal : principals)
        {
            if (principal instanceof SysUser)
            {
                SpringUtils.getBean(SysLoginService.class).setRolePermission((SysUser) principal);
            }
        }
        return principals;
    }
}
