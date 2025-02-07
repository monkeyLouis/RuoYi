package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 用戶表 數據層
 * 
 * @author ruoyi
 */
public interface SysUserMapper
{
    /**
     * 根據條件分頁查詢用戶列表
     * 
     * @param sysUser 用戶資訊
     * @return 用戶資訊集合資訊
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 根據條件分頁查詢已配用戶角色列表
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
     */
    public int deleteUserByIds(Long[] ids);

    /**
     * 修改用戶資訊
     * 
     * @param user 用戶資訊
     * @return 結果
     */
    public int updateUser(SysUser user);

    /**
     * 新增用戶資訊
     * 
     * @param user 用戶資訊
     * @return 結果
     */
    public int insertUser(SysUser user);

    /**
     * 校驗使用者名稱是否唯一
     * 
     * @param loginName 登錄名稱
     * @return 結果
     */
    public SysUser checkLoginNameUnique(String loginName);

    /**
     * 校驗手機號碼是否唯一
     *
     * @param phonenumber 手機號碼
     * @return 結果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校驗email是否唯一
     *
     * @param email 用戶信箱
     * @return 結果
     */
    public SysUser checkEmailUnique(String email);
}
