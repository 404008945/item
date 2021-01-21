package com.xishan.store.item.server.service;

import com.xishan.store.base.exception.ServiceException;
import com.xishan.store.item.api.model.Categories;
import com.xishan.store.item.api.response.GoodComplexDTO;
import com.xishan.store.item.server.es.client.GoodEsClient;
import com.xishan.store.item.server.mapper.BrandMapper;
import com.xishan.store.item.server.mapper.CategoriesMapper;
import com.xishan.store.item.server.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class CategoriesService {
    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Autowired
    private GoodEsClient goodEsClient;

    @Autowired
    private RedisUtil redisUtil;


    public Categories findById(Categories categories){
        return categoriesMapper.selectByPrimaryKey(categories.getId());
    }

    public Integer insert(Categories categories){
        return categoriesMapper.insert(categories);
    }

    public Integer update(Categories categories){
        int n = categoriesMapper.updateByPrimaryKeySelective(categories);
        if (n > 0) {//批量更新es
            GoodComplexDTO complexDTO = new GoodComplexDTO();
            complexDTO.setBrandId(categories.getId());
            List<GoodComplexDTO> complexDTOS = goodEsClient.listAll(complexDTO);
            complexDTOS.forEach(it->{
                it.setCateName(categories.getCateName());
                it.setCateId(categories.getPid());
            });
            String []keys = complexDTOS.stream().map(it-> redisUtil.makeGoodRedisKey(it.getId())).toArray(String[]::new);
            redisUtil.del(keys);
            goodEsClient.batchUpdate(complexDTOS);
        }
        return n;
    }

    /**
     * 如果存在关联商品，不允许删除
     * @param categories
     * @return
     */
    public Integer delete(Categories categories){
        GoodComplexDTO complexDTO = new GoodComplexDTO();
        complexDTO.setCateId(categories.getId());
        List<GoodComplexDTO> complexDTOS = goodEsClient.listAll(complexDTO);
        if(!CollectionUtils.isEmpty(complexDTOS)) {
            log.info("该品牌下有商品，无法删:{}",categories.getId());
            throw new ServiceException("该品牌下有商品，无法删除:"+categories.getId());
        }
        return categoriesMapper.deleteByPrimaryKey(categories.getId());
    }
}
