package com.ntoon.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ntoon.app.ResponseForm.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;

@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  protected ErrorResponse HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
    return new ErrorResponse("Invalid Request Format",700);
  }

  @ExceptionHandler(JsonProcessingException.class)
  protected ErrorResponse JsonProcessingExceptionHandler(JsonProcessingException e){
    return new ErrorResponse("Invalid Data Format",700);
  }

  @ExceptionHandler(ParseException.class)
  protected ErrorResponse ParseExceptionHandler(ParseException e){
    return new ErrorResponse("Invalid Request Format",700);
  }

  @ExceptionHandler(Exception.class)
  protected ErrorResponse ExceptionHandler(Exception e){
    return new ErrorResponse("Invalid Request Format",700);
  }

}