package com.ntoon.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "STAR")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarModel {

  public StarModel() { }

  public StarModel(String id, String seriesId, String userId, float score, Date createdAt, Date updatedAt) {
    this.id = id;
    this.seriesId = seriesId;
    this.userId = userId;
    this.score = score;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Id
  @JsonProperty("id")
  private String id;
  @JsonProperty("seriesId")
  private String seriesId;
  @JsonProperty("userId")
  private String userId;
  @JsonProperty("score")
  private float score;
  @JsonProperty("createdAt")
  private Date createdAt;
  @JsonProperty("updatedAt")
  private Date updatedAt;

}
