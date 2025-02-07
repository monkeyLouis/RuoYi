package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysConfig;

/**
 * 參數配置 服務層
 * 
 * @author ruoyi
 */
public interface ISysConfigService
{
    /**
     * 查詢參數配置資訊
     * 
     * @param configId 參數配置ID
     * @return 參數配置資訊
     */
    public SysConfig selectConfigById(Long configId);

    /**
     * 根據鍵名查詢參數配置資訊
     * 
     * @param configKey 參數鍵名
     * @return 參數鍵值
     */
    public String selectConfigByKey(String configKey);

    /**
     * 查詢參數配置列表
     * 
     * @param config 參數配置資訊
     * @return 參數配置集合
     */
    public List<SysConfig> selectConfigList(SysConfig config);

    /**
     * 新增參數配置
     * 
     * @param config 參數配置資訊
     * @return 結果
     */
    public int insertConfig(SysConfig config);

    /**
     * 修改參數配置
     * 
     * @param config 參數配置資訊
     * @return 結果
     */
    public int updateConfig(SysConfig config);

    /**
     * 批次刪除參數配置資訊
     * 
     * @param ids 需要刪除的數據ID
     */
    public void deleteConfigByIds(String ids);

    /**
     * 載入參數快取數據
     */
    public void loadingConfigCache();

    /**
     * 清空參數快取數據
     */
    public void clearConfigCache();

    /**
     * 重設參數快取數據
     */
    public void resetConfigCache();

    /**
     * 校驗參數鍵名是否唯一
     * 
     * @param config 參數資訊
     * @return 結果
     */
    public boolean checkConfigKeyUnique(SysConfig config);
}
