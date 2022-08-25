package com.gljt.providerorder.service;

import com.gljt.providerorder.bean.Order;

/**
* @Author liwei
* @Date 2022/8/19 14:05
* @Version 1.0
*/

public interface OrderService {

    /**
     * 创建订单
     *
     * @param userId
     * @param itemId
     * @param amount
     * @return
     */
    Order createOrder(Integer userId, Integer itemId, Integer amount);

}
