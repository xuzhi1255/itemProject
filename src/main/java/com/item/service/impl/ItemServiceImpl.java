package com.item.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.item.bean.CommonReply;
import com.item.bean.ItemRequest;
import com.item.bean.ItemSearch;
import com.item.dao.ItemMapper;
import com.item.model.Item;
import com.item.service.ItemService;
import com.item.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *@Description : item服务实现类
 *@Author : Zhilin_Xu
 *@Date : 2022/3/28 10:58
**/
@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapper itemMapper;

    @Override
    @Transactional
    public synchronized CommonReply addItem(ItemRequest itemRequest) throws Exception {
        //将请求的bean转换为entity
        Item itemModel = createItemModel(itemRequest);
        //检查itemNumber是否重复
        int itemCount = itemMapper.countByItemNumber(itemModel.getItemNumber());
        if(itemCount > 0){
            log.info("商品号码 {} 已经存在",itemModel.getItemNumber());
            throw new Exception("itemNumber" + itemModel.getItemNumber()+"已经存在");
        }
        int addCount = itemMapper.insertSelective(itemModel);
        if(addCount != 1){
            throw new Exception("itemNumber" + itemModel.getItemNumber()+"添加失败");
        }
        return CommonUtils.buildResp(200,"添加成功",null);
    }

    /**
     * @Description : 根据id删除item
     * @Author : Zhilin_Xu
     * @Date : 2022/3/28 17:15
     */
    @Override
    @Transactional
    public CommonReply deleteItemById(Long id) throws Exception {
        //先查询商品是否存在
        Item item = itemMapper.selectByPrimaryKey(id);
        if(ObjectUtil.isNull(item)){
            throw new Exception("id " + id +"不存在");
        }
        //再执行删除
        itemMapper.deleteByPrimaryKey(id);
        return CommonUtils.buildResp(200,"删除成功",null);
    }

    /**
     * @Description : 更新商品
     * @Author : Zhilin_Xu
     * @Date : 2022/3/28 17:15
     */
    @Override
    @Transactional
    public CommonReply updateItemById(ItemRequest itemRequest) throws Exception {
        //检查一些东西是否符合规范
        if(ObjectUtil.isNull(itemRequest.getId())){
            throw new Exception("商品id没有传输");
        }

        //先查询商品是否存在
        Item item = itemMapper.selectByPrimaryKey(itemRequest.getId());
        if(ObjectUtil.isNull(item)){
            throw new Exception("id " + itemRequest.getId() +"不存在");
        }
        //将请求的bean转换为entity
        Item itemModel = createItemModel(itemRequest);
        //再执行更新
        itemMapper.updateByPrimaryKeySelective(itemModel);
        return CommonUtils.buildResp(200,"更新商品成功",null);
    }


    /**
     * @param id
     * @Description : 根据id得到商品信息
     * @Author : Zhilin_Xu
     * @Date : 2022/3/29 14:19
     */
    @Override
    public CommonReply getItemById(Long id) throws Exception {
        Item item = itemMapper.selectByPrimaryKey(id);
        return CommonUtils.buildResp(200,"查询成功",JSONUtil.toJsonStr(item));
    }

    /**
     * @param itemSearch
     * @Description : 查询所有符合条件的商品
     * @Author : Zhilin_Xu
     * @Date : 2022/3/29 14:27
     */
    @Override
    public CommonReply searchAllItems(ItemSearch itemSearch) throws Exception {
        //赋予page和limit默认值
        if(ObjectUtil.isNull(itemSearch.getPage())){
            itemSearch.setPage(0);
        }else{
            itemSearch.setPage(itemSearch.getPage() - 1);
        }
        if(ObjectUtil.isNull(itemSearch.getLimit())){
            itemSearch.setLimit(10);
        }
        //得到所有符合条件记录
        List<Item> items =  itemMapper.findByAll(itemSearch);
        return CommonUtils.buildResp(200,"查询成功",JSONUtil.toJsonStr(items));
    }

    /**
     * @param itemSearch
     * @Description : 导出所有记录
     * @Author : Zhilin_Xu
     * @Date : 2022/3/29 14:56
     */
    @Override
    public CommonReply exportAllRecords(HttpServletResponse response,ItemSearch itemSearch) throws Exception {

        return null;
    }


    private Item createItemModel(ItemRequest request) throws Exception {
        Item item = new Item();
        BeanUtil.copyProperties(request,item);
        if(StrUtil.isNotBlank(request.getCreateTime())){
            try{
                item.setCreateTime(DateUtil.parse(request.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            }catch (Exception e){
                throw new Exception("createTime时间格式错误");
            }

        }
        if(StrUtil.isNotBlank(request.getEditTime())){
            try{
                item.setEditTime(DateUtil.parse(request.getEditTime(),"yyyy-MM-dd HH:mm:ss"));
            }catch (Exception e){
                throw new Exception("editTime时间格式错误");
            }

        }
        return item;
    }



}
