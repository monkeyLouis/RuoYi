package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 全局配置類
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "ruoyi")
public class RuoYiConfig
{
    /** 項目名稱 */
    private static String name;

    /** 版本 */
    private static String version;

    /** 版權年份 */
    private static String copyrightYear;

    /** 實例示範開關 */
    private static boolean demoEnabled;

    /** 上傳路徑 */
    private static String profile;

    /** 獲取地址開關 */
    private static boolean addressEnabled;

    public static String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        RuoYiConfig.name = name;
    }

    public static String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        RuoYiConfig.version = version;
    }

    public static String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        RuoYiConfig.copyrightYear = copyrightYear;
    }

    public static boolean isDemoEnabled()
    {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled)
    {
        RuoYiConfig.demoEnabled = demoEnabled;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        RuoYiConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        RuoYiConfig.addressEnabled = addressEnabled;
    }

    /**
     * 獲取導入上傳路徑
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    /**
     * 獲取頭像上傳路徑
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 獲取下載路徑
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 獲取上傳路徑
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
