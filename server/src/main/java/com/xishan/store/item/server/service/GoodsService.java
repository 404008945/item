package com.xishan.store.item.server.service;

import com.xishan.store.item.api.model.Goods;
import com.xishan.store.item.api.request.FindByGoodRequest;
import com.xishan.store.item.api.response.GoodComplexResponse;
import com.xishan.store.item.server.mapper.BrandMapper;
import com.xishan.store.item.server.mapper.CategoriesMapper;
import com.xishan.store.item.server.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * service查询出来的需要是复杂组合对象
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoriesMapper categoriesMapper;

    /**
     * 查询good并关联类目和品牌
     * @param findByGoodRequest
     * @return
     */
    public GoodComplexResponse findByGoodId(FindByGoodRequest findByGoodRequest){
        if(findByGoodRequest == null){
        }
        long goodId=findByGoodRequest.getId();
      //  Goods goods = goodsMapper.selectByPrimaryKey(goodId);

        return null;

    }


}
