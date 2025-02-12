package com.ruoyi.common.utils.uuid;

import java.util.concurrent.atomic.AtomicInteger;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * @author ruoyi 序列生成類
 */
public class Seq
{
    // 通用序列類型
    public static final String commSeqType = "COMMON";

    // 上傳序列類型
    public static final String uploadSeqType = "UPLOAD";

    // 通用介面序列數
    private static AtomicInteger commSeq = new AtomicInteger(1);

    // 上傳介面序列數
    private static AtomicInteger uploadSeq = new AtomicInteger(1);

    // 機器標識
    private static final String machineCode = "A";

    /**
     * 獲取通用序號
     * 
     * @return 序列值
     */
    public static String getId()
    {
        return getId(commSeqType);
    }
    
    /**
     * 默認16位序號 yyMMddHHmmss + 一位機器標識 + 3長度循環遞增字串
     * 
     * @return 序列值
     */
    public static String getId(String type)
    {
        AtomicInteger atomicInt = commSeq;
        if (uploadSeqType.equals(type))
        {
            atomicInt = uploadSeq;
        }
        return getId(atomicInt, 3);
    }

    /**
     * 通用介面序號 yyMMddHHmmss + 一位機器標識 + length長度循環遞增字串
     * 
     * @param atomicInt 序列數
     * @param length 數值長度
     * @return 序列值
     */
    public static String getId(AtomicInteger atomicInt, int length)
    {
        String result = DateUtils.dateTimeNow();
        result += machineCode;
        result += getSeq(atomicInt, length);
        return result;
    }

    /**
     * 序列循環遞增字串[1, 10 的 (length)冪次方), 用0左補齊length位數
     * 
     * @return 序列值
     */
    private synchronized static String getSeq(AtomicInteger atomicInt, int length)
    {
        // 先取值再+1
        int value = atomicInt.getAndIncrement();

        // 如果更新後值>=10 的 (length)冪次方則重設為1
        int maxSeq = (int) Math.pow(10, length);
        if (atomicInt.get() >= maxSeq)
        {
            atomicInt.set(1);
        }
        // 轉字串，用0左補齊
        return StringUtils.padl(value, length);
    }
}
