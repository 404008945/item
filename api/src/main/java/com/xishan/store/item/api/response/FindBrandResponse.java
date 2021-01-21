package com.xishan.store.item.api.response;

import com.xishan.store.base.page.PageCommon;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FindBrandResponse implements Serializable {

    private Integer id;

    private String brandName;

    private String desc;

    private Integer sort;

    private Date createdAt;

    private Date updatedAt;

}
