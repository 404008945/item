package com.xishan.store.item.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandUpdateRequest implements Serializable {

    private Integer id;

    private String brandName;

    private String desc;

    private Integer sort;
}
