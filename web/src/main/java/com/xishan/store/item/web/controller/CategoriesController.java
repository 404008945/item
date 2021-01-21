package com.xishan.store.item.web.controller;

import com.xishan.store.base.annoation.Authority;
import com.xishan.store.base.annoation.ResponseJsonFormat;
import com.xishan.store.base.exception.RestException;
import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.CategoriesReadFacade;
import com.xishan.store.item.api.facade.CategoriesWriteFacade;
import com.xishan.store.item.api.request.CategoriesUpdateRequest;
import com.xishan.store.item.api.request.FindCategoriesRequest;
import com.xishan.store.item.api.response.FindCategoriesResponse;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value="商品类目",tags={"类目操作接口"})
@RequestMapping("/categories")
@Controller
public class CategoriesController {

    @Reference
    private CategoriesReadFacade categoriesReadFacade;

    @Reference
    private CategoriesWriteFacade categoriesWriteFacade;

    @PostMapping("/findById")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public FindCategoriesResponse findById(FindCategoriesRequest request){
        Response<FindCategoriesResponse> responseResponse = categoriesReadFacade.findById(request);
        if(!responseResponse.isSuccess()){
            throw new RestException("查找失败："+ responseResponse.getMessage());
        }
        return responseResponse.getData();
    }

    @PostMapping("/update")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public String update(CategoriesUpdateRequest categoriesUpdateRequest){
        Response<Integer> response =  categoriesWriteFacade.update(categoriesUpdateRequest);
        if(!response.isSuccess()){
            throw new RestException("更新失败："+ response.getMessage());
        }
        return "更新成功";
    }

    @PostMapping("/insert")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public String insert(CategoriesUpdateRequest categoriesUpdateRequest){
        Response<Integer> response =  categoriesWriteFacade.insert(categoriesUpdateRequest);
        if(!response.isSuccess()){
            throw new RestException("插入失败："+ response.getMessage());
        }
        return "插入失败";
    }

    @PostMapping("/delete")
    @ResponseBody
    @ResponseJsonFormat
    @Authority
    public String delete(CategoriesUpdateRequest categoriesUpdateRequest){
        Response<Integer> response =  categoriesWriteFacade.delete(categoriesUpdateRequest);
        if(!response.isSuccess()){
            throw new RestException("删除失败："+ response.getMessage());
        }
        return "删除成功";
    }

}
