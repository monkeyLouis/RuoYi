package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;

/**
 * 部門管理 服務層
 * 
 * @author ruoyi
 */
public interface ISysDeptService
{
    /**
     * 查詢部門管理數據
     * 
     * @param dept 部門資訊
     * @return 部門資訊集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 查詢部門管理樹
     * 
     * @param dept 部門資訊
     * @return 所有部門資訊
     */
    public List<Ztree> selectDeptTree(SysDept dept);

    /**
     * 查詢部門管理樹（排除下級）
     * 
     * @param dept 部門資訊
     * @return 所有部門資訊
     */
    public List<Ztree> selectDeptTreeExcludeChild(SysDept dept);

    /**
     * 根據角色ID查詢菜單
     *
     * @param role 角色對象
     * @return 菜單列表
     */
    public List<Ztree> roleDeptTreeData(SysRole role);

    /**
     * 根據父部門ID查詢下級部門數量
     * 
     * @param parentId 父部門ID
     * @return 結果
     */
    public int selectDeptCount(Long parentId);

    /**
     * 查詢部門是否存在用戶
     * 
     * @param deptId 部門ID
     * @return 結果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(Long deptId);

    /**
     * 刪除部門管理資訊
     * 
     * @param deptId 部門ID
     * @return 結果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增保存部門資訊
     * 
     * @param dept 部門資訊
     * @return 結果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改保存部門資訊
     * 
     * @param dept 部門資訊
     * @return 結果
     */
    public int updateDept(SysDept dept);

    /**
     * 根據部門ID查詢資訊
     * 
     * @param deptId 部門ID
     * @return 部門資訊
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 根據ID查詢所有子部門（正常狀態）
     * 
     * @param deptId 部門ID
     * @return 子部門數
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * 校驗部門名稱是否唯一
     * 
     * @param dept 部門資訊
     * @return 結果
     */
    public boolean checkDeptNameUnique(SysDept dept);

    /**
     * 校驗部門是否有數據權限
     * 
     * @param deptId 部門id
     */
    public void checkDeptDataScope(Long deptId);
}
