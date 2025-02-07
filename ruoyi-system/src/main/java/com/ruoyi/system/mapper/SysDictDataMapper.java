package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.common.core.domain.entity.SysDictData;

/**
 * 字典表 數據層
 * 
 * @author ruoyi
 */
public interface SysDictDataMapper
{
    /**
     * 根據條件分頁查詢字典數據
     * 
     * @param dictData 字典數據資訊
     * @return 字典數據集合資訊
     */
    public List<SysDictData> selectDictDataList(SysDictData dictData);

    /**
     * 根據字典類型查詢字典數據
     * 
     * @param dictType 字典類型
     * @return 字典數據集合資訊
     */
    public List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根據字典類型和字典鍵值查詢字典數據資訊
     * 
     * @param dictType 字典類型
     * @param dictValue 字典鍵值
     * @return 字典標籤
     */
    public String selectDictLabel(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

    /**
     * 根據字典數據ID查詢資訊
     * 
     * @param dictCode 字典數據ID
     * @return 字典數據
     */
    public SysDictData selectDictDataById(Long dictCode);

    /**
     * 查詢字典數據
     * 
     * @param dictType 字典類型
     * @return 字典數據
     */
    public int countDictDataByType(String dictType);

    /**
     * 通過字典ID刪除字典數據資訊
     * 
     * @param dictCode 字典數據ID
     * @return 結果
     */
    public int deleteDictDataById(Long dictCode);

    /**
     * 批次刪除字典數據
     * 
     * @param ids 需要刪除的數據
     * @return 結果
     */
    public int deleteDictDataByIds(String[] ids);

    /**
     * 新增字典數據資訊
     * 
     * @param dictData 字典數據資訊
     * @return 結果
     */
    public int insertDictData(SysDictData dictData);

    /**
     * 修改字典數據資訊
     * 
     * @param dictData 字典數據資訊
     * @return 結果
     */
    public int updateDictData(SysDictData dictData);

    /**
     * 同步修改字典類型
     * 
     * @param oldDictType 舊字典類型
     * @param newDictType 新舊字典類型
     * @return 結果
     */
    public int updateDictDataType(@Param("oldDictType") String oldDictType, @Param("newDictType") String newDictType);
}
