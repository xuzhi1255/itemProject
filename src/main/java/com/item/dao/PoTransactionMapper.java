package com.item.dao;

import com.item.model.PoTransaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PoTransactionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PoTransaction record);

    int insertSelective(PoTransaction record);

    PoTransaction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PoTransaction record);

    int updateByPrimaryKey(PoTransaction record);
}