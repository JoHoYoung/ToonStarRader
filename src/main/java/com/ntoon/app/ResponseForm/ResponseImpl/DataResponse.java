package com.ntoon.app.ResponseForm.ResponseImpl;

import com.ntoon.app.ResponseForm.Response;
import lombok.Data;

import java.util.List;


@Data
public class DataResponse<T> extends Response {
  List<T> data;
  public DataResponse(int statusCode, String statusMsg,List<T> data){
    super(statusCode,statusMsg);
    this.data = data;
  }
}
