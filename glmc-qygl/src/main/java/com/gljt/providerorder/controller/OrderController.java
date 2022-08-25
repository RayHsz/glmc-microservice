package com.gljt.providerorder.controller;

import com.gljt.providerorder.error.BusinessException;
import com.gljt.providerorder.error.ErrorEnum;
import com.gljt.providerorder.mq.MQProducer;
import com.gljt.providerorder.result.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
* @Author liwei
* @Date 2022/8/19 14:03
* @Version 1.0
*/

@RestController()
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private MQProducer mqProducer;

    @RequestMapping(value = "/createOrder", method = {RequestMethod.POST})
    @ResponseBody
    public CommonResult createOrder(@RequestParam(name = "itemId") Integer itemId,
                                    @RequestParam(name = "amount") Integer amount) {

        // 完成事务消息
        if (!mqProducer.transactionAsyncReduceStock(1111, itemId, amount)) {
            throw new BusinessException(ErrorEnum.TRANSACTION_FAIL, "下单失败");
        }
        return CommonResult.success();
    }
}