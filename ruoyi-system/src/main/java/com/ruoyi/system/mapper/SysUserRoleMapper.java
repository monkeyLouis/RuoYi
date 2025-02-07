package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.SysUserRole;

/**
 * 用戶與角色關聯表 數據層
 * 
 * @author ruoyi
 */
public interface SysUserRoleMapper
{
    /**
     * 透過用戶ID查詢用戶和角色關聯
     * 
     * @param userId 用戶ID
     * @return 用戶和角色關聯列表
     */
    public List<SysUserRole> selectUserRoleByUserId(Long userId);

    /**
     * 透過用戶ID刪除用戶和角色關聯
     * 
     * @param userId 用戶ID
     * @return 結果
     */
    public int deleteUserRoleByUserId(Long userId);

    /**
     * 批次刪除用戶和角色關聯
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     */
    public int deleteUserRole(Long[] ids);

    /**
     * 透過角色ID查詢角色使用數量
     * 
     * @param roleId 角色ID
     * @return 結果
     */
    public int countUserRoleByRoleId(Long roleId);

    /**
     * 批次新增用戶角色資訊
     * 
     * @param userRoleList 用戶角色列表
     * @return 結果
     */
    public int batchUserRole(List<SysUserRole> userRoleList);

    /**
     * 刪除用戶和角色關聯資訊
     * 
     * @param userRole 用戶和角色關聯資訊
     * @return 結果
     */
    public int deleteUserRoleInfo(SysUserRole userRole);

    /**
     * 批次取消授權用戶角色
     * 
     * @param roleId 角色ID
     * @param userIds 需要刪除的用戶數據ID
     * @return 結果
     */
    public int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);
}
