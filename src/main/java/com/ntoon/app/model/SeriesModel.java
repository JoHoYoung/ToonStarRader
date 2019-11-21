package com.ntoon.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "SERIES")
public class SeriesModel {

  public SeriesModel(String id, String toonId, int userNum, int no, float score, Date createdAt, Date updatedAt) {
    this.id = id;
    this.toonId = toonId;
    this.no = no;
    this.userNum = userNum;
    this.score = score;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Id
  private String id;
  private String toonId;
  private int no;
  private int userNum;
  private float score;
  private Date createdAt;
  private Date updatedAt;
}
