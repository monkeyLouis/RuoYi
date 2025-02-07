package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysPost;

/**
 * 崗位資訊 服務層
 * 
 * @author ruoyi
 */
public interface ISysPostService
{
    /**
     * 查詢崗位資訊集合
     * 
     * @param post 崗位資訊
     * @return 崗位資訊集合
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
    public int deletePostByIds(String ids);

    /**
     * 新增保存崗位資訊
     * 
     * @param post 崗位資訊
     * @return 結果
     */
    public int insertPost(SysPost post);

    /**
     * 修改保存崗位資訊
     * 
     * @param post 崗位資訊
     * @return 結果
     */
    public int updatePost(SysPost post);

    /**
     * 透過崗位ID查詢崗位使用數量
     * 
     * @param postId 崗位ID
     * @return 結果
     */
    public int countUserPostById(Long postId);

    /**
     * 校驗崗位名稱
     * 
     * @param post 崗位資訊
     * @return 結果
     */
    public boolean checkPostNameUnique(SysPost post);

    /**
     * 校驗崗位編碼
     * 
     * @param post 崗位資訊
     * @return 結果
     */
    public boolean checkPostCodeUnique(SysPost post);
}
