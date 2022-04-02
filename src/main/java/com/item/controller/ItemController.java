package com.item.controller;

import com.item.bean.CommonReply;
import com.item.bean.ItemRequest;
import com.item.bean.ItemSearch;
import com.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

@RestController
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
    @PostMapping
    public CommonReply addItem(@RequestBody @Validated ItemRequest itemRequest) throws Exception {
        CommonReply commonReply = itemService.addItem(itemRequest);
        return commonReply;
    }

    /**
     *@Description : 删除商品根据id
     *@Author : Zhilin_Xu
     *@Date : 2022/3/28 17:20
    **/
    @DeleteMapping(value = "/{id}")
    public CommonReply removeById(@PathVariable("id") @NotNull(message = "id不能为空") Long id) throws Exception {
        CommonReply commonReply = itemService.deleteItemById(id);
        return commonReply;
    }

    /**
     *@Description : 根据id更改商品信息
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 10:27
    **/
    @PutMapping(value = "/{id}")
    public CommonReply updateItemById(@PathVariable("id") @NotNull(message = "id不能为空") Long id ,
                                      @RequestBody ItemRequest itemRequest) throws Exception {
        CommonReply commonReply = itemService.updateItemById(itemRequest,id);
        return commonReply;
    }
    /**
     *@Description : 根据id查询商品信息
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 11:35
    **/
    @GetMapping(value = "/{id}")
    public CommonReply getItemById(@PathVariable("id") Long id) throws Exception {
        CommonReply commonReply = itemService.getItemById(id);
        return commonReply;
    }



    /**
     *@Description : 分页查询所有符合的商品
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 11:40
    **/
    @PostMapping(value = "/query")
    public CommonReply serachAllItems(@RequestBody ItemSearch itemSearch) throws Exception {
        CommonReply commonReply = itemService.searchAllItems(itemSearch);
        return commonReply;
    }

    /**
     *@Description : 导出所有符合条件的记录
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 11:42
    **/
    @PostMapping(value ="/export")
    public void exportAllRecords(
            HttpServletResponse response,@RequestBody ItemSearch itemSearch) throws Exception {
        itemService.exportAllRecords(response,itemSearch);
    }





}
