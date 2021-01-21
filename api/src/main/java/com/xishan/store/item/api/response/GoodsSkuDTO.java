package com.xishan.store.item.api.response;

import lombok.Data;

@Data
public class GoodsSkuDTO {
    private Integer id;

    private Integer goodsId;

    private String title;

    private Integer num;

    private Long price;

    private String properties;

    private String barCode;

    private String goodsCode;

    private Byte status;
}
