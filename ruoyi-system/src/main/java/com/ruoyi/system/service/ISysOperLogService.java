package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysOperLog;

/**
 * 操作日誌 服務層
 * 
 * @author ruoyi
 */
public interface ISysOperLogService
{
    /**
     * 新增操作日誌
     * 
     * @param operLog 操作日誌對象
     */
    public void insertOperlog(SysOperLog operLog);

    /**
     * 查詢系統操作日誌集合
     * 
     * @param operLog 操作日誌對象
     * @return 操作日誌集合
     */
    public List<SysOperLog> selectOperLogList(SysOperLog operLog);

    /**
     * 批次刪除系統操作日誌
     * 
     * @param ids 需要刪除的數據
     * @return 結果
     */
    public int deleteOperLogByIds(String ids);

    /**
     * 查詢操作日誌詳細
     * 
     * @param operId 操作ID
     * @return 操作日誌對象
     */
    public SysOperLog selectOperLogById(Long operId);

    /**
     * 清空操作日誌
     */
    public void cleanOperLog();
}
