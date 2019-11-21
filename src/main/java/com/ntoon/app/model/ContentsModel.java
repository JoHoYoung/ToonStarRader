package com.ntoon.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "TOON")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentsModel {

  public ContentsModel() { }

  public ContentsModel(String id, String name, String author, Date createdAt, Date updatedAt) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

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
