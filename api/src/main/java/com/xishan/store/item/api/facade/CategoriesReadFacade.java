package com.xishan.store.item.api.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.request.FindCategoriesRequest;
import com.xishan.store.item.api.response.FindCategoriesResponse;

public interface CategoriesReadFacade {
    Response<FindCategoriesResponse> findById(FindCategoriesRequest request);
}
