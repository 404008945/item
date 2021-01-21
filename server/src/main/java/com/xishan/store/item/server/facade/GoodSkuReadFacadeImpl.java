package com.xishan.store.item.server.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.GoodSkuReadFacade;
import com.xishan.store.item.api.model.GoodsSku;
import com.xishan.store.item.api.request.FindGoodSkuRequest;
import com.xishan.store.item.api.response.FindGoodsSkuResponse;
import com.xishan.store.item.api.response.GoodsSkuDTO;
import com.xishan.store.item.server.service.GoodsSkuService;
import com.xishan.store.item.server.util.BeanUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class GoodSkuReadFacadeImpl implements GoodSkuReadFacade {

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Override
    public Response<FindGoodsSkuResponse> findById(FindGoodSkuRequest request) {
        try {
            GoodsSku req = BeanUtil.convertToBean(request,GoodsSku.class);
            GoodsSku goodsSku =  goodsSkuService.findById(req);
            return Response.ok(BeanUtil.convertToBean(goodsSku,FindGoodsSkuResponse.class));
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }
    }

    @Override
    public Response<List<GoodsSkuDTO>> listByGoodsId(FindGoodSkuRequest request) {
        try {
            GoodsSku req = BeanUtil.convertToBean(request,GoodsSku.class);
            return Response.ok(goodsSkuService.listByGoodsId(req));
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }
    }
}
