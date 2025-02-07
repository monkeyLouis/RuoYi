package com.ruoyi.generator.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.generator.mapper.GenTableColumnMapper;
import com.ruoyi.generator.service.IGenTableColumnService;

/**
 * 業務欄位 服務層實作
 * 
 * @author ruoyi
 */
@Service
public class GenTableColumnServiceImpl implements IGenTableColumnService
{
    @Autowired
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 查詢業務欄位列表
     * 
     * @param genTableColumn 業務欄位資訊
     * @return 業務欄位集合
     */
    @Override
    public List<GenTableColumn> selectGenTableColumnListByTableId(GenTableColumn genTableColumn)
    {
        return genTableColumnMapper.selectGenTableColumnListByTableId(genTableColumn);
    }

    /**
     * 新增業務欄位
     * 
     * @param genTableColumn 業務欄位資訊
     * @return 結果
     */
    @Override
    public int insertGenTableColumn(GenTableColumn genTableColumn)
    {
        return genTableColumnMapper.insertGenTableColumn(genTableColumn);
    }

    /**
     * 修改業務欄位
     * 
     * @param genTableColumn 業務欄位資訊
     * @return 結果
     */
    @Override
    public int updateGenTableColumn(GenTableColumn genTableColumn)
    {
        return genTableColumnMapper.updateGenTableColumn(genTableColumn);
    }

    /**
     * 刪除業務欄位對象
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     */
    @Override
    public int deleteGenTableColumnByIds(String ids)
    {
        return genTableColumnMapper.deleteGenTableColumnByIds(Convert.toLongArray(ids));
    }
}