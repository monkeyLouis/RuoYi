package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysLogininfor;

/**
 * 系統訪問日誌情況資訊 數據層
 * 
 * @author ruoyi
 */
public interface SysLogininforMapper
{
    /**
     * 新增系統登錄日誌
     * 
     * @param logininfor 訪問日誌對象
     */
    public void insertLogininfor(SysLogininfor logininfor);

    /**
     * 查詢系統登錄日誌集合
     * 
     * @param logininfor 訪問日誌對象
     * @return 登錄記錄集合
     */
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    /**
     * 批次刪除系統登錄日誌
     * 
     * @param ids 需要刪除的數據
     * @return 結果
     */
    public int deleteLogininforByIds(String[] ids);

    /**
     * 清空系統登錄日誌
     * 
     * @return 結果
     */
    public int cleanLogininfor();
}
