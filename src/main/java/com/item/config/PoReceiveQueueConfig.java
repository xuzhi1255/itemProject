package com.item.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description : 采购单消息队列配置类
 * @Author : Zhilin_Xu
 * @Date : 2022/4/11 9:49
 **/
@Component
public class PoReceiveQueueConfig {
    public final static String PO_RECEIVE_QUEUE = "PO_RECEIVE_QUEUE";

    /**
     * work queue 模型
     */
    @Bean
    Queue workQueue() {
        return new Queue(PO_RECEIVE_QUEUE);
    }

}
