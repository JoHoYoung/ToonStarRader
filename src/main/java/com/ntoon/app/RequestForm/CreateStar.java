package com.ntoon.app.RequestForm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateStar {

  @JsonProperty("volumeId")
  private String volumeId;
  @JsonProperty("userId")
  private String userId;
  @JsonProperty("score")
  private float score;

}
