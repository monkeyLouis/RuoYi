package com.ruoyi.common.exception.user;

/**
 * 使用者密碼輸入錯誤計數例外
 * 
 * @author ruoyi
 */
public class UserPasswordRetryLimitCountException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount)
    {
        super("user.password.retry.limit.count", new Object[] { retryLimitCount });
    }
}
