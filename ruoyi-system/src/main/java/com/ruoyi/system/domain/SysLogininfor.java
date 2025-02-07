package com.ruoyi.system.domain;

import java.util.Date;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系統訪問記錄表 sys_logininfor
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysLogininfor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "序號", cellType = ColumnType.NUMERIC)
    private Long infoId;

    /** 用戶帳號 */
    @Excel(name = "用戶帳號")
    private String loginName;

    /** 登錄狀態 0成功 1失敗 */
    @Excel(name = "登錄狀態", readConverterExp = "0=成功,1=失敗")
    private String status;

    /** 登錄IP位址 */
    @Excel(name = "登錄地址")
    private String ipaddr;

    /** 登錄地點 */
    @Excel(name = "登錄地點")
    private String loginLocation;

    /** 瀏覽器類型 */
    @Excel(name = "瀏覽器")
    private String browser;

    /** 操作系統 */
    @Excel(name = "操作系統")
    private String os;

    /** 提示消息 */
    @Excel(name = "提示消息")
    private String msg;

    /** 訪問時間 */
    @Excel(name = "訪問時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
    
}