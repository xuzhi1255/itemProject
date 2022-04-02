package com.item.dao;
import com.item.bean.ItemSearch;
import com.item.model.Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);

    int countByItemNumber(@Param("itemNumber")String itemNumber);

    List<Item> findByAll(ItemSearch itemSearch);
    Long countByIdNotAndItemNumber(@Param("notId")Long notId,@Param("itemNumber")String itemNumber);










}