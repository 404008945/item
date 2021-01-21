package com.xishan.store.item.server.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.GoodWriteFacade;
import com.xishan.store.item.api.model.Goods;
import com.xishan.store.item.api.request.DeleteGoodByIdRequest;
import com.xishan.store.item.api.request.GoodUpdateRequest;
import com.xishan.store.item.server.service.GoodsService;
import com.xishan.store.item.server.util.BeanUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class GoodWriteFacadeImpl implements GoodWriteFacade {

    @Autowired
    private GoodsService goodsService;

    @Override
    public Response<Integer> update(GoodUpdateRequest request) {
        try {
            Goods goods = BeanUtil.convertToBean(request , Goods.class);
            return Response.ok(goodsService.update(goods));
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }
    }

    @Override
    public Response<Integer> insert(GoodUpdateRequest request) {
        try {
            Goods goods = BeanUtil.convertToBean(request , Goods.class);
            return Response.ok(goodsService.insert(goods));
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }
    }

    @Override
    public Response<Integer> delete(DeleteGoodByIdRequest request) {
        try {
            return Response.ok(goodsService.delete(request));
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }
    }
}
