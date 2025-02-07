package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysDictData;

/**
 * 字典 業務層
 * 
 * @author ruoyi
 */
public interface ISysDictDataService
{
    /**
     * 根據條件分頁查詢字典數據
     * 
     * @param dictData 字典數據資訊
     * @return 字典數據集合資訊
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 根據字典類型和字典鍵值查詢字典數據資訊
     * 
     * @param dictType 字典類型
     * @param dictValue 字典鍵值
     * @return 字典標籤
     */
    public String selectDictLabel(String dictType, String dictValue);

    /**
     * 根據字典數據ID查詢資訊
     * 
     * @param dictCode 字典數據ID
     * @return 字典數據
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * 批次刪除字典數據
     * 
     * @param ids 需要刪除的數據
     */
    public void deleteDictDataByIds(String ids);

    /**
     * 新增保存字典數據資訊
     * 
     * @param dictData 字典數據資訊
     * @return 結果
     */
    public int insertDictData(SysDictData dictData);

    /**
     * 修改保存字典數據資訊
     * 
     * @param dictData 字典數據資訊
     * @return 結果
     */
    public int updateDictData(SysDictData dictData);
}
