package com.ruoyi.system.service;

import java.util.List;
import java.util.Set;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.SysUserRole;

/**
 * 角色業務層
 * 
 * @author ruoyi
 */
public interface ISysRoleService
{
    /**
     * 根據條件分頁查詢角色數據
     * 
     * @param role 角色資訊
     * @return 角色數據集合資訊
     */
    public List<SysRole> selectRoleList(SysRole role);

    /**
     * 根據用戶ID查詢角色列表
     * 
     * @param userId 用戶ID
     * @return 權限列表
     */
    public Set<String> selectRoleKeys(Long userId);

    /**
     * 根據用戶ID查詢角色權限
     * 
     * @param userId 用戶ID
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 查詢所有角色
     * 
     * @return 角色列表
     */
    public List<SysRole> selectRoleAll();

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
    public boolean deleteRoleById(Long roleId);

    /**
     * 批次刪除角色用戶資訊
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     * @throws Exception 異常
     */
    public int deleteRoleByIds(String ids);

    /**
     * 新增保存角色資訊
     * 
     * @param role 角色資訊
     * @return 結果
     */
    public int insertRole(SysRole role);

    /**
     * 修改保存角色資訊
     * 
     * @param role 角色資訊
     * @return 結果
     */
    public int updateRole(SysRole role);

    /**
     * 修改數據權限資訊
     * 
     * @param role 角色資訊
     * @return 結果
     */
    public int authDataScope(SysRole role);

    /**
     * 校驗角色名稱是否唯一
     * 
     * @param role 角色資訊
     * @return 結果
     */
    public boolean checkRoleNameUnique(SysRole role);

    /**
     * 校驗角色權限是否唯一
     * 
     * @param role 角色資訊
     * @return 結果
     */
    public boolean checkRoleKeyUnique(SysRole role);

    /**
     * 校驗角色是否允許操作
     * 
     * @param role 角色資訊
     */
    public void checkRoleAllowed(SysRole role);

    /**
     * 校驗角色是否有數據權限
     * 
     * @param roleIds 角色id
     */
    public void checkRoleDataScope(Long... roleIds);

    /**
     * 透過角色ID查詢角色使用數量
     * 
     * @param roleId 角色ID
     * @return 結果
     */
    public int countUserRoleByRoleId(Long roleId);

    /**
     * 角色狀態修改
     * 
     * @param role 角色資訊
     * @return 結果
     */
    public int changeStatus(SysRole role);

    /**
     * 取消授權用戶角色
     * 
     * @param userRole 用戶和角色關聯資訊
     * @return 結果
     */
    public int deleteAuthUser(SysUserRole userRole);

    /**
     * 批次取消授權用戶角色
     * 
     * @param roleId 角色ID
     * @param userIds 需要刪除的用戶數據ID
     * @return 結果
     */
    public int deleteAuthUsers(Long roleId, String userIds);

    /**
     * 批次選擇授權用戶角色
     * 
     * @param roleId 角色ID
     * @param userIds 需要刪除的用戶數據ID
     * @return 結果
     */
    public int insertAuthUsers(Long roleId, String userIds);
}
