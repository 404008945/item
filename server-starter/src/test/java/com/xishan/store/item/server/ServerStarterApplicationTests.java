package com.xishan.store.item.server;

import com.xishan.store.item.api.request.FindByGoodRequest;
import com.xishan.store.item.api.response.GoodComplexDTO;
import com.xishan.store.item.server.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServerStarterApplicationTests {

    @Autowired
    private GoodsService goodsService;
    @Test
    void contextLoads() {
        FindByGoodRequest findByGoodRequest = new FindByGoodRequest();
        findByGoodRequest.setId(1);
        GoodComplexDTO goodComplexDTO = goodsService.findByGoodId(findByGoodRequest);
        System.out.println(goodComplexDTO);
    }

}
