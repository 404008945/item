package com.xishan.store.item.api.request;

import com.xishan.store.base.page.PageCommon;
import lombok.Data;

@Data
public class FindCategoriesRequest  extends PageCommon {
    private Integer id;

    private Integer pid;

    private String cateName;

    private Short sort;

}
