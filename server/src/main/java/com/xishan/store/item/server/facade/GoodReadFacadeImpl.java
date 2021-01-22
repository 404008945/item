package com.xishan.store.item.server.facade;

import com.xishan.store.base.page.Paging;
import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.GoodReadFacade;
import com.xishan.store.item.api.request.FindByGoodRequest;
import com.xishan.store.item.api.request.GoodsPageingRequest;
import com.xishan.store.item.api.response.GoodComplexDTO;
import com.xishan.store.item.api.response.GoodDetailComplexDTO;
import com.xishan.store.item.server.service.GoodsService;
import com.xishan.store.item.server.util.BeanUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class GoodReadFacadeImpl implements GoodReadFacade {

    @Autowired
    private GoodsService goodsService;


    @Override
    public Response<GoodComplexDTO> findByGoodId(FindByGoodRequest findByGoodRequest) {
        try {
            return Response.ok(goodsService.findByGoodId(findByGoodRequest), GoodComplexDTO.class);
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }
    }

    @Override
    public Response<Paging<GoodComplexDTO>> paging( GoodsPageingRequest goodsPageingRequest) {
        try {
            GoodComplexDTO goodComplexDTO = BeanUtil.convertToBean(goodsPageingRequest,GoodComplexDTO.class);
            Paging<GoodComplexDTO> paging = goodsService.paging(goodComplexDTO,goodsPageingRequest.getPageNo(),goodsPageingRequest.getPageSize());
            return Response.ok(paging);
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }
    }

    @Override
    public Response<GoodDetailComplexDTO> getGoodDetail(FindByGoodRequest findByGoodRequest) {
        try {
            return Response.ok(goodsService.getGoodDetail(findByGoodRequest));
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }
    }
}
