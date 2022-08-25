package com.gljt.provideritem.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @Author liwei
* @Date 2022/8/19 14:01
* @Version 1.0
*/
@Data
public class Item implements Serializable {

    private static final long serialVersionUID = 2778111153054555565L;

    private Integer id;

    private String title;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private Integer sales;

    private String imgUrl;
}
