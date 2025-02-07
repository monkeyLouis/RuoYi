package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.SysUserRole;

/**
 * 用戶 業務層
 * 
 * @author ruoyi
 */
public interface ISysUserService
{
    /**
     * 根據條件分頁查詢用戶列表
     * 
     * @param user 用戶資訊
     * @return 用戶資訊集合資訊
     */
    public List<SysUser> selectUserList(SysUser user);

    /**
     * 根據條件分頁查詢已分配用戶角色列表
     * 
     * @param user 用戶資訊
     * @return 用戶資訊集合資訊
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根據條件分頁查詢未分配用戶角色列表
     * 
     * @param user 用戶資訊
     * @return 用戶資訊集合資訊
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 透過使用者名稱查詢用戶
     * 
     * @param userName 使用者名稱
     * @return 用戶對象資訊
     */
    public SysUser selectUserByLoginName(String userName);

    /**
     * 透過手機號碼查詢用戶
     * 
     * @param phoneNumber 手機號碼
     * @return 用戶對象資訊
     */
    public SysUser selectUserByPhoneNumber(String phoneNumber);

    /**
     * 透過信箱查詢用戶
     * 
     * @param email 信箱
     * @return 用戶對象資訊
     */
    public SysUser selectUserByEmail(String email);

    /**
     * 透過用戶ID查詢用戶
     * 
     * @param userId 用戶ID
     * @return 用戶對象資訊
     */
    public SysUser selectUserById(Long userId);

    /**
     * 透過用戶ID查詢用戶和角色關聯
     * 
     * @param userId 用戶ID
     * @return 用戶和角色關聯列表
     */
    public List<SysUserRole> selectUserRoleByUserId(Long userId);

    /**
     * 透過用戶ID刪除用戶
     * 
     * @param userId 用戶ID
     * @return 結果
     */
    public int deleteUserById(Long userId);

    /**
     * 批次刪除用戶資訊
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     * @throws Exception 異常
     */
    public int deleteUserByIds(String ids);

    /**
     * 保存用戶資訊
     * 
     * @param user 用戶資訊
     * @return 結果
     */
    public int insertUser(SysUser user);

    /**
     * 註冊用戶資訊
     * 
     * @param user 用戶資訊
     * @return 結果
     */
    public boolean registerUser(SysUser user);

    /**
     * 保存用戶資訊
     * 
     * @param user 用戶資訊
     * @return 結果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用戶詳細資訊
     * 
     * @param user 用戶資訊
     * @return 結果
     */
    public int updateUserInfo(SysUser user);

    /**
     * 用戶授權角色
     * 
     * @param userId 用戶ID
     * @param roleIds 角色組
     */
    public void insertUserAuth(Long userId, Long[] roleIds);

    /**
     * 修改用戶密碼資訊
     * 
     * @param user 用戶資訊
     * @return 結果
     */
    public int resetUserPwd(SysUser user);

    /**
     * 校驗使用者名稱是否唯一
     * 
     * @param user 用戶資訊
     * @return 結果
     */
    public boolean checkLoginNameUnique(SysUser user);

    /**
     * 校驗手機號碼是否唯一
     *
     * @param user 用戶資訊
     * @return 結果
     */
    public boolean checkPhoneUnique(SysUser user);

    /**
     * 校驗email是否唯一
     *
     * @param user 用戶資訊
     * @return 結果
     */
    public boolean checkEmailUnique(SysUser user);

    /**
     * 校驗用戶是否允許操作
     * 
     * @param user 用戶資訊
     */
    public void checkUserAllowed(SysUser user);

    /**
     * 校驗用戶是否有數據權限
     * 
     * @param userId 用戶id
     */
    public void checkUserDataScope(Long userId);

    /**
     * 根據用戶ID查詢用戶所屬角色組
     * 
     * @param userId 用戶ID
     * @return 結果
     */
    public String selectUserRoleGroup(Long userId);

    /**
     * 根據用戶ID查詢用戶所屬崗位組
     * 
     * @param userId 用戶ID
     * @return 結果
     */
    public String selectUserPostGroup(Long userId);

    /**
     * 導入用戶數據
     * 
     * @param userList 用戶數據列表
     * @param isUpdateSupport 是否更新支持，如果已存在，則進行更新數據
     * @param operName 操作用戶
     * @return 結果
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

    /**
     * 用戶狀態修改
     * 
     * @param user 用戶資訊
     * @return 結果
     */
    public int changeStatus(SysUser user);
}
