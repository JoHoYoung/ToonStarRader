package com.ntoon.app.service.MsgProducer;


public interface Producer {
    void publish(String topic, String value);
}
