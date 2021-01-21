package com.xishan.store.item.server.service;

import com.xishan.store.base.exception.ServiceException;
import com.xishan.store.base.util.Response;
import com.xishan.store.item.api.model.GoodsSku;
import com.xishan.store.item.api.request.BuySkuRequest;
import com.xishan.store.item.api.response.BuySkuResponse;
import com.xishan.store.item.api.response.GoodsSkuDTO;
import com.xishan.store.item.server.mapper.GoodsSkuMapper;
import com.xishan.store.item.server.redis.RedisLock;
import com.xishan.store.item.server.redis.RedisUtil;
import com.xishan.store.item.server.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GoodsSkuService {
    //增删改，直接操作数据库，还有考虑购买商品怎么操作

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisLock redisLock;

    //15秒未抢到，则失败，需要重新抢
    @Value("${lockExpireTime:15000}")
    private Integer lockExpireTime;

    public GoodsSku findById(GoodsSku goodsSku){
        return goodsSkuMapper.selectByPrimaryKey(goodsSku.getId());
    }

    public Integer insert(GoodsSku goodsSku){
        return goodsSkuMapper.insert(goodsSku);
    }

    public Integer update(GoodsSku goodsSku){//需要将缓存失效

        int n = goodsSkuMapper.updateByPrimaryKeySelective(goodsSku);
        if(n > 0){
            redisUtil.del(redisUtil.makeGoodRedisKey(goodsSku.getGoodsId()));
            return  n;
        }
       throw new ServiceException("更新失败");
    }

    public Integer delete(GoodsSku goodsSku){//需要将缓存失效
        int n = goodsSkuMapper.deleteByPrimaryKey(goodsSku.getId());
        if(n > 0){
            redisUtil.del(redisUtil.makeGoodRedisKey(goodsSku.getGoodsId()));
            return n;
        }
        throw new ServiceException("删除失败");
    }


    //更新sku时，把缓存失效

    public List<GoodsSkuDTO> listByGoodsId(GoodsSku goodsSku){

        List<GoodsSku> goodsSkus = goodsSkuMapper.selectByGoodsId(goodsSku);
        if(CollectionUtils.isEmpty(goodsSkus)){
            return  new ArrayList<>();
        }
        return BeanUtil.convertToBeanList(goodsSkus,GoodsSkuDTO.class);
    }
    /**
     * 购买商品为什么要加锁
     */
    public BuySkuResponse buyGoods(BuySkuRequest buySkuRequest){//锁的是sku，而不是good
        if (buySkuRequest == null) {
            throw new ServiceException("购买商品参数错误");
        }
        //value保证了，我这里加的锁只有本方法能解锁
        String value = UUID.randomUUID().toString();
        try {
            if (redisLock.tryLock(redisUtil.makeSkuRedisKey(buySkuRequest.getSkuId()), value, lockExpireTime)) {
                //进行判断与扣除操作
                GoodsSku req = new GoodsSku();
                req.setId(buySkuRequest.getSkuId());
                GoodsSku goodsSku = this.findById(req);
                if (goodsSku.getNum() < buySkuRequest.getNum()) {
                    //容量不够了
                    throw new ServiceException("操作失败，库存不足");
                }
                goodsSku.setNum(buySkuRequest.getNum());
                if (goodsSku.getNum() == 0) {
                    goodsSku.setStatus((byte) 1);
                }
                this.update(goodsSku);
            } else {//超时
                throw new ServiceException("太拥挤了，请稍后重试");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ServiceException("购买失败");
        } finally {
            if (!redisLock.unlock(redisUtil.makeSkuRedisKey(buySkuRequest.getSkuId()), value)) {
                throw new ServiceException("锁释放失败" + redisUtil.makeSkuRedisKey(buySkuRequest.getSkuId()));
            }
        }
        BuySkuResponse response = BeanUtil.convertToBean(buySkuRequest, BuySkuResponse.class);
        return response;
    }


}
