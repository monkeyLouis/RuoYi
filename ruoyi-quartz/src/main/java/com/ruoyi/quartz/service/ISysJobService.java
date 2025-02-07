package com.ruoyi.quartz.service;

import java.util.List;
import org.quartz.SchedulerException;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.quartz.domain.SysJob;

/**
 * 定時任務調度資訊資訊 服務層
 * 
 * @author ruoyi
 */
public interface ISysJobService
{
    /**
     * 獲取quartz調度器的計劃任務
     * 
     * @param job 調度資訊
     * @return 調度任務集合
     */
    public List<SysJob> selectJobList(SysJob job);

    /**
     * 通過調度任務ID查詢調度資訊
     * 
     * @param jobId 調度任務ID
     * @return 調度任務對象資訊
     */
    public SysJob selectJobById(Long jobId);

    /**
     * 暫停任務
     * 
     * @param job 調度資訊
     * @return 結果
     */
    public int pauseJob(SysJob job) throws SchedulerException;

    /**
     * 恢復任務
     * 
     * @param job 調度資訊
     * @return 結果
     */
    public int resumeJob(SysJob job) throws SchedulerException;

    /**
     * 刪除任務後，所對應的trigger也將被刪除
     * 
     * @param job 調度資訊
     * @return 結果
     */
    public int deleteJob(SysJob job) throws SchedulerException;

    /**
     * 批次刪除調度資訊
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     */
    public void deleteJobByIds(String ids) throws SchedulerException;

    /**
     * 任務調度狀態修改
     * 
     * @param job 調度資訊
     * @return 結果
     */
    public int changeStatus(SysJob job) throws SchedulerException;

    /**
     * 立即運行任務
     * 
     * @param job 調度資訊
     * @return 結果
     */
    public boolean run(SysJob job) throws SchedulerException;

    /**
     * 新增任務
     * 
     * @param job 調度資訊
     * @return 結果
     */
    public int insertJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 更新任務
     * 
     * @param job 調度資訊
     * @return 結果
     */
    public int updateJob(SysJob job) throws SchedulerException, TaskException;

    /**
     * 校驗cron表達式是否有效
     * 
     * @param cronExpression 表達式
     * @return 結果
     */
    public boolean checkCronExpressionIsValid(String cronExpression);
}