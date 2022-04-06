package com.item.service;

import com.item.bean.ItemRequest;
import com.item.bean.ItemSearch;
import com.item.model.Item;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *@Description : item服务类
 *@Author : Zhilin_Xu
 *@Date : 2022/3/28 10:58
 **/

public interface ItemService {
    /**
     *@Description : 添加商品
     *@Author : Zhilin_Xu
     *@Date : 2022/3/28 16:19
    **/
    void addItem(ItemRequest itemRequest) throws Exception;
    /**
     *@Description : 根据Id删除商品
     *@Author : Zhilin_Xu
     *@Date : 2022/3/28 17:15
    **/
    void deleteItemById(Long id) throws Exception;
    /**
     *@Description : 根据Id更新商品
     *@Author : Zhilin_Xu
     *@Date : 2022/3/28 17:15
     **/
    void updateItemById(ItemRequest itemRequest,Long id) throws Exception;
    /**
     *@Description : 根据id得到商品信息
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 14:19
    **/
    Item getItemById(Long id) throws Exception;

    /**
     *@Description : 查询所有符合条件的商品
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 14:27
    **/
    List<Item> searchAllItems(ItemSearch itemSearch) throws Exception;
    /**
     *@Description : 导出所有记录
     *@Author : Zhilin_Xu
     *@Date : 2022/3/29 14:56
    **/
    void exportAllRecords(HttpServletResponse response,ItemSearch itemSearch) throws Exception;




}
