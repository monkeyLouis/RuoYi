package com.ruoyi.common.core.page;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表格分頁數據對象
 * 
 * @author ruoyi
 */
@Data
@NoArgsConstructor
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 總記錄數 */
    private long total;

    /** 列表數據 */
    private List<?> rows;

    /** 消息狀態碼 */
    private int code;

    /** 消息內容 */
    private String msg;
    
}