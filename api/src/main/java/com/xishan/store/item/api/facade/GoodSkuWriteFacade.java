package com.xishan.store.item.api.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.request.BuySkuRequest;
import com.xishan.store.item.api.request.GoodSkuUpdateRequest;
import com.xishan.store.item.api.response.BuySkuResponse;


public interface GoodSkuWriteFacade {

    Response<Integer> insert(GoodSkuUpdateRequest request);

    Response<Integer> update(GoodSkuUpdateRequest request);

    Response<Integer> delete(GoodSkuUpdateRequest request);

    Response<BuySkuResponse> buyGoods(BuySkuRequest buySkuRequest);

}
