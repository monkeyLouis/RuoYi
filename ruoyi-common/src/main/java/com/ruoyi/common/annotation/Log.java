package com.ruoyi.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.OperatorType;

/**
 * 自訂操作日誌記錄註解
 * 
 * @author ruoyi
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 模組
     */
    public String title() default "";

    /**
     * 功能
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人類別
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存請求的參數
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存響應的參數
     */
    public boolean isSaveResponseData() default true;

    /**
     * 排除指定的請求參數
     */
    public String[] excludeParamNames() default {};
}
