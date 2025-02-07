package com.ruoyi.web.controller.demo.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

/**
 * 商品測試資訊
 * 
 * @author ruoyi
 */
@Data
public class GoodsModel
{
    /**
     * 商品名稱
     */
    private String name;

    /**
     * 商品重量
     */
    private Integer weight;

    /**
     * 商品價格
     */
    private Double price;
    
    /**
     * 商品日期
     */
    private Date date;

    /**
     * 商品種類
     */
    private String type;
    
}
