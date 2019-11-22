package com.ntoon.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Volume")
public class VolumeModel {
  @Id
  private String id;
  private String contentsId;
  private String no;
  private int totalUserNumber;
  private float totalStarNumber;
  private Date createdAt;
  private Date updatedAt;
  private List<StarModel> stars;
}
