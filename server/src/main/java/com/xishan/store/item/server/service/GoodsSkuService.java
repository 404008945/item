package com.xishan.store.item.server.service;

import com.alibaba.fastjson.JSON;
import com.xishan.store.base.exception.ServiceException;
import com.xishan.store.item.api.model.BuyRecord;
import com.xishan.store.item.api.model.GoodsSku;
import com.xishan.store.item.api.request.BuySkuRequest;
import com.xishan.store.item.api.response.BuySkuResponse;
import com.xishan.store.item.api.response.GoodsSkuDTO;
import com.xishan.store.item.server.annotation.NeedRedisLock;
import com.xishan.store.item.server.mapper.GoodsSkuMapper;
import com.xishan.store.item.server.mq.MqService;
import com.xishan.store.item.server.mq.message.GoodSkuNaneUpdateMessage;
import com.xishan.store.item.server.redis.RedisLock;
import com.xishan.store.item.server.redis.RedisUtil;
import com.xishan.store.item.server.util.BeanUtil;
import com.xishan.store.usercenter.userapi.context.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private BuyRecordService buyRecordService;

    @Autowired
    private MqService mqService;

    @Value("rocketmq.topic:updateName")
    private String topic;

    @Value("rocketmq.tag:skuName")
    private String tag;


    public GoodsSku findById(GoodsSku goodsSku){
        return goodsSkuMapper.selectByPrimaryKey(goodsSku.getId());
    }

    public Integer insert(GoodsSku goodsSku){
        return goodsSkuMapper.insert(goodsSku);
    }

    public Integer update(GoodsSku goodsSku){//需要将缓存失效
        GoodsSku sku = goodsSkuMapper.selectByPrimaryKey(goodsSku.getId());
        boolean flag = false;
        if(!sku.getTitle().equals(goodsSku.getTitle())){
            flag = true;
        }
        int n = goodsSkuMapper.updateByPrimaryKeySelective(goodsSku);
        if(n > 0){
            if(flag) {
                GoodSkuNaneUpdateMessage goodSkuNaneUpdateMessage = new GoodSkuNaneUpdateMessage();
                goodSkuNaneUpdateMessage.setId(goodsSku.getId());
                goodSkuNaneUpdateMessage.setSkuName(goodsSku.getTitle());
                mqService.send(topic,tag, JSON.toJSONString(goodSkuNaneUpdateMessage));
            }

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
     * 购买商品为什么要加锁,如何做幂等
     */
    public BuySkuResponse buyGoods(BuySkuRequest buySkuRequest){//锁的是sku，而不是good
        if (buySkuRequest == null) {
            throw new ServiceException("购买商品参数错误");
        }
        //value保证了，我这里加的锁只有本方法能解锁
        //做幂等，判断是否已经存在
        String value = buySkuRequest.getUuid();
        //进行判断与扣除操作
        GoodsSku req = new GoodsSku();
        req.setId(buySkuRequest.getSkuId());
        GoodsSku goodsSku = this.findById(req);
        BuyRecord record = buyRecordService.findByBuyId(value);
        if (record != null) {
            BuySkuResponse buySkuResponse = new BuySkuResponse();
            buySkuResponse.setAmount(goodsSku.getPrice()*record.getNum());
            buySkuResponse.setGoodId(buySkuRequest.getGoodId());
            buySkuResponse.setNum(record.getNum());
            buySkuResponse.setSkuId(buySkuRequest.getSkuId());
            return buySkuResponse;
        }

        if (goodsSku.getNum() < buySkuRequest.getNum()) {
            //容量不够了
            throw new ServiceException("操作失败，库存不足");
        }
        goodsSku.setNum(goodsSku.getNum() - buySkuRequest.getNum());
        if (goodsSku.getNum() == 0) {
            goodsSku.setStatus((byte) 1);
        }
        this.update(goodsSku);
        BuyRecord buyRecord = new BuyRecord();
        buyRecord.setBuyId(value);
        buyRecord.setBuyUserId(1l);
        buyRecord.setNum(buySkuRequest.getNum());
        buyRecord.setSkuId(buySkuRequest.getSkuId());
        buyRecordService.insert(buyRecord);
        BuySkuResponse response = BeanUtil.convertToBean(buySkuRequest, BuySkuResponse.class);
        response.setAmount(goodsSku.getPrice()*buySkuRequest.getNum());
        return response;
    }


}
