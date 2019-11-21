package com.ntoon.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntoon.app.RequestForm.CreateStar;
import com.ntoon.app.model.StarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DBTransaction {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MongoTemplate mongoTemplate;

  public void insertStarData(String recordJson) throws JsonProcessingException {
    CreateStar createStar = objectMapper.readValue(recordJson, CreateStar.class);
    StarModel starModel =  new StarModel(createStar.getUserId(),createStar.getScore(), new Date());
    Update update = new Update();

    update.addToSet("stars",starModel);
    update.inc("userNum",1);
    update.inc("score",starModel.getScore());
    mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(createStar.getVolumeId())),update, "Volume");

  }

}
