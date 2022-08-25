package com.gljt.providerorder.mapper;

import com.gljt.providerorder.bean.Order;
import org.apache.ibatis.annotations.Mapper;

/**
* @Author liwei
* @Date 2022/8/19 14:05
* @Version 1.0
*/
@Mapper
public interface OrderMapper {
    int insert(Order order);
}