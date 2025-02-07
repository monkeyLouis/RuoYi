package com.ruoyi.web.controller.demo.domain;

import java.util.List;

import lombok.Data;

/**
 * 客戶測試資訊
 * 
 * @author ruoyi
 */
@Data
public class CustomerModel
{
    /**
     * 客戶姓名
     */
    private String name;

    /**
     * 客戶手機
     */
    private String phonenumber;

    /**
     * 客戶性別
     */
    private String sex;

    /**
     * 客戶生日
     */
    private String birthday;

    /**
     * 客戶描述
     */
    private String remark;

    /**
     * 商品資訊
     */
    private List<GoodsModel> goods;
    
}
