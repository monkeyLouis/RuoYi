package com.ruoyi.common.core.domain.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 使用者 Entity sys_user
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用戶ID */
    @Excel(name = "用戶序號", type = Type.EXPORT, cellType = ColumnType.NUMERIC, prompt = "用戶編號")
    private Long userId;

    /** 部門ID */
    @Excel(name = "部門編號", type = Type.IMPORT)
    private Long deptId;

    /** 部門父ID */
    private Long parentId;

    /** 角色ID */
    private Long roleId;

    /** 登錄名稱 */
    @Excel(name = "登錄名稱")
    @Xss(message = "登入帳號不能包含腳本字元")
    @NotBlank(message = "登入帳號不能為空")
    @Size(min = 0, max = 30, message = "登入帳號長度不能超過30個字元")
    private String loginName;

    /** 使用者名稱 */
    @Excel(name = "使用者名稱")
    @Xss(message = "用戶暱稱不能包含腳本字元")
    @Size(min = 0, max = 30, message = "用戶暱稱長度不能超過30個字元")
    private String userName;

    /** 用戶類型 */
    private String userType;

    /** 用戶信箱 */
    @Excel(name = "用戶信箱")
    @Email(message = "信箱格式不正確")
    @Size(min = 0, max = 50, message = "信箱長度不能超過50個字元")
    private String email;

    /** 手機號碼 */
    @Excel(name = "手機號碼", cellType = ColumnType.TEXT)
    @Size(min = 0, max = 10, message = "手機號碼長度不能超過10個字元")
    private String phonenumber;

    /** 用戶性別 */
    @Excel(name = "用戶性別", readConverterExp = "0=男,1=女,2=多元性別")
    private String sex;

    /** 用戶頭像 */
    private String avatar;

    /** 密碼 */
    private String password;

    /** 鹽加密 */
    private String salt;

    /** 帳號狀態（0正常 1停用） */
    @Excel(name = "帳號狀態", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 刪除標誌（0代表存在 2代表刪除） */
    private String delFlag;

    /** 最後登錄IP */
    @Excel(name = "最後登錄IP", type = Type.EXPORT)
    private String loginIp;

    /** 最後登錄時間 */
    @Excel(name = "最後登錄時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;

    /** 密碼最後更新時間 */
    private Date pwdUpdateDate;

    /** 部門對象 */
    @Excels({
        @Excel(name = "部門名稱", targetAttr = "deptName", type = Type.EXPORT),
        @Excel(name = "部門負責人", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept dept;

    private List<SysRole> roles;

    /** 角色組 */
    private Long[] roleIds;

    /** 崗位組 */
    private Long[] postIds;
    
    public SysUser() {

    }

    public SysUser(Long userId) {
        this.userId = userId;
    }


    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }
    
    @JsonIgnore
    public String getPassword()
    {
        return password;
    }

    @JsonIgnore
    public String getSalt()
    {
        return salt;
    }

    public SysDept getDept()
    {
        if (dept == null)
        {
            dept = new SysDept();
        }
        return dept;
    }
    
}
