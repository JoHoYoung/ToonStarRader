package com.ntoon.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarModel {

  @Id
  @JsonProperty("id")
  private String id;
  @JsonProperty("score")
  private float score;
  @JsonProperty("createdAt")
  private Date createdAt;

}
