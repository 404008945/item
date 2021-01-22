package com.xishan.store.item.api.request;

import com.xishan.store.base.page.PageCommon;
import lombok.Data;

@Data
public class GoodsPageingRequest extends PageCommon {


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
}
