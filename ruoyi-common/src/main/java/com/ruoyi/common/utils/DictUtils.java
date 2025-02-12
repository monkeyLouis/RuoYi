package com.ruoyi.common.utils;

import java.util.List;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;

/**
 * 字典工具類
 * 
 * @author ruoyi
 */
@Component
public class DictUtils
{
    /**
     * 分隔符號
     */
    public static final String SEPARATOR = ",";

    /**
     * 設置字典快取
     * 
     * @param key 參數鍵
     * @param dictDatas 字典數據列表
     */
    public static void setDictCache(String key, List<SysDictData> dictDatas)
    {
        CacheUtils.put(getCacheName(), getCacheKey(key), dictDatas);
    }

    /**
     * 獲取字典快取
     * 
     * @param key 參數鍵
     * @return dictDatas 字典數據列表
     */
    public static List<SysDictData> getDictCache(String key)
    {
        Object cacheObj = CacheUtils.get(getCacheName(), getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj))
        {
            return StringUtils.cast(cacheObj);
        }
        return null;
    }

    /**
     * 根據字典類型和字典值獲取字典標籤
     * 
     * @param dictType 字典類型
     * @param dictValue 字典值
     * @return 字典標籤
     */
    public static String getDictLabel(String dictType, String dictValue)
    {
        if (StringUtils.isEmpty(dictValue))
        {
            return StringUtils.EMPTY;
        }
        return getDictLabel(dictType, dictValue, SEPARATOR);
    }

    /**
     * 根據字典類型和字典標籤獲取字典值
     * 
     * @param dictType 字典類型
     * @param dictLabel 字典標籤
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel)
    {
        if (StringUtils.isEmpty(dictLabel))
        {
            return StringUtils.EMPTY;
        }
        return getDictValue(dictType, dictLabel, SEPARATOR);
    }

    /**
     * 根據字典類型和字典值獲取字典標籤
     * 
     * @param dictType 字典類型
     * @param dictValue 字典值
     * @param separator 分隔符號
     * @return 字典標籤
     */
    public static String getDictLabel(String dictType, String dictValue, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);
        if (StringUtils.isNull(datas))
        {
            return StringUtils.EMPTY;
        }
        if (StringUtils.containsAny(dictValue, separator))
        {
            for (SysDictData dict : datas)
            {
                for (String value : dictValue.split(separator))
                {
                    if (value.equals(dict.getDictValue()))
                    {
                        propertyString.append(dict.getDictLabel()).append(separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (SysDictData dict : datas)
            {
                if (dictValue.equals(dict.getDictValue()))
                {
                    return dict.getDictLabel();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 根據字典類型和字典標籤獲取字典值
     * 
     * @param dictType 字典類型
     * @param dictLabel 字典標籤
     * @param separator 分隔符號
     * @return 字典值
     */
    public static String getDictValue(String dictType, String dictLabel, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);
        if (StringUtils.isNull(datas))
        {
            return StringUtils.EMPTY;
        }
        if (StringUtils.containsAny(dictLabel, separator))
        {
            for (SysDictData dict : datas)
            {
                for (String label : dictLabel.split(separator))
                {
                    if (label.equals(dict.getDictLabel()))
                    {
                        propertyString.append(dict.getDictValue()).append(separator);
                        break;
                    }
                }
            }
        }
        else
        {
            for (SysDictData dict : datas)
            {
                if (dictLabel.equals(dict.getDictLabel()))
                {
                    return dict.getDictValue();
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 根據字典類型獲取字典所有值
     *
     * @param dictType 字典類型
     * @return 字典值
     */
    public static String getDictValues(String dictType)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);
        if (StringUtils.isNull(datas))
        {
            return StringUtils.EMPTY;
        }
        for (SysDictData dict : datas)
        {
            propertyString.append(dict.getDictValue()).append(SEPARATOR);
        }
        return StringUtils.stripEnd(propertyString.toString(), SEPARATOR);
    }

    /**
     * 根據字典類型獲取字典所有標籤
     *
     * @param dictType 字典類型
     * @return 字典值
     */
    public static String getDictLabels(String dictType)
    {
        StringBuilder propertyString = new StringBuilder();
        List<SysDictData> datas = getDictCache(dictType);
        if (StringUtils.isNull(datas))
        {
            return StringUtils.EMPTY;
        }
        for (SysDictData dict : datas)
        {
            propertyString.append(dict.getDictLabel()).append(SEPARATOR);
        }
        return StringUtils.stripEnd(propertyString.toString(), SEPARATOR);
    }

    /**
     * 刪除指定字典快取
     * 
     * @param key 字典鍵
     */
    public static void removeDictCache(String key)
    {
        CacheUtils.remove(getCacheName(), getCacheKey(key));
    }

    /**
     * 清空字典快取
     */
    public static void clearDictCache()
    {
        CacheUtils.removeAll(getCacheName());
    }

    /**
     * 獲取cache name
     * 
     * @return 快取名
     */
    public static String getCacheName()
    {
        return Constants.SYS_DICT_CACHE;
    }

    /**
     * 設置cache key
     * 
     * @param configKey 參數鍵
     * @return 快取鍵key
     */
    public static String getCacheKey(String configKey)
    {
        return Constants.SYS_DICT_KEY + configKey;
    }
}
