package com.ruoyi.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 崗位表 sys_post
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysPost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 崗位序號 */
    @Excel(name = "崗位序號", cellType = ColumnType.NUMERIC)
    private Long postId;

    /** 崗位編碼 */
    @Excel(name = "崗位編碼")
    @NotBlank(message = "崗位編碼不能為空")
    @Size(min = 0, max = 64, message = "崗位編碼長度不能超過64個字元")
    private String postCode;

    /** 崗位名稱 */
    @Excel(name = "崗位名稱")
    @NotBlank(message = "崗位名稱不能為空")
    @Size(min = 0, max = 50, message = "崗位名稱長度不能超過50個字元")
    private String postName;

    /** 崗位排序 */
    @Excel(name = "崗位排序", cellType = ColumnType.NUMERIC)
    @NotBlank(message = "顯示順序不能為空")
    private String postSort;

    /** 狀態（0正常 1停用） */
    @Excel(name = "狀態", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 用戶是否存在此崗位標識 默認不存在 */
    private boolean flag = false;
    
}
