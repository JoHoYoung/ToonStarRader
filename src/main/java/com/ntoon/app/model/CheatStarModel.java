package com.ntoon.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheatStarModel {
  String volumeId;
  float score;
  float volumeAverage;
  Date createdAt;
}
