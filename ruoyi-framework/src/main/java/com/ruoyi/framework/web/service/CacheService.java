package com.ruoyi.framework.web.service;

import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.CacheUtils;

/**
 * 快取操作處理
 * 
 * @author ruoyi
 */
@Service
public class CacheService
{
    /**
     * 獲取所有快取名稱
     * 
     * @return 快取列表
     */
    public String[] getCacheNames()
    {
        String[] cacheNames = CacheUtils.getCacheNames();
        return ArrayUtils.removeElement(cacheNames, Constants.SYS_AUTH_CACHE);
    }

    /**
     * 根據快取名稱獲取所有鍵名
     * 
     * @param cacheName 快取名稱
     * @return 鍵名列表
     */
    public Set<String> getCacheKeys(String cacheName)
    {
        return new TreeSet<>(CacheUtils.getCache(cacheName).keys());
    }

    /**
     * 根據快取名稱和鍵名獲取內容值
     * 
     * @param cacheName 快取名稱
     * @param cacheKey 鍵名
     * @return 鍵值
     */
    public Object getCacheValue(String cacheName, String cacheKey)
    {
        return CacheUtils.get(cacheName, cacheKey);
    }

    /**
     * 根據名稱刪除快取資訊
     * 
     * @param cacheName 快取名稱
     */
    public void clearCacheName(String cacheName)
    {
        CacheUtils.removeAll(cacheName);
    }

    /**
     * 根據名稱和鍵名刪除快取資訊
     * 
     * @param cacheName 快取名稱
     * @param cacheKey 鍵名
     */
    public void clearCacheKey(String cacheName, String cacheKey)
    {
        CacheUtils.remove(cacheName, cacheKey);
    }

    /**
     * 清理所有快取
     */
    public void clearAll()
    {
        String[] cacheNames = getCacheNames();
        for (String cacheName : cacheNames)
        {
            CacheUtils.removeAll(cacheName);
        }
    }
}
