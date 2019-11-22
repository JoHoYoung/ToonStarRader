package com.ntoon.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ntoon.app.RequestForm.CreateStar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DetermineCheat {

  @Autowired
  DBTransaction dbTransaction;

  private float pivot = 3.0f;

  public void determine(CreateStar createStar){
    float avg = dbTransaction.findVolumeAverageById(createStar.getVolumeId());
    if(createStar.getScore() < avg - this.pivot){
      dbTransaction.insertCheatingModel(createStar, avg);
    }
  }
}
