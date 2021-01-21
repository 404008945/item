package com.xishan.store.item.api.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteGoodByIdRequest implements Serializable {
    private Integer id;
}
