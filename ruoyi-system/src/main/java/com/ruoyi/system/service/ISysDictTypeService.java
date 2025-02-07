package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysDictType;

/**
 * 字典 業務層
 * 
 * @author ruoyi
 */
public interface ISysDictTypeService
{
    /**
     * 根據條件分頁查詢字典類型
     * 
     * @param dictType 字典類型資訊
     * @return 字典類型集合資訊
     */
    public List<SysDictType> selectDictTypeList(SysDictType dictType);

    /**
     * 根據所有字典類型
     * 
     * @return 字典類型集合資訊
     */
    public List<SysDictType> selectDictTypeAll();

    /**
     * 根據字典類型查詢字典數據
     * 
     * @param dictType 字典類型
     * @return 字典數據集合資訊
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根據字典類型ID查詢資訊
     * 
     * @param dictId 字典類型ID
     * @return 字典類型
     */
    public SysDictType selectDictTypeById(Long dictId);

    /**
     * 根據字典類型查詢資訊
     * 
     * @param dictType 字典類型
     * @return 字典類型
     */
    public SysDictType selectDictTypeByType(String dictType);

    /**
     * 批次刪除字典類型
     * 
     * @param ids 需要刪除的數據
     */
    public void deleteDictTypeByIds(String ids);

    /**
     * 載入字典快取數據
     */
    public void loadingDictCache();

    /**
     * 清空字典快取數據
     */
    public void clearDictCache();

    /**
     * 重設字典快取數據
     */
    public void resetDictCache();

    /**
     * 新增保存字典類型資訊
     * 
     * @param dictType 字典類型資訊
     * @return 結果
     */
    public int insertDictType(SysDictType dictType);

    /**
     * 修改保存字典類型資訊
     * 
     * @param dictType 字典類型資訊
     * @return 結果
     */
    public int updateDictType(SysDictType dictType);

    /**
     * 校驗字典類型稱是否唯一
     * 
     * @param dictType 字典類型
     * @return 結果
     */
    public boolean checkDictTypeUnique(SysDictType dictType);

    /**
     * 查詢字典類型樹
     * 
     * @param dictType 字典類型
     * @return 所有字典類型
     */
    public List<Ztree> selectDictTree(SysDictType dictType);
}
