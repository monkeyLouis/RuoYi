package com.ruoyi.common.exception.user;

/**
 * 使用者被阻擋 例外
 * 
 * @author ruoyi
 */
public class UserBlockedException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserBlockedException()
    {
        super("user.blocked", null);
    }
}
