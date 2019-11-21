package com.ntoon.app.service.MsgConsumer.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ntoon.app.Repository.VolumeRepository;
import com.ntoon.app.service.DBTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Qualifier("kafka")
@PropertySource("classpath:/kafka.properties")
public class ConsumerKafka {

  @Autowired
  VolumeRepository volumeRepository;

  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  DBTransaction dbTransaction;

  @KafkaListener(topics = "test", groupId = "test")
  public void receive(String record) throws JsonProcessingException{
     dbTransaction.insertStarData(record);
  }
}
