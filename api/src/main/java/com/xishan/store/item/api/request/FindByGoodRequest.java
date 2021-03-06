package com.xishan.store.item.api.request;

import com.xishan.store.base.page.PageCommon;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FindByGoodRequest extends PageCommon {

    private Integer id;

    private String goodsName;

    private Integer brandId;

    private Integer cateId;

    private Long price;

    private Long original;

    private String tags;

    private Byte isSale;

    private Date createdAt;

    private Date updatedAt;
}
