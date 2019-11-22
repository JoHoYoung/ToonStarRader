package com.ntoon.app.ResponseForm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Response {
  private int statusCode;
  private String statusMsg;
}
