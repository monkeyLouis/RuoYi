package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysPost;

/**
 * 崗位資訊 數據層
 * 
 * @author ruoyi
 */
public interface SysPostMapper
{
    /**
     * 查詢崗位數據集合
     * 
     * @param post 崗位資訊
     * @return 崗位數據集合
     */
    public List<SysPost> selectPostList(SysPost post);

    /**
     * 查詢所有崗位
     * 
     * @return 崗位列表
     */
    public List<SysPost> selectPostAll();

    /**
     * 根據用戶ID查詢崗位
     * 
     * @param userId 用戶ID
     * @return 崗位列表
     */
    public List<SysPost> selectPostsByUserId(Long userId);

    /**
     * 通過崗位ID查詢崗位資訊
     * 
     * @param postId 崗位ID
     * @return 角色對象資訊
     */
    public SysPost selectPostById(Long postId);

    /**
     * 批次刪除崗位資訊
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     */
    public int deletePostByIds(Long[] ids);

    /**
     * 修改崗位資訊
     * 
     * @param post 崗位資訊
     * @return 結果
     */
    public int updatePost(SysPost post);

    /**
     * 新增崗位資訊
     * 
     * @param post 崗位資訊
     * @return 結果
     */
    public int insertPost(SysPost post);

    /**
     * 校驗崗位名稱
     * 
     * @param postName 崗位名稱
     * @return 結果
     */
    public SysPost checkPostNameUnique(String postName);

    /**
     * 校驗崗位編碼
     * 
     * @param postCode 崗位編碼
     * @return 結果
     */
    public SysPost checkPostCodeUnique(String postCode);
}
