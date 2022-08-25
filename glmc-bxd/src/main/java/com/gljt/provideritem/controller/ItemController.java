package com.gljt.provideritem.controller;


import com.gljt.provideritem.bean.Item;
import com.gljt.provideritem.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
* @Author liwei
* @Date 2022/8/19 14:02
* @Version 1.0
*/

@RequestMapping("/item")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public Item findById(@PathVariable Integer id) {
        return itemService.getItemById(id);
    }
}
