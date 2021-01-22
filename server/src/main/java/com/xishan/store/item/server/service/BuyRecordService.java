package com.xishan.store.item.server.service;


import com.xishan.store.base.exception.ServiceException;
import com.xishan.store.item.api.model.BuyRecord;
import com.xishan.store.item.server.mapper.BuyRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyRecordService {

    @Autowired
    private BuyRecordMapper buyRecordMapper;

    //根据 buy_id查找。和插入两个

    public BuyRecord  findByBuyId(String buyId){
        return buyRecordMapper.selectByBuyId(buyId);
    }


    public int insert(BuyRecord record){
        int n =  buyRecordMapper.insert(record);
        if(n <= 0){
            throw new ServiceException("插入购买记录失败");
        }
        return n;
    }

}
