package com.xishan.store.item.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class FindCategoriesResponse implements Serializable {
    private Integer id;

    private Integer pid;

    private String cateName;

    private Short sort;

    private Date createdAt;

    private Date updatedAt;
}
