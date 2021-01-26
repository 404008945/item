package com.xishan.store.item.server.mq;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class MqService {

    @Value("rocketmq.group:defaultGroup")
    private String group;
    @Value("rocketmq.nameServerAddr:47.93.9.181:9876")
    private String nameServer;

    @Value("rocketmq.topic:updateName")
    private String tag;

    private DefaultMQProducer producer = null;

    @PostConstruct
    public void initMQProducer() {
        producer = new DefaultMQProducer(group);
        producer.setNamesrvAddr(nameServer);
        producer.setRetryTimesWhenSendFailed(3);

        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public boolean send(String topic, String tags, String content) {
        Message msg = new Message(topic, tags, "", content.getBytes());
        try {
            producer.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @PreDestroy
    public void shutDownProducer() {
        if(producer != null) {
            producer.shutdown();
        }
    }
}