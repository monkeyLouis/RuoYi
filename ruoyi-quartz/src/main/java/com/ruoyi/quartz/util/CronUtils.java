package com.ruoyi.quartz.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.quartz.CronExpression;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import com.ruoyi.common.utils.DateUtils;

/**
 * cron表達式工具類
 * 
 * @author ruoyi
 *
 */
public class CronUtils
{
    /**
     * 返回一個布林值代表一個給定的Cron表達式的有效性
     *
     * @param cronExpression Cron表達式
     * @return boolean 表達式是否有效
     */
    public static boolean isValid(String cronExpression)
    {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * 返回一個字串值,表示該消息無效Cron表達式給出有效性
     *
     * @param cronExpression Cron表達式
     * @return String 無效時返回表達式錯誤描述,如果有效返回null
     */
    public static String getInvalidMessage(String cronExpression)
    {
        try
        {
            new CronExpression(cronExpression);
            return null;
        }
        catch (ParseException pe)
        {
            return pe.getMessage();
        }
    }

    /**
     * 返回下一個執行時間根據給定的Cron表達式
     *
     * @param cronExpression Cron表達式
     * @return Date 下次Cron表達式執行時間
     */
    public static Date getNextExecution(String cronExpression)
    {
        try
        {
            CronExpression cron = new CronExpression(cronExpression);
            return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 通過表達式獲取近10次的執行時間
     * 
     * @param cron 表達式
     * @return 時間列表
     */
    public static List<String> getRecentTriggerTime(String cron)
    {
        List<String> list = new ArrayList<String>();
        try
        {
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
            cronTriggerImpl.setCronExpression(cron);
            List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 10);
            for (Date date : dates)
            {
                list.add(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, date));
            }
        }
        catch (ParseException e)
        {
            return null;
        }
        return list;
    }
}
