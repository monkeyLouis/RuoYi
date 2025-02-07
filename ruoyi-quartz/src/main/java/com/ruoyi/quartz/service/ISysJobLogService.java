package com.ruoyi.quartz.service;

import java.util.List;
import com.ruoyi.quartz.domain.SysJobLog;

/**
 * 定時任務調度日誌資訊資訊 服務層
 * 
 * @author ruoyi
 */
public interface ISysJobLogService
{
    /**
     * 獲取quartz調度器日誌的計劃任務
     * 
     * @param jobLog 調度日誌資訊
     * @return 調度任務日誌集合
     */
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog);

    /**
     * 通過調度任務日誌ID查詢調度資訊
     * 
     * @param jobLogId 調度任務日誌ID
     * @return 調度任務日誌對象資訊
     */
    public SysJobLog selectJobLogById(Long jobLogId);

    /**
     * 新增任務日誌
     * 
     * @param jobLog 調度日誌資訊
     */
    public void addJobLog(SysJobLog jobLog);

    /**
     * 批次刪除調度日誌資訊
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     */
    public int deleteJobLogByIds(String ids);

    /**
     * 刪除任務日誌
     * 
     * @param jobId 調度日誌ID
     * @return 結果
     */
    public int deleteJobLogById(Long jobId);
    
    /**
     * 清空任務日誌
     */
    public void cleanJobLog();
}
