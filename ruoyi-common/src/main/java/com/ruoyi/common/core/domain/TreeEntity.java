package com.ruoyi.common.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Tree基類
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class TreeEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 父菜單名稱 */
    private String parentName;

    /** 父菜單ID */
    private Long parentId;

    /** 顯示順序 */
    private Integer orderNum;

    /** 祖級列表 */
    private String ancestors;

}