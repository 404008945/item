package com.xishan.store.item.server.es.client;

import com.google.common.collect.Lists;
import com.xishan.store.base.exception.ServiceException;
import com.xishan.store.escommon.EsUtil;
import com.xishan.store.escommon.model.Bulk;
import com.xishan.store.escommon.page.EsPage;
import com.xishan.store.item.api.response.GoodComplexDTO;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现goods在es中的增删改
 */
@Component
public class GoodEsClient  implements InitializingBean {
    @Autowired
    private RestHighLevelClient client;

    @Value("${goodIndex:goods_index}")
    private String goodIndex;

    private EsUtil esUtil;
    @Override
    public void afterPropertiesSet() throws Exception {
        this.esUtil = new EsUtil();
        this.esUtil.setClient(client);
    }

    /**
     * 增加更新
     * @param goodComplexDTO
     */
    public void index(GoodComplexDTO goodComplexDTO){
        if(goodComplexDTO == null){
            throw  new ServiceException("goodComplexDTO不可为空");
        }
        Bulk bulk = new Bulk(goodComplexDTO.getId().toString(),goodComplexDTO);
        esUtil.index(goodIndex,bulk);
    }

    //删除
    public void deleteById(GoodComplexDTO goodComplexDTO){
        if(goodComplexDTO == null){
            throw  new ServiceException("goodComplexDTO不可为空");
        }
        esUtil.delete(goodIndex,goodComplexDTO.getId().toString());
    }

    //根据id查找

    public GoodComplexDTO getById(GoodComplexDTO goodComplexDTO){
        if(goodComplexDTO == null){
            throw  new ServiceException("goodComplexDTO不可为空");
        }
        return esUtil.getById(goodIndex,goodComplexDTO.getId().toString(),GoodComplexDTO.class);
    }


    /**
     * 多条件检索并集，适用于搜索比如包含腾讯大王卡，滴滴大王卡的用户
     * @param fieldKey
     * @param queryList
     * @return
     */
    public BoolQueryBuilder orMatchUnionWithList(String fieldKey, List<String> queryList){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        for (String fieldValue : queryList) {
            boolQuery.should(QueryBuilders.matchPhraseQuery(fieldKey, fieldValue));
        }
        return boolQuery;
    }

    /**
     * term单条件
     * @param fieldKey
     * @param value
     * @return
     */
    public BoolQueryBuilder onTermWithSingle(String fieldKey,  Integer value){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.should(QueryBuilders.termQuery(fieldKey, value));
        return boolQuery;
    }

    public EsPage<GoodComplexDTO> paging(GoodComplexDTO goodComplexDTO,int pageNo ,int pageSize){
        if(goodComplexDTO == null){
            throw  new ServiceException("goodComplexDTO不可为空");
        }
        BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
        makeCretiria(mustQuery,goodComplexDTO);
        return esUtil.paging(goodIndex,mustQuery,pageNo,pageSize,GoodComplexDTO.class);
    }
    public List<GoodComplexDTO> listAll(GoodComplexDTO goodComplexDTO){
        if(goodComplexDTO == null){
            throw  new ServiceException("goodComplexDTO不可为空");
        }
        BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
        makeCretiria(mustQuery,goodComplexDTO);
        return esUtil.listAll(goodIndex,mustQuery,GoodComplexDTO.class);
    }

    private void makeCretiria(BoolQueryBuilder boolQueryBuilder,GoodComplexDTO goodComplexDTO){

        if(goodComplexDTO.getGoodsName()!=null) {
            boolQueryBuilder.must(orMatchUnionWithList("goodsName", Lists.newArrayList(goodComplexDTO.getGoodsName())));
        }
        if(goodComplexDTO.getBrandName()!=null) {
            boolQueryBuilder.must(orMatchUnionWithList("brandName", Lists.newArrayList(goodComplexDTO.getBrandName())));
        }
        if(goodComplexDTO.getCateName() != null) {
            boolQueryBuilder.must(orMatchUnionWithList("cateName", Lists.newArrayList(goodComplexDTO.getCateName())));
        }
        if(goodComplexDTO.getBrandId() != null) {
            boolQueryBuilder.must(onTermWithSingle("brandId",goodComplexDTO.getBrandId()));
        }
        if(goodComplexDTO.getBrandId() != null) {
            boolQueryBuilder.must(onTermWithSingle("brandId",goodComplexDTO.getBrandId()));
        }
        if(goodComplexDTO.getCateId() != null) {
            boolQueryBuilder.must(onTermWithSingle("cateId",goodComplexDTO.getCateId()));
        }
    }

    public void batchUpdate(List<GoodComplexDTO> goodComplexDTOS){
        List<Bulk> bulks = new ArrayList<>();
        goodComplexDTOS.forEach(it->{
            bulks.add(new Bulk(String.valueOf(it.getId()),it));
        });
        esUtil.bulkIndex(goodIndex,bulks);
    }
}