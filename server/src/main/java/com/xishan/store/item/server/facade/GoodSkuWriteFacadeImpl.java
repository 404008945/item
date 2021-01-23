package com.xishan.store.item.server.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.GoodSkuWriteFacade;
import com.xishan.store.item.api.model.GoodsSku;
import com.xishan.store.item.api.request.BuySkuRequest;
import com.xishan.store.item.api.request.GoodSkuUpdateRequest;
import com.xishan.store.item.api.response.BuySkuResponse;
import com.xishan.store.item.server.annotation.NeedRedisLock;
import com.xishan.store.item.server.service.GoodsSkuService;
import com.xishan.store.item.server.util.BeanUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoodSkuWriteFacadeImpl implements GoodSkuWriteFacade {

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Override
    public Response<Integer> insert(GoodSkuUpdateRequest request) {
        try {
            GoodsSku goodsSku = BeanUtil.convertToBean(request,GoodsSku.class);
            return  Response.ok(goodsSkuService.insert(goodsSku));
        }catch (Exception e){
            return  Response.fail(e.getMessage());
        }

    }

    @Override
    public Response<Integer> update(GoodSkuUpdateRequest request) {
        try {
            GoodsSku goodsSku = BeanUtil.convertToBean(request,GoodsSku.class);
            return  Response.ok(goodsSkuService.update(goodsSku));
        }catch (Exception e){
            return  Response.fail(e.getMessage());
        }
    }

    @Override
    public Response<Integer> delete(GoodSkuUpdateRequest request) {
        try {
            GoodsSku goodsSku = BeanUtil.convertToBean(request,GoodsSku.class);
            return  Response.ok(goodsSkuService.delete(goodsSku));
        }catch (Exception e){
            return  Response.fail(e.getMessage());
        }
    }

    @NeedRedisLock(value = "buySkuRequest.uuid",key = "buySkuRequest.skuId")
    @Transactional
    @Override
    public Response<BuySkuResponse> buyGoods(BuySkuRequest buySkuRequest) {
        try {
            return  Response.ok(goodsSkuService.buyGoods(buySkuRequest));
        }catch (Exception e){
            return  Response.fail(e.getMessage());
        }
    }
}
