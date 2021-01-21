package com.xishan.store.item.api.facade;

import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.model.GoodsSku;
import com.xishan.store.item.api.request.FindGoodSkuRequest;
import com.xishan.store.item.api.response.FindGoodsSkuResponse;
import com.xishan.store.item.api.response.GoodsSkuDTO;

import java.util.List;

public interface GoodSkuReadFacade {
    Response<FindGoodsSkuResponse> findById(FindGoodSkuRequest request);

    Response<List<GoodsSkuDTO>> listByGoodsId(FindGoodSkuRequest request);
}
