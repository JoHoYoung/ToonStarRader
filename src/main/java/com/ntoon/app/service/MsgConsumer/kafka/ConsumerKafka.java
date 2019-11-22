package com.ntoon.app.service.MsgConsumer.kafka;

import com.ntoon.app.service.DBTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Qualifier("kafka")
@PropertySource("classpath:/kafka.properties")
public class ConsumerKafka {

  @Autowired
  DBTransaction dbTransaction;

  @KafkaListener(topics = "test", groupId = "test")
  public void receive(String record) throws IOException {
    dbTransaction.insertStarData(record);
  }
}
