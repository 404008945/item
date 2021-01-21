package com.xishan.store.item.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FindGoodsSkuResponse implements Serializable {

    private Integer id;

    private Integer goodsId;

    private String title;

    private Integer num;

    private Long price;

    private String properties;

    private String barCode;

    private String goodsCode;

    private Byte status;//0 有货 1 无货

    private Date createdAt;

    private Date updatedAt;
}
