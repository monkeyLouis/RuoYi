package com.ruoyi.common.exception.user;

/**
 * 角色已鎖定例外
 * 
 * @author ruoyi
 */
public class RoleBlockedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public RoleBlockedException()
    {
        super("role.blocked", null);
    }
}
