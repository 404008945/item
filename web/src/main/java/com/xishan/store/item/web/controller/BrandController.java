package com.xishan.store.item.web.controller;

import com.xishan.store.base.annoation.Authority;
import com.xishan.store.base.annoation.ResponseJsonFormat;
import com.xishan.store.base.exception.RestException;
import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.BrandReadFacade;
import com.xishan.store.item.api.facade.BrandWriteFacade;
import com.xishan.store.item.api.request.BrandUpdateRequest;
import com.xishan.store.item.api.request.FindBrandRequest;
import com.xishan.store.item.api.response.FindBrandResponse;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value="商品品牌",tags={"品牌操作接口"})
@RequestMapping("/brand")
@Controller
public class BrandController {

    @Reference
    private BrandReadFacade brandReadFacade;

    @Reference
    private BrandWriteFacade brandWriteFacade;


    @PostMapping("/findById")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public FindBrandResponse findById(FindBrandRequest findBrandRequest){//web层，不返回response
        Response<FindBrandResponse> response = brandReadFacade.findById(findBrandRequest);
        if(!response .isSuccess()){
            throw new RestException("查询失败"+response.getMessage());
        }
        return response.getData();
    }

    @PostMapping("/update")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    String update(BrandUpdateRequest brandUpdateRequest){
        Response<Integer> response = brandWriteFacade.update(brandUpdateRequest);
        if (!response.isSuccess()) {
            throw new RestException("更新失败"+response.getMessage());
        }
        return "更新成功";
    }

    @PostMapping("/insert")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    String insert(BrandUpdateRequest brandUpdateRequest){
        Response<Integer> response = brandWriteFacade.insert(brandUpdateRequest);
        if (!response.isSuccess()) {
            throw new RestException("插入失败"+response.getMessage());
        }
        return "插入成功";
    }

    @PostMapping("/delete")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    String delete(BrandUpdateRequest brandUpdateRequest) {
        Response<Integer> response = brandWriteFacade.delete(brandUpdateRequest);
        if (!response.isSuccess()) {
            throw new RestException("删除失败" + response.getMessage());
        }
        return "删除成功";
    }




}
