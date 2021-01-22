package com.xishan.store.item.server.mapper;


import com.xishan.store.item.api.model.BuyRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuyRecord record);

    int insertSelective(BuyRecord record);

    BuyRecord selectByPrimaryKey(Integer id);

    BuyRecord selectByBuyId(String id);

    int updateByPrimaryKeySelective(BuyRecord record);

    int updateByPrimaryKey(BuyRecord record);
}