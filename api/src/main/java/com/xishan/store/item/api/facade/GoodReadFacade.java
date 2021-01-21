package com.xishan.store.item.api.facade;

import com.xishan.store.base.page.Paging;
import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.request.FindByGoodRequest;
import com.xishan.store.item.api.response.GoodComplexDTO;
import com.xishan.store.item.api.response.GoodDetailComplexDTO;

public interface GoodReadFacade {
     Response<GoodComplexDTO> findByGoodId(FindByGoodRequest findByGoodRequest);

     Response<Paging<GoodComplexDTO>> paging(GoodComplexDTO goodComplexDTO);

     Response<GoodDetailComplexDTO> getGoodDetail(FindByGoodRequest  findByGoodRequest);
}
