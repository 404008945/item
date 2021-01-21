package com.xishan.store.item.web.controller;

import com.xishan.store.base.annoation.Authority;
import com.xishan.store.base.annoation.ResponseJsonFormat;
import com.xishan.store.base.exception.ServiceException;
import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.GoodSkuReadFacade;
import com.xishan.store.item.api.facade.GoodSkuWriteFacade;
import com.xishan.store.item.api.request.BuySkuRequest;
import com.xishan.store.item.api.request.FindGoodSkuRequest;
import com.xishan.store.item.api.request.GoodSkuUpdateRequest;
import com.xishan.store.item.api.response.BuySkuResponse;
import com.xishan.store.item.api.response.FindGoodsSkuResponse;
import com.xishan.store.item.api.response.GoodsSkuDTO;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value="sku",tags={"sku操作接口"})
@RequestMapping("/goodsSku")
@Controller
public class GoodsSkuController {
    @Reference
    private GoodSkuReadFacade goodSkuReadFacade;

    @Reference
    private GoodSkuWriteFacade goodSkuWriteFacade;

    @PostMapping("/findById")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public FindGoodsSkuResponse findById(FindGoodSkuRequest request){
        Response<FindGoodsSkuResponse> response = goodSkuReadFacade.findById(request);
        if(!response.isSuccess()){
            throw new ServiceException("查找失败"+response.getMessage());
        }
        return response.getData();
    }

    @PostMapping("/listByGoodsId")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public List<GoodsSkuDTO> listByGoodsId(FindGoodSkuRequest request){
        Response<List<GoodsSkuDTO>> response = goodSkuReadFacade.listByGoodsId(request);
        if(!response.isSuccess()){
            throw new ServiceException("查找失败"+response.getMessage());
        }
        return response.getData();
    }

    @PostMapping("/insert")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public  String insert(GoodSkuUpdateRequest request){
        Response<Integer> response = goodSkuWriteFacade.insert(request);

        if(!response.isSuccess()){
            throw new ServiceException("插入失败"+response.getMessage());
        }
        return "插入成功";

    }

    @PostMapping("/update")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public  String update(GoodSkuUpdateRequest request){
        Response<Integer> response = goodSkuWriteFacade.update(request);

        if(!response.isSuccess()){
            throw new ServiceException("更新失败"+response.getMessage());
        }
        return "更新成功";
    }

    @PostMapping("/delete")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public String delete(GoodSkuUpdateRequest request){
        Response<Integer> response = goodSkuWriteFacade.delete(request);

        if(!response.isSuccess()){
            throw new ServiceException("删除失败"+response.getMessage());
        }
        return "删除成功";
    }

    @PostMapping("/buyGoods")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public BuySkuResponse buyGoods(BuySkuRequest buySkuRequest){
        Response<BuySkuResponse> response = goodSkuWriteFacade.buyGoods(buySkuRequest);
        if(!response.isSuccess()){
            throw new ServiceException("购买失败"+response.getMessage());
        }
        return response.getData();
    }

}
