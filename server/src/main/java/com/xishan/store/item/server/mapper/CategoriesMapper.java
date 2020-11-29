package com.xishan.store.item.server.mapper;

import com.xishan.store.item.api.model.Categories;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoriesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Categories record);

    int insertSelective(Categories record);

    Categories selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Categories record);

    int updateByPrimaryKey(Categories record);
}