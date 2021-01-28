package com.xishan.store.item.api.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuySkuResponse implements Serializable {
    private Integer goodId;

    private Integer skuId;

    private Integer num;

    private Long amount;//购买所需余额

    private String orderCode;
    private Integer orderId;

}
