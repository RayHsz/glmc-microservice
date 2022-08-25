package com.gljt.provideritem.service;

import com.gljt.provideritem.bean.Item;

/**
* @Author liwei
* @Date 2022/8/19 14:02
* @Version 1.0
*/
public interface ItemService {
    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    Item getItemById(Integer id);

    /**
     * 库存扣减
     *
     * @param itemId
     * @param amount
     * @return
     */
    boolean decreaseStock(Integer itemId, Integer amount);

    /**
     * 商品下单后对应销量增加
     *
     * @param itemId
     * @param amount
     */
    void increaseSales(Integer itemId, Integer amount);
}