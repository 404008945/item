package com.xishan.store.item.server.es;

import com.github.pagehelper.PageInfo;
import com.xishan.store.escommon.EsUtil;
import com.xishan.store.escommon.model.Bulk;
import com.xishan.store.item.api.request.FindByGoodRequest;
import com.xishan.store.item.api.response.GoodComplexDTO;

import com.xishan.store.item.server.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

/**
 * 创建索引并dump全量数据。
 */
@Component
@Slf4j
public class EsInit  implements InitializingBean {
    @Autowired
    private RestHighLevelClient client;

    private EsUtil esUtil;

    @Value("${goodIndex:goods_index}")
    private String goodIndex;

    @Autowired
    private GoodsService goodsService;

    @PostConstruct
    public void init(){
        this.esUtil = new EsUtil();
        this.esUtil.setClient(client);
       if(!esUtil.indexExists(goodIndex)){
           //创建索引
           createGoodsIndex();
           //全量dump数据
           dumpGoodsIndex();

       }
    }


    public void createGoodsIndex() {
        try {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(goodIndex);
            Map<String, Object> id = new HashMap<>();
            id.put("type", "integer");
            Map<String, Object> goodsName = new HashMap<>();
            goodsName.put("type", "text");
            Map<String, Object> brandId = new HashMap<>();
            brandId.put("type", "integer");
            Map<String, Object> cateId = new HashMap<>();
            cateId.put("type", "integer");
            Map<String, Object> price = new HashMap<>();
            price.put("type", "long");
            Map<String, Object> original = new HashMap<>();
            original.put("type", "long");
            Map<String, Object> tags = new HashMap<>();
            tags.put("type", "text");
            Map<String, Object> isSale = new HashMap<>();
            isSale.put("type", "byte");
            Map<String, Object> pid = new HashMap<>();
            pid.put("type", "integer");
            Map<String, Object> cateName = new HashMap<>();
            cateName.put("type", "text");
            Map<String, Object> brandName = new HashMap<>();
            brandName.put("type", "text");
            Map<String, Object> brandDesc = new HashMap<>();
            brandDesc.put("type", "text");


            Map<String, Object> properties = new HashMap<>();
            properties.put("id", id);
            properties.put("goodsName", goodsName);
            properties.put("brandId", brandId);
            properties.put("cateId", cateId);
            properties.put("price", price);
            properties.put("original", original);
            properties.put("tags", tags);

            properties.put("isSale", isSale);

            properties.put("cateName", cateName);
            properties.put("brandName", brandName);
            properties.put("brandDesc", brandDesc);
            Map<String, Object> mapping = new HashMap<>();
            mapping.put("properties", properties);
            createIndexRequest.mapping(mapping);
            client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 全量dump索引,通过分页，一次分页100,直到全部分页完成
     */
    public void dumpGoodsIndex() {
        int pageNo = 0;
        PageInfo<Integer> pageInfo = goodsService.pagingIds(pageNo, 100);
        List<Integer> ids = pageInfo.getList();
        queryAndIndex(ids);
        while (pageInfo.isHasNextPage()) {
            ids = pageInfo.getList();
            queryAndIndex(ids);
            pageNo++;
            pageInfo = goodsService.pagingIds(pageNo, 100);
        }

    }

    public void queryAndIndex(List<Integer> ids){
        List<GoodComplexDTO> goods = new ArrayList<>();
        List<Bulk> bulks = new ArrayList<>();
        ids.stream().forEach(it->{
            FindByGoodRequest findByGoodRequest = new FindByGoodRequest();
            findByGoodRequest.setId(it);
            GoodComplexDTO goodComplexDTO =  goodsService.findByGoodId(findByGoodRequest);
            if(goodComplexDTO != null){
                log.info("dump good:{}",goodComplexDTO);
                goods.add(goodComplexDTO);
                Bulk bulk = new Bulk(String.valueOf(goodComplexDTO.getId()),goodComplexDTO);

                bulks.add(bulk);
            }
        });


        esUtil.bulkIndex(goodIndex,bulks);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
