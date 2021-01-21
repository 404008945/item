package com.xishan.store.item.web.controller;

import com.xishan.store.base.annoation.Authority;
import com.xishan.store.base.annoation.ResponseJsonFormat;
import com.xishan.store.base.exception.RestException;
import com.xishan.store.base.page.Paging;
import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.GoodReadFacade;
import com.xishan.store.item.api.facade.GoodWriteFacade;
import com.xishan.store.item.api.request.DeleteGoodByIdRequest;
import com.xishan.store.item.api.request.FindByGoodRequest;
import com.xishan.store.item.api.request.GoodUpdateRequest;
import com.xishan.store.item.api.response.GoodComplexDTO;
import com.xishan.store.item.api.response.GoodDetailComplexDTO;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Api(value="商品",tags={"商品操作接口"})
@RequestMapping("/goods")
@Controller
public class GoodsController {

    @Reference
    private GoodReadFacade goodReadFacade;

    @Reference
    private GoodWriteFacade goodWriteFacade;


    @PostMapping("/findByGoodId")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public GoodComplexDTO findByGoodId(FindByGoodRequest findByGoodRequest){
        Response<GoodComplexDTO> response = goodReadFacade.findByGoodId(findByGoodRequest);
        if(response.isSuccess()){
            throw new RestException("查找失败"+response.getMessage());
        }
        return  response.getData();
    }

    @PostMapping("/paging")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public Paging<GoodComplexDTO> paging(GoodComplexDTO goodComplexDTO){
        Response<Paging<GoodComplexDTO>> response = goodReadFacade.paging(goodComplexDTO);
        if(!response.isSuccess()){
            throw new RestException("查找失败"+response.getMessage());
        }
        return  response.getData();
    }
    @PostMapping("/getGoodDetail")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public GoodDetailComplexDTO getGoodDetail(FindByGoodRequest  findByGoodRequest){
        Response<GoodDetailComplexDTO> response = goodReadFacade.getGoodDetail(findByGoodRequest);
        if(response.isSuccess()){
            throw new RestException("查找失败"+response.getMessage());
        }
        return  response.getData();
    }

    @PostMapping("/update")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public String update(GoodUpdateRequest request){
        Response<Integer> response =  goodWriteFacade.update(request);
        if(!response.isSuccess()){
            throw new RestException("更新失败："+ response.getMessage());
        }
        return "更新成功";
    }
    @PostMapping("/insert")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public String insert(GoodUpdateRequest request){
        Response<Integer> response =  goodWriteFacade.insert(request);
        if(!response.isSuccess()){
            throw new RestException("新增失败："+ response.getMessage());
        }
        return "新增成功";
    }
    @PostMapping("/delete")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public String delete(DeleteGoodByIdRequest request){
        Response<Integer> response =  goodWriteFacade.delete(request);
        if(!response.isSuccess()){
            throw new RestException("删除失败："+ response.getMessage());
        }
        return "删除成功";
    }
}
