package com.ruoyi.quartz.domain;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.quartz.util.CronUtils;

/**
 * 定時任務調度表 sys_job
 * 
 * @author ruoyi
 */
public class SysJob extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 任務ID */
    @Excel(name = "任務序號", cellType = ColumnType.NUMERIC)
    private Long jobId;

    /** 任務名稱 */
    @Excel(name = "任務名稱")
    private String jobName;

    /** 任務組名 */
    @Excel(name = "任務組名")
    private String jobGroup;

    /** 調用目標字串 */
    @Excel(name = "調用目標字串")
    private String invokeTarget;

    /** cron執行表達式 */
    @Excel(name = "執行表達式 ")
    private String cronExpression;

    /** cron計劃策略 */
    @Excel(name = "計劃策略 ", readConverterExp = "0=默認,1=立即觸發執行,2=觸發一次執行,3=不觸發立即執行")
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    /** 是否並發執行（0允許 1禁止） */
    @Excel(name = "並發執行", readConverterExp = "0=允許,1=禁止")
    private String concurrent;

    /** 任務狀態（0正常 1暫停） */
    @Excel(name = "任務狀態", readConverterExp = "0=正常,1=暫停")
    private String status;

    public Long getJobId()
    {
        return jobId;
    }

    public void setJobId(Long jobId)
    {
        this.jobId = jobId;
    }

    @NotBlank(message = "任務名稱不能為空")
    @Size(min = 0, max = 64, message = "任務名稱不能超過64個字元")
    public String getJobName()
    {
        return jobName;
    }

    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    public String getJobGroup()
    {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup)
    {
        this.jobGroup = jobGroup;
    }

    @NotBlank(message = "調用目標字串不能為空")
    @Size(min = 0, max = 1000, message = "調用目標字串長度不能超過500個字元")
    public String getInvokeTarget()
    {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget)
    {
        this.invokeTarget = invokeTarget;
    }

    @NotBlank(message = "Cron執行表達式不能為空")
    @Size(min = 0, max = 255, message = "Cron執行表達式不能超過255個字元")
    public String getCronExpression()
    {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression)
    {
        this.cronExpression = cronExpression;
    }

    public Date getNextValidTime()
    {
        if (StringUtils.isNotEmpty(cronExpression))
        {
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }

    public String getMisfirePolicy()
    {
        return misfirePolicy;
    }

    public void setMisfirePolicy(String misfirePolicy)
    {
        this.misfirePolicy = misfirePolicy;
    }

    public String getConcurrent()
    {
        return concurrent;
    }

    public void setConcurrent(String concurrent)
    {
        this.concurrent = concurrent;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("jobId", getJobId())
            .append("jobName", getJobName())
            .append("jobGroup", getJobGroup())
            .append("cronExpression", getCronExpression())
            .append("nextValidTime", getNextValidTime())
            .append("misfirePolicy", getMisfirePolicy())
            .append("concurrent", getConcurrent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}