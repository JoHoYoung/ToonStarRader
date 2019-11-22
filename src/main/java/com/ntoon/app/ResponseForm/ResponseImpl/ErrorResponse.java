package com.ntoon.app.ResponseForm.ResponseImpl;

import com.ntoon.app.ResponseForm.Response;
import lombok.Data;

@Data
public class ErrorResponse extends Response {
  public ErrorResponse(int statusCode, String statusMsg) {
    super(statusCode, statusMsg);
  }
}