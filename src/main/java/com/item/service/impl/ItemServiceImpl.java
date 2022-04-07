package com.item.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.item.bean.ItemRequest;
import com.item.bean.ItemSearch;
import com.item.dao.ItemMapper;
import com.item.model.Item;
import com.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description : item服务实现类
 * @Author : Zhilin_Xu
 * @Date : 2022/3/28 10:58
 **/
@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapper itemMapper;

    @Override
    public synchronized void addItem(ItemRequest itemRequest) throws Exception {
        //将请求的bean转换为entity
        Item itemModel = createItemModel(itemRequest);
        //检查itemNumber是否重复
        int itemCount = itemMapper.countByItemNumber(itemModel.getItemNumber());
        if (itemCount > 0) {
            log.info("商品号码 {} 已经存在", itemModel.getItemNumber());
            throw new Exception("itemNumber" + itemModel.getItemNumber() + "已经存在");
        }
        int addCount = itemMapper.insertSelective(itemModel);
        if (addCount != 1) {
            throw new Exception("itemNumber" + itemModel.getItemNumber() + "添加失败");
        }
    }

    /**
     * @Description : 根据id删除item
     * @Author : Zhilin_Xu
     * @Date : 2022/3/28 17:15
     */
    @Override
    public void deleteItemById(Long id) throws Exception {
        //先查询商品是否存在
        Item item = itemMapper.selectByPrimaryKey(id);
        if (ObjectUtil.isNull(item)) {
            throw new Exception("id " + id + "不存在");
        }
        //再执行删除
        itemMapper.deleteByPrimaryKey(id);
    }

    /**
     * @Description : 更新商品
     * @Author : Zhilin_Xu
     * @Date : 2022/3/28 17:15
     */
    @Override
    public void updateItemById(ItemRequest itemRequest, Long id) throws Exception {
        //检查一些东西是否符合规范
        if (ObjectUtil.isNull(id)) {
            throw new Exception("商品id没有传输");
        }

        //先查询商品是否存在
        Item item = itemMapper.selectByPrimaryKey(id);
        if (ObjectUtil.isNull(item)) {
            throw new Exception("id " + id + "不存在");
        }
        //如果请求更改itemNumber,检查itemNumber是否唯一
        if (StrUtil.isNotBlank(itemRequest.getItemNumber())) {
            Long itemCount = itemMapper.countByIdNotAndItemNumber(id, itemRequest.getItemNumber());
            if (itemCount > 0) {
                log.info("商品号码 {} 已经存在", itemRequest.getItemNumber());
                throw new Exception("itemNumber" + itemRequest.getItemNumber() + "已经存在, 不能更新");
            }
        }
        //将请求的bean转换为entity
        Item itemModel = createItemModel(itemRequest);
        itemModel.setId(id);
        //再执行更新
        itemMapper.updateByPrimaryKeySelective(itemModel);
    }


    /**
     * @param id
     * @Description : 根据id得到商品信息
     * @Author : Zhilin_Xu
     * @Date : 2022/3/29 14:19
     */
    @Override
    public Item getItemById(Long id) throws Exception {
        Item item = itemMapper.selectByPrimaryKey(id);
        return item;
    }

    /**
     * @param itemSearch
     * @Description : 查询所有符合条件的商品
     * @Author : Zhilin_Xu
     * @Date : 2022/3/29 14:27
     */
    @Override
    public List<Item> searchAllItems(ItemSearch itemSearch) throws Exception {
        //对页数和每页限制条数进行校验
        if (ObjectUtil.isNotEmpty(itemSearch.getPage())) {
            if (itemSearch.getPage() < 1) {
                itemSearch.setPage(0);
            } else {
                itemSearch.setPage(itemSearch.getPage() - 1);
            }
        }

        if (ObjectUtil.isNotEmpty(itemSearch.getLimit())) {
            if (itemSearch.getLimit() <= 0) {
                itemSearch.setLimit(10);
            }
        }
        //得到所有符合条件记录
        List<Item> items = itemMapper.findByAll(itemSearch);
        return items;
    }

    /**
     * @param itemSearch
     * @Description : 导出所有记录
     * @Author : Zhilin_Xu
     * @Date : 2022/3/29 14:56
     */
    @Override
    public void exportAllRecords(HttpServletResponse response, ItemSearch itemSearch) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "Item" + System.currentTimeMillis() + ".xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), Item.class).build();
        int pageNumber = 1;
        int pageSize = 10000;
        int dataLength = pageSize;
        List<Item> data = null;
        while (dataLength == pageSize) {
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet" + pageNumber).build();
            itemSearch.setPage(pageNumber - 1);
            itemSearch.setLimit(pageSize);
            data = itemMapper.findByAll(itemSearch);  //分页查询
            excelWriter.write(data, writeSheet);
            if (null == data || data.isEmpty()) {
                break;
            }
            dataLength = data.size();
            pageNumber++;
        }
        excelWriter.finish();

    }


    private Item createItemModel(ItemRequest request) throws Exception {
        Item item = new Item();
        BeanUtil.copyProperties(request, item);
        return item;
    }


}
