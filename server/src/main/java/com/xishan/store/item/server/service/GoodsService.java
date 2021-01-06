package com.xishan.store.item.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xishan.store.base.exception.ServiceException;
import com.xishan.store.item.api.model.Brand;
import com.xishan.store.item.api.model.Categories;
import com.xishan.store.item.api.model.Goods;
import com.xishan.store.item.api.request.FindByGoodRequest;
import com.xishan.store.item.api.response.GoodComplexDTO;
import com.xishan.store.item.server.mapper.BrandMapper;
import com.xishan.store.item.server.mapper.CategoriesMapper;
import com.xishan.store.item.server.mapper.GoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service查询出来的需要是复杂组合对象
 */
@Service
@Slf4j
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoriesMapper categoriesMapper;

    /**
     * 查询good并关联类目和品牌
     * @param findByGoodRequest
     * @return
     */
    public GoodComplexDTO findByGoodId(FindByGoodRequest  findByGoodRequest) {
        if (findByGoodRequest == null) {
            throw new ServiceException("findByGoodRequest不可为没空");
        }
        Integer goodId = findByGoodRequest.getId();
        Goods good = goodsMapper.selectByPrimaryKey(goodId);
        if(good == null){
            log.info("商品不存在:{}",goodId);
            throw new ServiceException("商品不存在："+goodId);
        }
        Brand brand = brandMapper.selectByPrimaryKey(good.getBrandId());
        Categories categories  = categoriesMapper.selectByPrimaryKey(good.getCateId());
        return toGoodComplex(good,brand,categories);
    }

    private GoodComplexDTO toGoodComplex(Goods good,Brand brand,Categories categories){
        GoodComplexDTO goodComplexDTO = new GoodComplexDTO();
        BeanUtils.copyProperties(good,goodComplexDTO);

        goodComplexDTO.setPid(categories.getPid());
        goodComplexDTO.setCateName(categories.getCateName());

        goodComplexDTO.setBrandDesc(brand.getDesc());

        goodComplexDTO.setBrandName(brand.getBrandName());

        return goodComplexDTO;
    }

    public PageInfo<Integer> pagingIds(int pageNo,int pageSize){
        PageHelper.startPage(pageNo,pageSize);//利用了mybatis的拦截器
        List<Integer> ids = goodsMapper.pagingIds();
        PageInfo<Integer> pageInfo = new PageInfo(ids);
        return pageInfo;
    }


}
