package com.ruoyi.common.core.page;

import com.ruoyi.common.utils.StringUtils;

import lombok.Data;

/**
 * 分頁數據
 * 
 * @author ruoyi
 */
@Data
public class PageDomain
{
    /** 當前記錄起始索引 */
    private Integer pageNum;

    /** 每頁顯示記錄數 */
    private Integer pageSize;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";

    /** 分頁參數合理化 */
    private Boolean reasonable = true;

    public String getOrderBy() {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    public Boolean getReasonable() {
        if (StringUtils.isNull(reasonable))
        {
            return Boolean.TRUE;
        }
        return reasonable;
    }

}