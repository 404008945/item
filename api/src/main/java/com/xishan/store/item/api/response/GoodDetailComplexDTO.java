package com.xishan.store.item.api.response;

import lombok.Data;

import java.util.List;

/**
 * 用于redis缓存,聚合了商品以及sku的信息
 */
@Data
public class GoodDetailComplexDTO {
    private Integer id;

    private String goodsName;

    private Integer brandId;

    private Integer cateId;

    private Long price;

    private Long original;

    private String tags;

    private Byte isSale;

    /**
     * categories字段
     */

    private Integer pid;

    private String cateName;


    /**
     * brand字段
     */
    private String brandName;

    private String brandDesc;

    private List<GoodsSkuDTO> goodsSkuList;



}
