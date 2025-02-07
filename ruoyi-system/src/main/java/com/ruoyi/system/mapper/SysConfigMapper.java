package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysConfig;

/**
 * 參數配置 數據層
 * 
 * @author ruoyi
 */
public interface SysConfigMapper
{
    /**
     * 查詢參數配置資訊
     * 
     * @param config 參數配置資訊
     * @return 參數配置資訊
     */
    public SysConfig selectConfig(SysConfig config);

    /**
     * 通過ID查詢配置
     * 
     * @param configId 參數ID
     * @return 參數配置資訊
     */
    public SysConfig selectConfigById(Long configId);

    /**
     * 查詢參數配置列表
     * 
     * @param config 參數配置資訊
     * @return 參數配置集合
     */
    public List<SysConfig> selectConfigList(SysConfig config);

    /**
     * 根據鍵名查詢參數配置資訊
     * 
     * @param configKey 參數鍵名
     * @return 參數配置資訊
     */
    public SysConfig checkConfigKeyUnique(String configKey);

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
     * 刪除參數配置
     * 
     * @param configId 參數主鍵
     * @return 結果
     */
    public int deleteConfigById(Long configId);

    /**
     * 批次刪除參數配置
     * 
     * @param configIds 需要刪除的數據ID
     * @return 結果
     */
    public int deleteConfigByIds(String[] configIds);
}