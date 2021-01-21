package com.xishan.store.item.api.request;

import lombok.Data;

@Data
public class BuySkuRequest {

    private Integer goodId;

    private Integer skuId;

    private Integer num;
}
