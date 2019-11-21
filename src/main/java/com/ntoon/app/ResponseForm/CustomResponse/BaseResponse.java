package com.ntoon.app.ResponseForm.CustomResponse;

import com.ntoon.app.ResponseForm.Response;

public class BaseResponse implements Response {

  private String statusMsg;
  private int statusCode;

  public BaseResponse(String statusMsg,int statusCode){
    this.statusCode = statusCode;
    this.statusMsg = statusMsg;
  }
}
