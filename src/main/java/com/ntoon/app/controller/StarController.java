package com.ntoon.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntoon.app.RequestForm.CreateStar;
import com.ntoon.app.ResponseForm.ResponseImpl.BaseResponse;
import com.ntoon.app.ResponseForm.ResponseImpl.DataResponse;
import com.ntoon.app.ResponseForm.Response;
import com.ntoon.app.service.DBTransaction;
import com.ntoon.app.service.DateTreat;
import com.ntoon.app.service.MsgProducer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping(value = "/star")
public class StarController {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  DateTreat dateTreat;

  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  DBTransaction dbTransaction;

  @Autowired
  @Qualifier("kafka")
  Producer producer;

  @RequestMapping(value = "/read", method = RequestMethod.GET)
  @CrossOrigin("*")
  public Response readStar(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("volumeId") String volumeId) throws ParseException {
    return new DataResponse(200,"success",dbTransaction.findStarByIdBetween(volumeId,startDate,endDate));
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public Response createStar(@RequestBody CreateStar data) throws JsonProcessingException {
    data.setCreatedAt(new Date());
    producer.publish("test", objectMapper.writeValueAsString(data));
    return new BaseResponse(200,"success");
  }

}
