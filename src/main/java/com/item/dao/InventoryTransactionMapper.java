package com.item.dao;
import org.apache.ibatis.annotations.Param;

import com.item.model.InventoryTransaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InventoryTransactionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InventoryTransaction record);

    int insertSelective(InventoryTransaction record);

    InventoryTransaction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InventoryTransaction record);

    int updateByPrimaryKey(InventoryTransaction record);

    InventoryTransaction selectOneByItemNumber(@Param("itemNumber")String itemNumber);




}