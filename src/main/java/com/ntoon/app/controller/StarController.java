package com.ntoon.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntoon.app.Repository.SeriesRepository;
import com.ntoon.app.Repository.StarRepository;
import com.ntoon.app.model.SeriesModel;
import com.ntoon.app.model.StarModel;
import com.ntoon.app.service.MsgProducer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;

import java.util.*;

import java.text.SimpleDateFormat;

@RestController
@RequestMapping(value = "/star")
public class StarController {

  @Autowired
  StarRepository starRepository;

  @Autowired
  SeriesRepository seriesRepository;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  @Qualifier("kafka")
  Producer producer;


  // 클라이언트 요청시 html로 데이터 전송
  @RequestMapping(value = "/read", method = RequestMethod.GET)
  @CrossOrigin("*")
  public JSONObject readStar(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("seriesId") String seriesId) throws Exception {

    JSONObject JSON = new JSONObject();
    try {

      // Date format
      SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
      Calendar cal = Calendar.getInstance();
      Date start = transFormat.parse(startDate);
      Date end = transFormat.parse(endDate);

      // Add 9 hour for mongodb timezone
      cal.setTime(start);
      cal.add(Calendar.HOUR, 9);
      start = cal.getTime();

      // Add 9 hour for mongodb timezone
      cal.setTime(end);
      cal.add(Calendar.HOUR, 9);
      end = cal.getTime();

      // Get Data from start ~ end
      List<StarModel> Stars = starRepository.findByUserIdBetween(start, end, seriesId);
      List<String> data = new ArrayList<>();

      // Return data serialize
      for (int i = 0; i < Stars.size(); i++) {
        data.add(objectMapper.writeValueAsString(Stars.get(i)));
      }
      JSON.put("stautsCode", 200);
      JSON.put("statusMsg", "sucsss");
      JSON.put("data", data);
      return JSON;
    } catch (java.text.ParseException e) {
      JSON.put("statusCode", 703);
      JSON.put("statusMsg", "Invalid Query Date Format Correct : yyyy-MM-dd-HH:mm:ss");
      return JSON;
    } catch (Exception e) {
      System.out.println(e);
      JSON.put("stautsCode", 750);
      JSON.put("statusMsg", "Internal Server Error");
      return JSON;
    }
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  public JSONObject createStar(@RequestBody StarModel tuple) {
    JSONObject JSON = new JSONObject();
    try {

      // if user already  start at this series
      if(!starRepository.findByUserIdAndSeriesId(tuple.getUserId(),tuple.getSeriesId()).isEmpty()){
        JSON.put("statusCode", 701);
        JSON.put("statusMsg", "User Already star at this series ");
        return JSON;
      }
      // if target series not exist
      if(!seriesRepository.findById(tuple.getSeriesId()).isPresent()){
        JSON.put("statusCode", 702);
        JSON.put("statusMsg", "Seriese [" + tuple.getSeriesId() + "] is not eixst");
        return JSON;
      }
      // queueing to kafka
      producer.publish("test", objectMapper.writeValueAsString(tuple));
      JSON.put("statusCode", 200);
      JSON.put("statusMsg", "success");
      return JSON;

      // invaid request format
    } catch (JsonProcessingException e) {
      System.out.println("Invalid Data Format: " + tuple);
      JSON.put("statusCode", 700);
      JSON.put("statusMsg", "Invalid Data Format");
      return JSON;
    } catch (Exception e) {
      JSON.put("stautsCode", 750);
      JSON.put("statusMsg", "Internal Server Error " + e);
      return JSON;
    }
  }


//    // 1주일치 더미데이터 GEN 604800 개.
//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    @CrossOrigin("*")
//    public JSONObject testStar() throws Exception{
//        JSONObject JSON = new JSONObject();
//        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        for(int day =0 ;day<7;day++){
//            for(int hour = 0;hour<24;hour++){
//                for(int min =0;min<60;min++){
//                    for(int sec =0;sec<60;sec++){
//                        String Hour,Min,Sec;
//                        Hour = hour < 10 ? "0" + Integer.toString(hour) : Integer.toString(hour);
//                        Min = min<10 ? "0" + Integer.toString(min) : Integer.toString(min);
//                        Sec = sec<10 ? "0" + Integer.toString(sec) : Integer.toString(sec);
//
//                        String uid = UUID.randomUUID().toString();
//                        float rand = ((int)((Math.random())*20))/2.0f;
//                        String sDate = "2019" + "11" + Integer.toString(10+day) + Hour + Min + Sec;
//                        System.out.println(sDate);
//                        starRepository.insert(new StarModel(uid,"toon117","hy",rand,transFormat.parse(sDate),transFormat.parse(sDate)));
//
//                    }
//                }
//            }
//        }
//        System.out.println("END");
//        return JSON;
//    }
//    @RequestMapping(value = "/create", method = RequestMethod.GET)
//    public String readStar(HttpServletRequest req, @RequestParam("startDate") Date stateDate, @RequestParam("endDate")Date endDate) {
//
//        producer.send("WW","HI");
//        return "00";
//    }
}
