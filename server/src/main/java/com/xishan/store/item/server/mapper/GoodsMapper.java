package com.xishan.store.item.server.mapper;


import com.xishan.store.item.api.model.Goods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);
}