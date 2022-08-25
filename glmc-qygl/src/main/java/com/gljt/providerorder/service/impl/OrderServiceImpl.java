package com.gljt.providerorder.service.impl;


import com.gljt.providerorder.bean.Item;
import com.gljt.providerorder.bean.Order;
import com.gljt.providerorder.mapper.OrderMapper;
import com.gljt.providerorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
* @Author liwei
* @Date 2022/8/19 14:05
* @Version 1.0
*/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(Integer userId, Integer itemId, Integer amount) {

        Item item = this.restTemplate.getForObject("http://localhost:8000/item/{id}", Item.class, itemId);

        Order order = new Order();
        order.setId(UUID.randomUUID().toString().replace("-",""));
        order.setUserId(userId);
        order.setItemId(itemId);
        order.setAmount(amount);
        order.setItemPrice(item.getPrice());
        order.setOrderPrice(order.getItemPrice().multiply(BigDecimal.valueOf(amount)));

        // 生成订单
        orderMapper.insert(order);

        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return order;
    }
}
