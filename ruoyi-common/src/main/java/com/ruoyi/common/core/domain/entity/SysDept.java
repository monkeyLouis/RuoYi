package com.ruoyi.common.core.domain.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部門表 sys_dept
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部門ID */
    private Long deptId;

    /** 父部門ID */
    private Long parentId;

    /** 祖級列表 */
    private String ancestors;

    /** 部門名稱 */
    @NotBlank(message = "部門名稱不能為空")
    @Size(min = 0, max = 30, message = "部門名稱長度不能超過30個字元")
    private String deptName;

    /** 顯示順序 */
    @NotNull(message = "顯示順序不能為空")
    private Integer orderNum;

    /** 負責人 */
    private String leader;

    /** 聯絡電話 */
    @Size(min = 0, max = 11, message = "聯絡電話長度不能超過11個字元")
    private String phone;

    /** 信箱 */
    @Email(message = "信箱格式不正確")
    @Size(min = 0, max = 50, message = "信箱長度不能超過50個字元")
    private String email;

    /** 部門狀態:0正常,1停用 */
    private String status;

    /** 刪除標誌（0代表存在 2代表刪除） */
    private String delFlag;

    /** 父部門名稱 */
    private String parentName;

    /** 排除編號 */
    private Long excludeId;

    @JsonIgnore
    public Long getExcludeId()
    {
        return excludeId;
    }
    
}
