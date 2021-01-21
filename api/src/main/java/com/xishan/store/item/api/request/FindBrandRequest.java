package com.xishan.store.item.api.request;

import com.xishan.store.base.page.PageCommon;
import lombok.Data;

@Data
public class FindBrandRequest extends PageCommon {

    private Integer id;

    private String brandName;

    private String desc;

    private Integer sort;

}
