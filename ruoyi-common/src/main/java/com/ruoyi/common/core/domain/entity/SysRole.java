package com.ruoyi.common.core.domain.entity;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表 sys_role
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @Excel(name = "角色序號", cellType = ColumnType.NUMERIC)
    private Long roleId;

    /** 角色名稱 */
    @Excel(name = "角色名稱")
    @NotBlank(message = "角色名稱不能為空")
    @Size(min = 0, max = 30, message = "角色名稱長度不能超過30個字元")
    private String roleName;

    /** 角色權限 */
    @Excel(name = "角色權限")
    @NotBlank(message = "權限字元不能為空")
    @Size(min = 0, max = 100, message = "權限字元長度不能超過100個字元")
    private String roleKey;

    /** 角色排序 */
    @Excel(name = "角色排序", cellType = ColumnType.NUMERIC)
    @NotBlank(message = "顯示順序不能為空")
    private String roleSort;

    /** 數據範圍（1：所有數據權限；2：自訂數據權限；3：本部門數據權限；4：本部門及以下數據權限；5：僅本人數據權限） */
    @Excel(name = "數據範圍", readConverterExp = "1=所有數據權限,2=自訂數據權限,3=本部門數據權限,4=本部門及以下數據權限,5=僅本人數據權限")
    private String dataScope;

    /** 角色狀態（0正常 1停用） */
    @Excel(name = "角色狀態", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 刪除標誌（0代表存在 2代表刪除） */
    private String delFlag;

    /** 用戶是否存在此角色標識 默認不存在 */
    private boolean flag = false;

    /** 菜單組 */
    private Long[] menuIds;

    /** 部門組（數據權限） */
    private Long[] deptIds;

    /** 角色菜單權限 */
    private Set<String> permissions;

    public SysRole()
    {

    }

    public SysRole(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId)
    {
        return roleId != null && 1L == roleId;
    }

}
