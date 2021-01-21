package com.xishan.store.item.server.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.CategoriesReadFacade;
import com.xishan.store.item.api.model.Categories;
import com.xishan.store.item.api.request.FindCategoriesRequest;
import com.xishan.store.item.api.response.FindCategoriesResponse;
import com.xishan.store.item.server.service.CategoriesService;
import com.xishan.store.item.server.util.BeanUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class CategoriesReadFacadeImpl implements CategoriesReadFacade {
    @Autowired
    private CategoriesService categoriesService;

    @Override
    public Response<FindCategoriesResponse> findById(FindCategoriesRequest request) {
        try {
            Categories req = BeanUtil.convertToBean(request,Categories.class);
            Categories  categories  =  categoriesService.findById(req);
            FindCategoriesResponse response = BeanUtil.convertToBean(categories,FindCategoriesResponse.class);
            return Response.ok(response,FindCategoriesResponse.class);
        }catch (Exception e){
            return Response.fail(e.getMessage(),FindCategoriesResponse.class);
        }
    }
}
