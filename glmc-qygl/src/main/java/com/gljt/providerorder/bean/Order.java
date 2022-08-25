package com.gljt.providerorder.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @Author liwei
* @Date 2022/8/19 14:05
* @Version 1.0
*/
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 1932307326489266506L;

    /**
     * 订单号
     */
    private String id;

    private Integer userId;

    private Integer itemId;

    private BigDecimal itemPrice;

    private Integer amount;

    private BigDecimal orderPrice;
}
