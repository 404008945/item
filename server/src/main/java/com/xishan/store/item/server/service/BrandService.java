package com.xishan.store.item.server.service;

import com.xishan.store.base.exception.ServiceException;
import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.model.Brand;
import com.xishan.store.item.api.request.FindBrandRequest;
import com.xishan.store.item.api.response.GoodComplexDTO;
import com.xishan.store.item.server.es.client.GoodEsClient;
import com.xishan.store.item.server.mapper.BrandMapper;
import com.xishan.store.item.server.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private GoodEsClient goodEsClient;

    @Autowired
    private RedisUtil redisUtil;


    public Brand findById(FindBrandRequest brand){
        return brandMapper.selectByPrimaryKey(brand.getId());
    }

    public Integer insert(Brand brand){
        int n = brandMapper.insert(brand);
        if (n > 0) {
            return n;
        }
        throw new ServiceException("插入失败");
    }

    public Integer update(Brand brand){
        int n = brandMapper.updateByPrimaryKeySelective(brand);
        if (n > 0) {//批量更新es
            GoodComplexDTO complexDTO = new GoodComplexDTO();
            complexDTO.setBrandId(brand.getId());
            List<GoodComplexDTO> complexDTOS = goodEsClient.listAll(complexDTO);
            complexDTOS.forEach(it->{
                it.setBrandName(brand.getBrandName());
                it.setBrandDesc(brand.getDesc());
            });
            String []keys = complexDTOS.stream().map(it-> redisUtil.makeGoodRedisKey(it.getId())).toArray(String[]::new);
            redisUtil.del(keys);
            goodEsClient.batchUpdate(complexDTOS);
            return n;
        }
        throw new ServiceException("更新失败");
    }

    /**
     * 如果存在关联商品，不允许删除
     * @param brand
     * @return
     */
    public Integer delete(Brand brand){
        GoodComplexDTO complexDTO = new GoodComplexDTO();
        complexDTO.setBrandId(brand.getId());
        List<GoodComplexDTO> complexDTOS = goodEsClient.listAll(complexDTO);
        if(!CollectionUtils.isEmpty(complexDTOS)) {
            log.info("该品牌下有商品，无法删:{}",brand.getId());
            throw new ServiceException("该品牌下有商品，无法删:"+brand.getId());
        }
        int n = brandMapper.deleteByPrimaryKey(brand.getId());
        if(n <= 0){
            throw new ServiceException("数据库更新失败");
        }
        return n;
    }
}
