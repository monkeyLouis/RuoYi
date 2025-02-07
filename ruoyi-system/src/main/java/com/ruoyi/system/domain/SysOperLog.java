package com.ruoyi.system.domain;

import java.util.Date;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日誌記錄表 oper_log
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysOperLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日誌主鍵 */
    @Excel(name = "操作序號", cellType = ColumnType.NUMERIC)
    private Long operId;

    /** 操作模組 */
    @Excel(name = "操作模組")
    private String title;

    /** 業務類型（0其它 1新增 2修改 3刪除） */
    @Excel(name = "業務類型", readConverterExp = "0=其它,1=新增,2=修改,3=刪除,4=授權,5=導出,6=導入,7=強退,8=生成代碼,9=清空數據")
    private Integer businessType;

    /** 業務類型數組 */
    private Integer[] businessTypes;

    /** 請求方法 */
    @Excel(name = "請求方法")
    private String method;

    /** 請求方式 */
    @Excel(name = "請求方式")
    private String requestMethod;

    /** 操作類別（0其它 1後台用戶 2手機端用戶） */
    @Excel(name = "操作類別", readConverterExp = "0=其它,1=後台用戶,2=手機端用戶")
    private Integer operatorType;

    /** 操作人員 */
    @Excel(name = "操作人員")
    private String operName;

    /** 部門名稱 */
    @Excel(name = "部門名稱")
    private String deptName;

    /** 請求url */
    @Excel(name = "請求地址")
    private String operUrl;

    /** 操作地址 */
    @Excel(name = "操作地址")
    private String operIp;

    /** 操作地點 */
    @Excel(name = "操作地點")
    private String operLocation;

    /** 請求參數 */
    @Excel(name = "請求參數")
    private String operParam;

    /** 返回參數 */
    @Excel(name = "返回參數")
    private String jsonResult;

    /** 操作狀態（0正常 1異常） */
    @Excel(name = "狀態", readConverterExp = "0=正常,1=異常")
    private Integer status;

    /** 錯誤消息 */
    @Excel(name = "錯誤消息")
    private String errorMsg;

    /** 操作時間 */
    @Excel(name = "操作時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    /** 消耗時間 */
    @Excel(name = "消耗時間", suffix = "毫秒")
    private Long costTime;

}
