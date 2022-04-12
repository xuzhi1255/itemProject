package com.item.listener;

import cn.hutool.json.JSONUtil;
import com.item.message.PoReceiveMessage;
import com.item.service.PurchaseOrderService;
import com.item.util.RedisUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description : 采购单消息接收器
 * @Author : Zhilin_Xu
 * @Date : 2022/4/11 10:24
 **/
@Component
@Slf4j
public class PoReceiveListener {

    @Autowired
    PurchaseOrderService productOrderService;

    @Autowired
    RedisUtil redisUtil;

    @RabbitListener(queuesToDeclare = @Queue(value = "PO_RECEIVE_QUEUE"))
    @RabbitHandler
    public void process(Channel channel, Message message) throws IOException {
        String messageId = message.getMessageProperties().getMessageId();
        try {
            //检查消息是否重复消费
            Long count = redisUtil.sadd("PoNumberReceived", message.getMessageProperties().getMessageId());
            if (count == 0) {
                log.info("MessageId {} 重复消费", messageId);
                return;
            }
            //将消息化为实体，并进行处理
            String msg = new String(message.getBody(), "UTF-8");
            log.info("MessageId : {} , get PoNumberReceived msg = {}", messageId, msg);
            PoReceiveMessage poReceiveMessage = JSONUtil.toBean(msg, PoReceiveMessage.class);
            productOrderService.processPoMessage(poReceiveMessage);
            //处理完消息手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("MessageId : {} consumed finish", messageId);
        } catch (Exception e) {
            log.error("MessageId : {}, PoReceiveListener error ", messageId, e);
            redisUtil.srem("PoNumberReceived", message.getMessageProperties().getMessageId());
            //处理失败拒绝消息，重回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),
                    false, true);
        }
    }
}
