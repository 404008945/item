package com.xishan.store.item.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xishan.store.base.exception.ServiceException;
import com.xishan.store.item.api.model.Brand;
import com.xishan.store.item.api.model.Categories;
import com.xishan.store.item.api.model.Goods;
import com.xishan.store.item.api.model.GoodsSku;
import com.xishan.store.item.api.request.DeleteGoodByIdRequest;
import com.xishan.store.item.api.request.FindByGoodRequest;
import com.xishan.store.item.api.response.GoodComplexDTO;
import com.xishan.store.item.api.response.GoodDetailComplexDTO;
import com.xishan.store.item.api.response.GoodsSkuDTO;
import com.xishan.store.item.server.es.client.GoodEsClient;
import com.xishan.store.item.server.mapper.BrandMapper;
import com.xishan.store.item.server.mapper.CategoriesMapper;
import com.xishan.store.item.server.mapper.GoodsMapper;
import com.xishan.store.item.server.redis.RedisUtil;
import com.xishan.store.item.server.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 增删改需要同步到es
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

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Autowired
    private GoodEsClient goodEsClient;

    @Autowired
    private RedisUtil redisUtil;

    //默认30秒到期
    @Value("${expireTime:1000*60*30}")
    private Integer expireTime;

    /**
     * 查询good并关联类目和品牌
     * @param findByGoodRequest
     * @return
     */
    public GoodComplexDTO findByGoodId(FindByGoodRequest  findByGoodRequest) {
        if (findByGoodRequest == null) {
            throw new ServiceException("findByGoodRequest不可为没空");
        }
        GoodComplexDTO complexDTO = new GoodComplexDTO();
        complexDTO.setId(findByGoodRequest.getId());
        complexDTO = goodEsClient.getById(complexDTO);
        if(complexDTO != null){
            return complexDTO;
        }
        //先从es中查 因为es是查询好的组装对象
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
        if(good != null) {
            BeanUtils.copyProperties(good, goodComplexDTO);
        }

        if(categories != null) {
            goodComplexDTO.setPid(categories.getPid());
            goodComplexDTO.setCateName(categories.getCateName());
        }

        if(brand != null) {
            goodComplexDTO.setBrandDesc(brand.getDesc());

            goodComplexDTO.setBrandName(brand.getBrandName());
        }

        return goodComplexDTO;
    }

    public PageInfo<Integer> pagingIds(int pageNo,int pageSize){
        PageHelper.startPage(pageNo,pageSize);//利用了mybatis的拦截器
        List<Integer> ids = goodsMapper.pagingIds();
        PageInfo<Integer> pageInfo = new PageInfo(ids);
        return pageInfo;
    }

    public int insert(Goods goods){
        if(goods == null){
            throw new ServiceException("goods不可为没空");
        }
       int id = goodsMapper.insert(goods);
        if(id > 0){
            FindByGoodRequest findByGoodRequest = new FindByGoodRequest();
            findByGoodRequest.setId(goods.getId());
            goodEsClient.index(this.findByGoodId(findByGoodRequest));
        }
        return id;
    }

    public int update(Goods goods){
        if(goods == null){
            throw new ServiceException("goods不可为空");
        }
        int n = goodsMapper.updateByPrimaryKeySelective(goods);
        if (n >= 0) {
            goodEsClient.index(this.toGoodComplex(goods,null,null));
        }
        return n;
    }

    public int delete(DeleteGoodByIdRequest deleteGoodByIdRequest){
        if(deleteGoodByIdRequest == null){
            throw new ServiceException("deleteGoodByIdRequest 不可为空");
        }
        int n = goodsMapper.deleteByPrimaryKey(deleteGoodByIdRequest.getId());
        if (n >= 0) {
            GoodComplexDTO goodComplexDTO = new GoodComplexDTO();
            goodComplexDTO.setId(deleteGoodByIdRequest.getId());
            goodEsClient.deleteById(goodComplexDTO);
        }
        return n;
    }

    /**
     * 在redis，直接用，不在redis，重新取
     * @param findByGoodRequest
     * @return
     */
    public GoodDetailComplexDTO getGoodDetail(FindByGoodRequest  findByGoodRequest){
        GoodDetailComplexDTO goodDetailComplexDTO = redisUtil.get(redisUtil.makeGoodRedisKey(findByGoodRequest.getId()),GoodDetailComplexDTO.class);
        if(goodDetailComplexDTO != null){
            return goodDetailComplexDTO;
        }
        GoodComplexDTO goodComplexDTO = this.findByGoodId(findByGoodRequest);
        if(goodComplexDTO == null){
            return null;
        }
        goodDetailComplexDTO = BeanUtil.convertToBean(goodComplexDTO,GoodDetailComplexDTO.class);
        GoodsSku goodsSku = new GoodsSku();
        goodsSku.setGoodsId(findByGoodRequest.getId());
        List<GoodsSkuDTO> goodsSkuDTOS = goodsSkuService.listByGoodsId(goodsSku);
        goodDetailComplexDTO.setGoodsSkuList(goodsSkuDTOS);
        //放进redis
        redisUtil.set(redisUtil.makeGoodRedisKey(goodDetailComplexDTO.getId()),goodDetailComplexDTO,expireTime);
        return goodDetailComplexDTO;
    }



}
