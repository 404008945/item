package com.xishan.store.item.server.mapper;


import com.xishan.store.item.api.model.GoodsSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsSkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsSku record);

    int insertSelective(GoodsSku record);

    GoodsSku selectByPrimaryKey(Integer id);

    List<GoodsSku> selectByGoodsId(GoodsSku record);

    int updateByPrimaryKeySelective(GoodsSku record);

    int updateByPrimaryKey(GoodsSku record);
}