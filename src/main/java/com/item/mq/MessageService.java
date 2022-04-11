package com.item.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description : 发送消息实现类
 * @Author : Zhilin_Xu
 * @Date : 2022/4/11 10:05
 **/
@Component
@Slf4j
public class MessageService implements ConfirmCallback, ReturnsCallback {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendMessage(String queneName, Object msg) {
        //消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);
        //消息消费者确认收到消息后，手动ack回执
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
        //发送消息
        rabbitTemplate.convertAndSend(queneName, msg);
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("发送者已经收到确认，ack={}, cause={}", ack, cause);
        } else {
            log.info("---- confirm ----ack==false  cause=" + cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        System.out.println("退回的消息是：" + new String(returnedMessage.getMessage().getBody()));
        System.out.println("退回的replyCode是：" + returnedMessage.getReplyCode());
        System.out.println("退回的replyText是：" + returnedMessage.getReplyText());
        System.out.println("退回的exchange是：" + returnedMessage.getExchange());
        System.out.println("退回的routingKey是：" + returnedMessage.getRoutingKey());
    }
}

