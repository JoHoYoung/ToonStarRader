package com.ntoon.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntoon.app.Repository.VolumeRepository;
import com.ntoon.app.Repository.StarRepository;
import com.ntoon.app.RequestForm.CreateStar;
import com.ntoon.app.ResponseForm.CustomResponse.BaseResponse;
import com.ntoon.app.ResponseForm.Response;
import com.ntoon.app.model.StarModel;
import com.ntoon.app.service.DateTreat;
import com.ntoon.app.service.MsgProducer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;

import java.util.*;

@RestController
@RequestMapping(value = "/star")
public class StarController {

  @Autowired
  StarRepository starRepository;

  @Autowired
  VolumeRepository seriesRepository;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  DateTreat dateTreat;

  @Autowired
  @Qualifier("kafka")
  Producer producer;

  // 클라이언트 요청시 html로 데이터 전송
  @RequestMapping(value = "/read", method = RequestMethod.GET)
  @CrossOrigin("*")
  public JSONObject readStar(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("seriesId") String seriesId) throws Exception {

    JSONObject JSON = new JSONObject();
    Date startTime = dateTreat.addHour(startDate,9);
    Date endTime = dateTreat.addHour(endDate,9);

    // Get Data from start ~ end
    List<StarModel> Stars = starRepository.findByUserIdBetween(startTime, endTime, seriesId);
    List<String> data = new ArrayList<>();

    // Return data serialize
    for (int i = 0; i < Stars.size(); i++) {
      data.add(objectMapper.writeValueAsString(Stars.get(i)));
    }
    JSON.put("stautsCode", 200);
    JSON.put("statusMsg", "sucsss");
    JSON.put("data", data);
    return JSON;
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public Response createStar(@RequestBody CreateStar data) throws JsonProcessingException {
    producer.publish("test", objectMapper.writeValueAsString(data));
    return new BaseResponse("success",200);
  }


}
