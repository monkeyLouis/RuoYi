package com.ruoyi.common.core.domain;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * CxSelect樹結構實體類
 * 
 * @author ruoyi
 */
@Data
public class CxSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 數據值欄位名稱
     */
    private String v;

    /**
     * 數據標題欄位名稱
     */
    private String n;

    /**
     * 子集數據欄位名稱
     */
    private List<CxSelect> s;

    public CxSelect() { }

    public CxSelect(String v, String n) {
        this.v = v;
        this.n = n;
    }

}
