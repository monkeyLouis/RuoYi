package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysUserPost;

/**
 * 用戶與崗位關聯表 數據層
 * 
 * @author ruoyi
 */
public interface SysUserPostMapper
{
    /**
     * 透過用戶ID刪除用戶和崗位關聯
     * 
     * @param userId 用戶ID
     * @return 結果
     */
    public int deleteUserPostByUserId(Long userId);
    
    /**
     * 透過崗位ID查詢崗位使用數量
     * 
     * @param postId 崗位ID
     * @return 結果
     */
    public int countUserPostById(Long postId);
    
    /**
     * 批次刪除用戶和崗位關聯
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     */
    public int deleteUserPost(Long[] ids);

    /**
     * 批次新增用戶崗位資訊
     * 
     * @param userPostList 用戶崗位列表
     * @return 結果
     */
    public int batchUserPost(List<SysUserPost> userPostList);
}
