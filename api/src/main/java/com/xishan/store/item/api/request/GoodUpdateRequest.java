package com.xishan.store.item.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodUpdateRequest implements Serializable {

    private Integer id;

    private String goodsName;

    private Integer brandId;

    private Integer cateId;

    private Long price;

    private Long original;

    private String tags;

    private Byte isSale;

}
