package com.ruoyi.common.core.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜單權限表 sys_menu
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysMenu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜單ID */
    private Long menuId;

    /** 菜單名稱 */
    @NotBlank(message = "菜單名稱不能為空")
    @Size(min = 0, max = 50, message = "菜單名稱長度不能超過50個字元")
    private String menuName;

    /** 父菜單名稱 */
    private String parentName;

    /** 父菜單ID */
    private Long parentId;

    /** 顯示順序 */
    @NotBlank(message = "顯示順序不能為空")
    private String orderNum;

    /** 菜單URL */
    @Size(min = 0, max = 200, message = "請求地址不能超過200個字元")
    private String url;

    /** 打開方式（menuItem頁簽 menuBlank新窗口） */
    private String target;

    /** 類型（M目錄 C菜單 F按鈕） */
    @NotBlank(message = "菜單類型不能為空")
    private String menuType;

    /** 菜單狀態（0顯示 1隱藏） */
    private String visible;

    /** 是否刷新（0刷新 1不刷新） */
    private String isRefresh;

    /** 權限字串 */
    @Size(min = 0, max = 100, message = "權限標識長度不能超過100個字元")
    private String perms;

    /** 菜單圖示 */
    private String icon;

    /** 子菜單 */
    private List<SysMenu> children = new ArrayList<SysMenu>();

}
