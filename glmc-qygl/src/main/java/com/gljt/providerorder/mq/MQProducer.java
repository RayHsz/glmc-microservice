package com.gljt.providerorder.mq;

import com.alibaba.fastjson.JSON;
import com.gljt.providerorder.service.OrderService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
* @Author liwei
* @Date 2022/8/19 14:05
* @Version 1.0
*/

@Component
public class MQProducer {
    //    IoC容器管理我们的Bean
    @Value("${mq.nameserver.addr}")
    private String nameAddr;

    @Value("${mq.topicname}")
    private String topicName;

    private TransactionMQProducer transactionMQProducer;

    @Autowired
    private OrderService orderService;
    /**
     * 构造函数后
     *
     * @throws MQClientException
     */
    @PostConstruct
    public void init() throws MQClientException {
        transactionMQProducer = new TransactionMQProducer("stock_producer_group");
        transactionMQProducer.setNamesrvAddr(nameAddr);
        transactionMQProducer.start();
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            /**
             * 执行本地事务，只要这个方法返回COMMIT_MESSAGE，意味着本地事务执行成功
             * @param message
             * @param o
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                String jsonString = new String(message.getBody());
                System.out.println("提交事务json: " + jsonString);
                Integer itemId = (Integer) ((Map) o).get("itemId");
                Integer userId = (Integer) ((Map) o).get("userId");
                Integer amount = (Integer) ((Map) o).get("amount");
                try {
                    orderService.createOrder(userId, itemId, amount);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("本地事务出了异常");
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("事务消息回查");
                return null;
            }
        });
    }

    public boolean transactionAsyncReduceStock(Integer userId, Integer itemId, Integer amount) {
        /**
         * 消息组装
         */
        Map<String, Object> messageBody = new HashMap<>();
        messageBody.put("itemId", itemId);
        messageBody.put("amount", amount);

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("userId", userId);
        bodyMap.put("itemId", itemId);
        bodyMap.put("amount", amount);

        // 取时间的纳秒作为Message的key，用于幂等性处理
        Message message = new Message(topicName, "increase", String.valueOf(System.nanoTime()),
                JSON.toJSON(messageBody).toString().getBytes(Charset.forName("UTF-8")));
        try {
            TransactionSendResult result = transactionMQProducer.sendMessageInTransaction(message, bodyMap);
            if (result.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE) {
                return true;
            }
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
