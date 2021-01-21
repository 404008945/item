package com.xishan.store.item.server.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.facade.BrandReadFacade;
import com.xishan.store.item.api.model.Brand;
import com.xishan.store.item.api.request.FindBrandRequest;
import com.xishan.store.item.api.response.FindBrandResponse;
import com.xishan.store.item.server.service.BrandService;
import com.xishan.store.item.server.util.BeanUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BrandReadFacadeImpl implements BrandReadFacade {
    @Autowired
    private BrandService brandService;

    @Override
    public Response<FindBrandResponse> findById(FindBrandRequest request) {
        try {
            Brand brand =    brandService.findById(request);
            FindBrandResponse response = BeanUtil.convertToBean(brand,FindBrandResponse.class);
            return Response.ok(response,FindBrandResponse.class);
        }catch (Exception e){
            return Response.fail(e.getMessage(),FindBrandResponse.class);
        }
    }
}
