package com.gljt.provideritem.mq;

import com.alibaba.fastjson.JSON;
import com.gljt.provideritem.service.ItemService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
* @Author liwei
* @Date 2022/8/19 14:02
* @Version 1.0
*/

@Component
public class MQConsumer {

    private DefaultMQPushConsumer consumer;

    @Value("${mq.nameserver.addr}")
    private String nameAddr;

    @Value("${mq.topicname}")
    private String topicName;

    @Autowired
    private ItemService itemService;


    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer("stock_consumer_group");
        consumer.setNamesrvAddr(nameAddr);
        consumer.subscribe(topicName, "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                //实现库存扣减逻辑
                for (Message msg : msgs) {
                    String jsonString = new String(msg.getBody());
                    System.out.println("接受减库存消息: " + jsonString);
                    Map<String, Object> map = JSON.parseObject(jsonString, Map.class);
                    Integer itemId = (Integer) map.get("itemId");
                    Integer amount = (Integer) map.get("amount");
                    // 减库存
                    itemService.decreaseStock(itemId, amount);
                    System.out.println("减库存成功: " + jsonString);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}