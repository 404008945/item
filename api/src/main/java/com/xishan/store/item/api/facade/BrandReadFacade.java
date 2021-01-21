package com.xishan.store.item.api.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.request.FindBrandRequest;
import com.xishan.store.item.api.response.FindBrandResponse;

public interface BrandReadFacade {
     Response<FindBrandResponse> findById(FindBrandRequest request);
}
