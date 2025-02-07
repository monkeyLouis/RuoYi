package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.common.core.domain.entity.SysDept;

/**
 * 部門管理 數據層
 * 
 * @author ruoyi
 */
public interface SysDeptMapper
{
    /**
     * 查詢下級部門數量
     * 
     * @param dept 部門資訊
     * @return 結果
     */
    public int selectDeptCount(SysDept dept);

    /**
     * 查詢部門是否存在用戶
     * 
     * @param deptId 部門ID
     * @return 結果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 查詢部門管理數據
     * 
     * @param dept 部門資訊
     * @return 部門資訊集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 刪除部門管理資訊
     * 
     * @param deptId 部門ID
     * @return 結果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增部門資訊
     * 
     * @param dept 部門資訊
     * @return 結果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改部門資訊
     * 
     * @param dept 部門資訊
     * @return 結果
     */
    public int updateDept(SysDept dept);

    /**
     * 修改子元素關係
     * 
     * @param depts 子元素
     * @return 結果
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 根據部門ID查詢資訊
     * 
     * @param deptId 部門ID
     * @return 部門資訊
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 校驗部門名稱是否唯一
     * 
     * @param deptName 部門名稱
     * @param parentId 父部門ID
     * @return 結果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 根據角色ID查詢部門
     *
     * @param roleId 角色ID
     * @return 部門列表
     */
    public List<String> selectRoleDeptTree(Long roleId);

    /**
     * 修改所在部門正常狀態
     * 
     * @param deptIds 部門ID組
     */
    public void updateDeptStatusNormal(Long[] deptIds);

    /**
     * 根據ID查詢所有子部門
     * 
     * @param deptId 部門ID
     * @return 部門列表
     */
    public List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 根據ID查詢所有子部門（正常狀態）
     * 
     * @param deptId 部門ID
     * @return 子部門數
     */
    public int selectNormalChildrenDeptById(Long deptId);
}
