package com.xishan.store.item.server;

import com.xishan.store.item.api.request.FindByGoodRequest;
import com.xishan.store.item.api.response.GoodComplexDTO;
import com.xishan.store.item.server.es.EsInit;
import com.xishan.store.item.server.es.client.GoodEsClient;
import com.xishan.store.item.server.service.GoodsService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ServerStarterApplicationTests {

    @Autowired
    private GoodsService goodsService;


    @Autowired
    private GoodEsClient  client;
    @Test
    void contextLoads() {

        GoodComplexDTO complexDTO = new GoodComplexDTO();
        complexDTO.setGoodsName("玉米");
        System.out.println( client.paging(complexDTO,1,10));

    }

}
