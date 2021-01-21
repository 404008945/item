package com.xishan.store.item.api.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.request.BrandUpdateRequest;



public interface BrandWriteFacade {
    Response<Integer> update(BrandUpdateRequest brandUpdateRequest);

    Response<Integer> insert(BrandUpdateRequest brandUpdateRequest);

    Response<Integer> delete(BrandUpdateRequest brandUpdateRequest);

}
