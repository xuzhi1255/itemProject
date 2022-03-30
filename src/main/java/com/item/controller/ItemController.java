package com.item.controller;

import cn.hutool.json.JSONUtil;
import com.item.bean.CommonReply;
import com.item.bean.ItemRequest;
import com.item.bean.ItemSearch;
import com.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequestMapping("/items")
@Validated
public class ItemController {

    @Autowired
    ItemService itemService;




    /**
     *@Description : 增加商品controller
     *@Author : Zhilin_Xu
     *@Date : 2022/3/28 16:33
    **/
    @PostMapping("/addItem")
    public CommonReply addItem(@RequestBody @Validated ItemRequest itemRequest) throws Exception {
        log.info("addItems input : {}", JSONUtil.toJsonStr(itemRequest));
        CommonReply commonReply = itemService.addItem(itemRequest);
        log.info("addItems Output : {}", JSONUtil.toJsonStr(commonReply));
         return commonReply;
    }

    /**
     *@Description : 删除商品根据id
     *@Author : Zhilin_Xu
     *@Date : 2022/3/28 17:20
    **/
    @PostMapping(value = "/removeItemById")
    public CommonReply removeById(@NotNull(message = "id不能为空") Long id) throws Exception {
        log.info("deleteItemById input : {}", id);
        CommonReply commonReply = itemService.deleteItemById(id);
        log.info("deleteItemById Output : {}", JSONUtil.toJsonStr(commonReply));
        return commonReply;
    }

    /**
     *@Description : 根据id更改商品信息
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 10:27
    **/
    @PostMapping(value = "/updateItemById")
    public CommonReply updateItemById(@RequestBody ItemRequest itemRequest) throws Exception {
        log.info("updateItemById input : {}", JSONUtil.toJsonStr(itemRequest));
        CommonReply commonReply = itemService.updateItemById(itemRequest);
        log.info("updateItemById Output : {}", JSONUtil.toJsonStr(commonReply));
        return commonReply;
    }
    /**
     *@Description : 根据id查询商品信息
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 11:35
    **/
    @GetMapping(value = "/getItemById")
    public CommonReply getItemById(Long id) throws Exception {
        log.info("getItemById input : {}", id);
        CommonReply commonReply = itemService.getItemById(id);
        log.info("getItemById Output : {}", JSONUtil.toJsonStr(commonReply));
        return commonReply;
    }



    /**
     *@Description : 分页查询所有符合的商品
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 11:40
    **/
    @PostMapping(value = "/serachAllItems")
    public CommonReply serachAllItems(@RequestBody ItemSearch itemSearch) throws Exception {
        log.info("serachAllItems input : {}", JSONUtil.toJsonStr(itemSearch));
        CommonReply commonReply = itemService.searchAllItems(itemSearch);
        log.info("serachAllItems onput : {}", JSONUtil.toJsonStr(commonReply));
        return commonReply;
    }

    /**
     *@Description : 导出所有符合条件的记录
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 11:42
    **/
    @PostMapping(value ="/exportAllRecords")
    public void exportAllRecords(
            HttpServletResponse response,@RequestBody ItemSearch itemSearch) throws Exception {
        log.info("exportAllRecords input : {}", JSONUtil.toJsonStr(itemSearch));
        itemService.exportAllRecords(response,itemSearch);
    }





}
