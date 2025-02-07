package com.ruoyi.framework.aspectj;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import com.ruoyi.common.core.context.PermissionContextHolder;
import com.ruoyi.common.utils.StringUtils;

/**
 * 自訂權限攔截器，將權限字串放到當前請求中以便用於多個角色匹配符合要求的權限
 * 
 * @author ruoyi
 */
@Aspect
@Component
public class PermissionsAspect
{
    @Before("@annotation(controllerRequiresPermissions)")
    public void doBefore(JoinPoint point, RequiresPermissions controllerRequiresPermissions) throws Throwable
    {
        handleRequiresPermissions(point, controllerRequiresPermissions);
    }

    protected void handleRequiresPermissions(final JoinPoint joinPoint, RequiresPermissions requiresPermissions)
    {
        PermissionContextHolder.setContext(StringUtils.join(requiresPermissions.value(), ","));
    }
}
