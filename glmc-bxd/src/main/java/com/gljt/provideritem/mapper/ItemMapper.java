package com.gljt.provideritem.mapper;

import com.gljt.provideritem.bean.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @Author liwei
* @Date 2022/8/19 14:00
* @Version 1.0
*/

@Mapper
public interface ItemMapper {

    Item selectByPrimaryKey(Integer id);

    int increaseSales(@Param("id") Integer id, @Param("amount") Integer amount);
    /**
     * 减少库存
     * @param itemId
     * @param amount
     * @return
     */
    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}