package com.ntoon.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Contents")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentsModel {
  @Id
  @JsonProperty("id")
  private String id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("author")
  private String author;
  @JsonProperty("createdAt")
  private Date createdAt;
  @JsonProperty("updatedAt")
  private Date updatedAt;
}
