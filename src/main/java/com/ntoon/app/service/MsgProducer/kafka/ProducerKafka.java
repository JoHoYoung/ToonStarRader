package com.ntoon.app.service.MsgProducer.kafka;

import com.ntoon.app.service.MsgProducer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.apache.kafka.clients.producer.ProducerRecord;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;


@Component
@Qualifier("kafka")
@PropertySource("classpath:/kafka.properties")
public class ProducerKafka implements Producer {

  @Autowired
  private Environment env;

  private KafkaProducer connection;

  @PostConstruct
  private void init() {
    this.connection = new KafkaProducer(producerConfigs());
  }

  public void publish(String topic, String message) {
    this.connection.send(new ProducerRecord<String, String>(topic, message));
  }

  private Properties producerConfigs() {
    Properties configs = new Properties();
    configs.put("bootstrap.servers", env.getProperty("bootstrap.servers"));
    configs.put("acks", env.getProperty("acks"));
    configs.put("block.on.buffer.full", env.getProperty("block.on.buffer.full"));
    configs.put("key.serializer", StringSerializer.class);
    configs.put("value.serializer", StringSerializer.class);
    return configs;
  }
}