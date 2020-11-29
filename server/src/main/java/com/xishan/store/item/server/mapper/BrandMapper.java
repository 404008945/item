package com.xishan.store.item.server.mapper;

import com.xishan.store.item.api.model.Brand;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);
}