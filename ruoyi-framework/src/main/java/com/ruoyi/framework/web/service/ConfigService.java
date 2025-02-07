package com.ruoyi.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.service.ISysConfigService;

/**
 * RuoYi首創 html調用 thymeleaf 實現參數管理
 * 
 * @author ruoyi
 */
@Service("config")
public class ConfigService
{
    @Autowired
    private ISysConfigService configService;

    /**
     * 根據鍵名查詢參數配置資訊
     * 
     * @param configKey 參數鍵名
     * @return 參數鍵值
     */
    public String getKey(String configKey)
    {
        return configService.selectConfigByKey(configKey);
    }
}
