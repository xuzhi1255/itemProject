package com.item.dao;

import com.item.model.PoMaster;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PoMasterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PoMaster record);

    int insertSelective(PoMaster record);

    PoMaster selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PoMaster record);

    int updateByPrimaryKey(PoMaster record);
}