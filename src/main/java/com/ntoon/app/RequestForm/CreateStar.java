package com.ntoon.app.RequestForm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateStar {

  @JsonProperty("volumeId")
  private String volumeId;
  @JsonProperty("userId")
  private String userId;
  @JsonProperty("score")
  private float score;
  @JsonProperty("createdAt")
  private Date createdAt;
}
