package com.ruoyi.generator.service;

import java.util.List;
import com.ruoyi.generator.domain.GenTableColumn;

/**
 * 業務欄位 服務層
 * 
 * @author ruoyi
 */
public interface IGenTableColumnService
{
    /**
     * 查詢業務欄位列表
     * 
     * @param genTableColumn 業務欄位資訊
     * @return 業務欄位集合
     */
    public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn);

    /**
     * 新增業務欄位
     * 
     * @param genTableColumn 業務欄位資訊
     * @return 結果
     */
    public int insertGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 修改業務欄位
     * 
     * @param genTableColumn 業務欄位資訊
     * @return 結果
     */
    public int updateGenTableColumn(GenTableColumn genTableColumn);

    /**
     * 刪除業務欄位資訊
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     */
    public int deleteGenTableColumnByIds(String ids);
}
