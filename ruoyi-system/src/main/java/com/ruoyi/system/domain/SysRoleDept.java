package com.ruoyi.system.domain;

import lombok.Data;

/**
 * 角色和部門關聯 sys_role_dept
 * 
 * @author ruoyi
 */
@Data
public class SysRoleDept
{
    /** 角色ID */
    private Long roleId;
    
    /** 部門ID */
    private Long deptId;

}
