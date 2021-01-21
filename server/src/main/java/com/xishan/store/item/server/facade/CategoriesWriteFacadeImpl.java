package com.xishan.store.item.server.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.CategoriesWriteFacade;
import com.xishan.store.item.api.model.Categories;
import com.xishan.store.item.api.request.CategoriesUpdateRequest;
import com.xishan.store.item.server.service.CategoriesService;
import com.xishan.store.item.server.util.BeanUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CategoriesWriteFacadeImpl implements CategoriesWriteFacade {

    @Autowired
    private CategoriesService categoriesService;

    @Override
    public Response<Integer> update(CategoriesUpdateRequest categoriesUpdateRequest) {
        try {
            Categories req = BeanUtil.convertToBean(categoriesUpdateRequest,Categories.class);
            return com.xishan.store.base.util.Response.ok( categoriesService.update(req),Integer.class);
        }catch (Exception e){
            return com.xishan.store.base.util.Response.fail(e.getMessage(), Integer.class);
        }
    }

    @Override
    public Response<Integer> insert(CategoriesUpdateRequest categoriesUpdateRequest) {
        try {
            Categories req = BeanUtil.convertToBean(categoriesUpdateRequest,Categories.class);
            return com.xishan.store.base.util.Response.ok( categoriesService.insert(req),Integer.class);
        }catch (Exception e){
            return com.xishan.store.base.util.Response.fail(e.getMessage(), Integer.class);
        }
    }

    @Override
    public Response<Integer> delete(CategoriesUpdateRequest categoriesUpdateRequest) {
        try {
            Categories req = BeanUtil.convertToBean(categoriesUpdateRequest,Categories.class);
            return com.xishan.store.base.util.Response.ok( categoriesService.delete(req),Integer.class);
        }catch (Exception e){
            return com.xishan.store.base.util.Response.fail(e.getMessage(), Integer.class);
        }
    }
}
