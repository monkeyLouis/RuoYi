package com.ruoyi.common.core.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * Ztree樹結構實體類
 * 
 * @author ruoyi
 */
@Data
public class Ztree implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 節點ID */
    private Long id;

    /** 節點父ID */
    private Long pId;

    /** 節點名稱 */
    private String name;

    /** 節點標題 */
    private String title;

    /** 是否勾選 */
    private boolean checked = false;

    /** 是否展開 */
    private boolean open = false;

    /** 是否能勾選 */
    private boolean nocheck = false;

}