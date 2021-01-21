package com.xishan.store.item.server.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.BrandWriteFacade;
import com.xishan.store.item.api.model.Brand;
import com.xishan.store.item.api.request.BrandUpdateRequest;
import com.xishan.store.item.api.response.FindBrandResponse;
import com.xishan.store.item.server.service.BrandService;
import com.xishan.store.item.server.util.BeanUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BrandWoriteFacadeImpl implements BrandWriteFacade {

    @Autowired
    private BrandService brandService;

    @Override
    public Response<Integer> update(BrandUpdateRequest brandUpdateRequest) {
        try {
            Brand brand = BeanUtil.convertToBean(brandUpdateRequest,Brand.class);
            return com.xishan.store.base.util.Response.ok( brandService.update(brand),Integer.class);
        }catch (Exception e){
            return Response.fail(e.getMessage(), Integer.class);
        }
    }

    @Override
    public Response<Integer> insert(BrandUpdateRequest brandUpdateRequest) {
        try {
            Brand brand = BeanUtil.convertToBean(brandUpdateRequest,Brand.class);
            return com.xishan.store.base.util.Response.ok( brandService.insert(brand),Integer.class);
        }catch (Exception e){
            return Response.fail(e.getMessage(), Integer.class);
        }
    }

    @Override
    public Response<Integer> delete(BrandUpdateRequest brandUpdateRequest) {
        try {
            Brand brand = BeanUtil.convertToBean(brandUpdateRequest,Brand.class);
            return com.xishan.store.base.util.Response.ok( brandService.delete(brand),Integer.class);
        }catch (Exception e){
            return Response.fail(e.getMessage(), Integer.class);
        }
    }
}
