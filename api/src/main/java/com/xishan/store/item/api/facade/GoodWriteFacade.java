package com.xishan.store.item.api.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.model.Goods;
import com.xishan.store.item.api.request.DeleteGoodByIdRequest;
import com.xishan.store.item.api.request.GoodUpdateRequest;

public interface GoodWriteFacade {
     Response<Integer> update(GoodUpdateRequest request);

     Response<Integer> insert(GoodUpdateRequest request);

     Response<Integer> delete(DeleteGoodByIdRequest request);
}
