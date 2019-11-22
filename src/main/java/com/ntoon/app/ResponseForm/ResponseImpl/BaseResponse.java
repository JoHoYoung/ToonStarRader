package com.ntoon.app.ResponseForm.ResponseImpl;

import com.ntoon.app.ResponseForm.Response;
import lombok.Data;

@Data
public class BaseResponse extends Response {
  public BaseResponse(int statusCode, String statusMsg) {
    super(statusCode, statusMsg);
  }
}
