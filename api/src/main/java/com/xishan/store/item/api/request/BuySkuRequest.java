package com.xishan.store.item.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuySkuRequest implements Serializable {

    private Integer goodId;

    private Integer skuId;

    private Integer num;

    private String uuid;//做幂等用
}
