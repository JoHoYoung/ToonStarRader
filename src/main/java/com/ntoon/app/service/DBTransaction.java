package com.ntoon.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntoon.app.RequestForm.CreateStar;
import com.ntoon.app.model.CheatStarModel;
import com.ntoon.app.model.CheatUserModel;
import com.ntoon.app.model.StarModel;
import com.ntoon.app.model.VolumeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DBTransaction {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  DateTreat dateTreat;

  @Autowired
  DetermineCheat determineCheat;

  public void insertStarData(String recordJson) throws IOException {
    CreateStar createStar = objectMapper.readValue(recordJson, CreateStar.class);
    StarModel starModel =  new StarModel(createStar.getUserId(),createStar.getScore(), new Date());

    determineCheat.determine(createStar);
    Update update = new Update();

    update.addToSet("stars",starModel);
    update.inc("totalUserNumber",1);
    update.inc("totalStarNumber",starModel.getScore());
    mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(createStar.getVolumeId())), update,"Volume");
  }

  public List<StarModel> findStarByIdBetween(String volumeId, String startDate, String endDate) throws ParseException {
    Date startTime = dateTreat.addHour(startDate,9);
    Date endTime = dateTreat.addHour(endDate,9);

    List <AggregationOperation> aggregationOperations = new ArrayList<>();

    aggregationOperations.add(Aggregation.match(Criteria.where("_id").is(volumeId)));
    aggregationOperations.add(Aggregation.unwind("stars"));
    aggregationOperations.add(Aggregation.match(Criteria.where("stars.createdAt").lt(endTime).andOperator(Criteria.where("stars.createdAt").gt(startTime))));
    aggregationOperations.add(Aggregation.group().addToSet("stars").as("stars"));

    Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
    List<VolumeModel> Stars = mongoTemplate.aggregate(aggregation,"Volume",VolumeModel.class).getMappedResults();
    return Stars.get(0).getStars();
  }

  public float findVolumeAverageById(String volumeId){
    Query query = new Query(Criteria.where("_id").is(volumeId));
    query.fields().exclude("stars");
    VolumeModel tuple = mongoTemplate.findOne(query,VolumeModel.class,"Volume");
    int userNum = tuple.getTotalUserNumber() == 0 ? 1 : tuple.getTotalUserNumber();
    float starNum = tuple.getTotalStarNumber();
    return starNum/userNum;
  }

  public void insertCheatingModel(CreateStar cheatStar,float avg){
    Update update = new Update();
    update.addToSet("stars",new CheatStarModel(cheatStar.getVolumeId(),cheatStar.getScore(),avg,cheatStar.getCreatedAt()));
    mongoTemplate.upsert(Query.query(Criteria.where("_id").is(cheatStar.getUserId())), update, CheatUserModel.class);
  }

}
