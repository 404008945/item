package com.xishan.store.item.server;

import com.alibaba.fastjson.JSON;
import com.xishan.store.item.api.facade.GoodSkuWriteFacade;
import com.xishan.store.item.api.model.Goods;
import com.xishan.store.item.api.request.BuySkuRequest;
import com.xishan.store.item.api.request.FindByGoodRequest;
import com.xishan.store.item.api.response.GoodComplexDTO;
import com.xishan.store.item.server.es.EsInit;
import com.xishan.store.item.server.es.client.GoodEsClient;
import com.xishan.store.item.server.mq.MqService;
import com.xishan.store.item.server.mq.message.GoodSkuNaneUpdateMessage;
import com.xishan.store.item.server.service.GoodsService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.support.JdbcTransactionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.lang.Thread.sleep;

@SpringBootTest
class ServerStarterApplicationTests {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodSkuWriteFacade goodSkuWriteFacade;


    @Autowired
    private MqService mqService;


    @Autowired
    private GoodEsClient  client;
    @Test
    void contextLoads() {

        GoodSkuNaneUpdateMessage goodSkuNaneUpdateMessage = new GoodSkuNaneUpdateMessage();
        goodSkuNaneUpdateMessage.setId(1);
        goodSkuNaneUpdateMessage.setSkuName("23213");
        mqService.send("updateName","skuName", JSON.toJSONString(goodSkuNaneUpdateMessage));

    }

    @Test
    void insert(){
        Goods goods = new Goods();
        goods.setBrandId(1);
        goods.setCateId(1);
        goods.setCreatedAt(new Date());
        goods.setUpdatedAt(new Date());
        goods.setGoodsName("大豆");
        goods.setIsSale((byte)1);
        goods.setPrice(10l);
        goods.setOriginal(12l);
        goods.setTags("111");
        goodsService.insert(goods);
    }

    @Test
    void buy(){
        BuySkuRequest buySkuRequest = new  BuySkuRequest();
        buySkuRequest.setGoodId(1);
        buySkuRequest.setNum(1);
        buySkuRequest.setSkuId(1);
        buySkuRequest.setUuid(UUID.randomUUID().toString());
        goodSkuWriteFacade.buyGoods(buySkuRequest);
        while (true){
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
