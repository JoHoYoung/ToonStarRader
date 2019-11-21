package com.ntoon.app.service.MsgConsumer.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntoon.app.Repository.SeriesRepository;
import com.ntoon.app.Repository.StarRepository;
import com.ntoon.app.model.SeriesModel;
import com.ntoon.app.model.StarModel;
import com.ntoon.app.service.MsgConsumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@Component
@Qualifier("kafka")
@PropertySource("classpath:/kafka.properties")
public class ConsumerKafka extends Thread implements Consumer<ConsumerRecords<String, String>> {

  @Autowired
  private Environment env;

  @Autowired
  private StarRepository starRepository;

  private KafkaConsumer connection;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  SeriesRepository seriesRepository;

  @PostConstruct
  private void init() {
    this.connection = new KafkaConsumer(consumerConfigs());
    this.connection.subscribe(Arrays.asList("test"));
    this.start();
  }

  private Properties consumerConfigs() {
    Properties configs = new Properties();
    configs.put("bootstrap.servers", env.getProperty("bootstrap.servers"));
    configs.put("session.timeout.ms", "10000");
    configs.put("group.id", "test");
    configs.put("key.deserializer", StringDeserializer.class);
    configs.put("value.deserializer", StringDeserializer.class);
    return configs;
  }

  public ConsumerRecords<String, String> subscribe() {
    ConsumerRecords<String, String> records = this.connection.poll(Duration.ofMillis(1000));
    return records;
  }

  @Override
  public void run() {
    while (true) {  // 계속 loop를 돌면서 producer의 message를 띄운다.
      ConsumerRecords<String, String> records = this.connection.poll(Duration.ofMillis(1000));
      for (ConsumerRecord<String, String> record : records) {
        try {
          // Star 정보 삽입
          StarModel starTuple = objectMapper.readValue(record.value(), StarModel.class);
          starRepository.insert(starTuple);

          // 스타를 받게된 Series정보도 수정 (score + a, userNumber + 1)
          SeriesModel seriesTuple = seriesRepository.findById(starTuple.getSeriesId()).get();
          seriesTuple.setScore(seriesTuple.getScore() + starTuple.getScore());
          seriesTuple.setUserNum(seriesTuple.getUserNum() + 1);
          seriesRepository.save(seriesTuple);

       } catch (JsonProcessingException e) {
          System.out.println("Invalid Data format : " + record.value() + e);
          continue;
        } catch (Exception e) {
          System.out.println("ERROR : " + e);
          continue;
        }
      }
    }
  }
}
