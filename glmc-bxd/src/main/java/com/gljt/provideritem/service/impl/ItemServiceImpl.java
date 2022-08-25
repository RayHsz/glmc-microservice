package com.gljt.provideritem.service.impl;


import com.gljt.provideritem.bean.Item;
import com.gljt.provideritem.mapper.ItemMapper;
import com.gljt.provideritem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @Author liwei
* @Date 2022/8/19 14:00
* @Version 1.0
*/

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;


    @Override
    public Item getItemById(Integer id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        return item;
    }

    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) {
        increaseSales(itemId,amount);
        itemMapper.decreaseStock(itemId,amount);
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increaseSales(Integer itemId, Integer amount) {
        itemMapper.increaseSales(itemId, amount);
    }
}
