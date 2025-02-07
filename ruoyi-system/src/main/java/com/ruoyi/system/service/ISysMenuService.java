package com.ruoyi.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 菜單 業務層
 * 
 * @author ruoyi
 */
public interface ISysMenuService
{
    /**
     * 根據用戶ID查詢菜單
     * 
     * @param user 用戶資訊
     * @return 菜單列表
     */
    public List<SysMenu> selectMenusByUser(SysUser user);

    /**
     * 查詢系統菜單列表
     * 
     * @param menu 菜單資訊
     * @param userId 用戶ID
     * @return 菜單列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId);

    /**
     * 查詢菜單集合
     * 
     * @param userId 用戶ID
     * @return 所有菜單資訊
     */
    public List<SysMenu> selectMenuAll(Long userId);

    /**
     * 根據用戶ID查詢權限
     * 
     * @param userId 用戶ID
     * @return 權限列表
     */
    public Set<String> selectPermsByUserId(Long userId);

    /**
     * 根據角色ID查詢權限
     * 
     * @param roleId 角色ID
     * @return 權限列表
     */
    public Set<String> selectPermsByRoleId(Long roleId);

    /**
     * 根據角色ID查詢菜單
     * 
     * @param role 角色對象
     * @param userId 用戶ID
     * @return 菜單列表
     */
    public List<Ztree> roleMenuTreeData(SysRole role, Long userId);

    /**
     * 查詢所有菜單資訊
     * 
     * @param userId 用戶ID
     * @return 菜單列表
     */
    public List<Ztree> menuTreeData(Long userId);

    /**
     * 查詢系統所有權限
     * 
     * @param userId 用戶ID
     * @return 權限列表
     */
    public Map<String, String> selectPermsAll(Long userId);

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
     * 查詢菜單使用數量
     * 
     * @param menuId 菜單ID
     * @return 結果
     */
    public int selectCountRoleMenuByMenuId(Long menuId);

    /**
     * 新增保存菜單資訊
     * 
     * @param menu 菜單資訊
     * @return 結果
     */
    public int insertMenu(SysMenu menu);

    /**
     * 修改保存菜單資訊
     * 
     * @param menu 菜單資訊
     * @return 結果
     */
    public int updateMenu(SysMenu menu);

    /**
     * 校驗菜單名稱是否唯一
     * 
     * @param menu 菜單資訊
     * @return 結果
     */
    public boolean checkMenuNameUnique(SysMenu menu);
}
