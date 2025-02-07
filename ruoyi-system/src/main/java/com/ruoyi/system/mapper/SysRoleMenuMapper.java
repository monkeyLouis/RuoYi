package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysRoleMenu;

/**
 * 角色與菜單關聯表 數據層
 * 
 * @author ruoyi
 */
public interface SysRoleMenuMapper
{
    /**
     * 通過角色ID刪除角色和菜單關聯
     * 
     * @param roleId 角色ID
     * @return 結果
     */
    public int deleteRoleMenuByRoleId(Long roleId);
    
    /**
     * 批次刪除角色菜單關聯資訊
     * 
     * @param ids 需要刪除的數據ID
     * @return 結果
     */
    public int deleteRoleMenu(Long[] ids);
    
    /**
     * 查詢菜單使用數量
     * 
     * @param menuId 菜單ID
     * @return 結果
     */
    public int selectCountRoleMenuByMenuId(Long menuId);
    
    /**
     * 批次新增角色菜單資訊
     * 
     * @param roleMenuList 角色菜單列表
     * @return 結果
     */
    public int batchRoleMenu(List<SysRoleMenu> roleMenuList);
}
