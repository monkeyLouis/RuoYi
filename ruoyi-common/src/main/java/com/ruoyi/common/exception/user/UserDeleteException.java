package com.ruoyi.common.exception.user;

/**
 * 使用者帳號已被刪除例外
 * 
 * @author ruoyi
 */
public class UserDeleteException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserDeleteException()
    {
        super("user.password.delete", null);
    }
}
