package com.ntoon.app.ResponseForm;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  public ErrorResponse(String statusMsg, int statusCode){
    this.statusMsg = statusMsg;
    this.statusCode = statusCode;
  }
  private String statusMsg;
  private int statusCode;

}