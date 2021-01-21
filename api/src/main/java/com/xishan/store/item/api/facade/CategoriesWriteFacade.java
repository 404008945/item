package com.xishan.store.item.api.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.request.BrandUpdateRequest;
import com.xishan.store.item.api.request.CategoriesUpdateRequest;


public interface CategoriesWriteFacade {

    Response<Integer> update(CategoriesUpdateRequest categoriesUpdateRequest);

    Response<Integer> insert(CategoriesUpdateRequest categoriesUpdateRequest);

    Response<Integer> delete(CategoriesUpdateRequest categoriesUpdateRequest);
}
