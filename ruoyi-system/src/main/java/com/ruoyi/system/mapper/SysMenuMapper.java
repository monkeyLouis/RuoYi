package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.common.core.domain.entity.SysMenu;

/**
 * 菜單表 數據層
 * 
 * @author ruoyi
 */
public interface SysMenuMapper
{
    /**
     * 查詢系統所有菜單（含按鈕）
     * 
     * @return 菜單列表
     */
    public List<SysMenu> selectMenuAll();

    /**
     * 根據用戶ID查詢菜單
     * 
     * @param userId 用戶ID
     * @return 菜單列表
     */
    public List<SysMenu> selectMenuAllByUserId(Long userId);

    /**
     * 查詢系統正常顯示菜單（不含按鈕）
     * 
     * @return 菜單列表
     */
    public List<SysMenu> selectMenuNormalAll();

    /**
     * 根據用戶ID查詢菜單
     * 
     * @param userId 用戶ID
     * @return 菜單列表
     */
    public List<SysMenu> selectMenusByUserId(Long userId);

    /**
     * 根據用戶ID查詢權限
     * 
     * @param userId 用戶ID
     * @return 權限列表
     */
    public List<String> selectPermsByUserId(Long userId);

    /**
     * 根據角色ID查詢權限
     * 
     * @param roleId 角色ID
     * @return 權限列表
     */
    public List<String> selectPermsByRoleId(Long roleId);

    /**
     * 根據角色ID查詢菜單
     * 
     * @param roleId 角色ID
     * @return 菜單列表
     */
    public List<String> selectMenuTree(Long roleId);

    /**
     * 查詢系統菜單列表
     * 
     * @param menu 菜單資訊
     * @return 菜單列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu);

    /**
     * 查詢系統菜單列表
     * 
     * @param menu 菜單資訊
     * @return 菜單列表
     */
    public List<SysMenu> selectMenuListByUserId(SysMenu menu);

    /**
     * 刪除菜單管理資訊
     * 
     * @param menuId 菜單ID
     * @return 結果
     */
    public int deleteMenuById(Long menuId);

    /**
     * 根據菜單ID查詢資訊
     * 
     * @param menuId 菜單ID
     * @return 菜單資訊
     */
    public SysMenu selectMenuById(Long menuId);

    /**
     * 查詢菜單數量
     * 
     * @param parentId 菜單父ID
     * @return 結果
     */
    public int selectCountMenuByParentId(Long parentId);

    /**
     * 新增菜單資訊
     * 
     * @param menu 菜單資訊
     * @return 結果
     */
    public int insertMenu(SysMenu menu);

    /**
     * 修改菜單資訊
     * 
     * @param menu 菜單資訊
     * @return 結果
     */
    public int updateMenu(SysMenu menu);

    /**
     * 校驗菜單名稱是否唯一
     * 
     * @param menuName 菜單名稱
     * @param parentId 父菜單ID
     * @return 結果
     */
    public SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);
}
