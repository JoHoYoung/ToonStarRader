package com.ntoon.app.service.MsgProducer.kafka;

import com.ntoon.app.service.MsgProducer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Qualifier("kafka")
@PropertySource("classpath:/kafka.properties")
public class ProducerKafka implements Producer {

  @Autowired
  KafkaTemplate kafkaTemplate;

  public void publish(String topic, String message) {
    kafkaTemplate.send(topic, message);
  }

}