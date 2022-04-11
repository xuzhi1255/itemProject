package com.item;

import cn.hutool.json.JSONUtil;
import com.item.model.Item;
import com.item.mq.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemApplicationTests {

    @Autowired
    MessageService messageService;

    @Test
    void contextLoads() {
        Item item = new Item();
        item.setId(Long.valueOf(11111));
        item.setItemNumber("asdadadadadsa");
        messageService.sendMessage("PO_RECEIVE_QUEUE", JSONUtil.toJsonStr(item));
    }

}
