package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysRole;

/**
 * 角色表 數據層
 * 
 * @author ruoyi
 */
public interface SysRoleMapper
{
    /**
     * 根據條件分頁查詢角色數據
     * 
     * @param role 角色資訊
     * @return 角色數據集合資訊
     */
    public List<SysRole> selectRoleList(SysRole role);

    /**
     * 根據用戶ID查詢角色
     * 
     * @param userId 用戶ID
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 通過角色ID查詢角色
     * 
     * @param roleId 角色ID
     * @return 角色對象資訊
     */
    public SysRole selectRoleById(Long roleId);

    /**
     * 通過角色ID刪除角色
     * 
     * @param roleId 角色ID
     * @return 結果
     */
    public int deleteRoleById(Long roleId);

    /**
     * 批次角色用戶資訊
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     */
    public int deleteRoleByIds(Long[] ids);

    /**
     * 修改角色資訊
     * 
     * @param role 角色資訊
     * @return 結果
     */
    public int updateRole(SysRole role);

    /**
     * 新增角色資訊
     * 
     * @param role 角色資訊
     * @return 結果
     */
    public int insertRole(SysRole role);

    /**
     * 校驗角色名稱是否唯一
     * 
     * @param roleName 角色名稱
     * @return 角色資訊
     */
    public SysRole checkRoleNameUnique(String roleName);
    
    /**
     * 校驗角色權限是否唯一
     * 
     * @param roleKey 角色權限
     * @return 角色資訊
     */
    public SysRole checkRoleKeyUnique(String roleKey);
}
