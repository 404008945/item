package com.xishan.store.item.server.mq.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodNameUpdateMessage implements Serializable {

    private Integer id;

    private String goodName;

}
