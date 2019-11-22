package com.ntoon.app.service;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateTreat {

  private Calendar cal = Calendar.getInstance();
  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

  public Date addHour(String timeString, int amount) throws ParseException {
    Date time = dateFormat.parse(timeString);
    this.cal.setTime(time);
    this.cal.add(Calendar.HOUR,amount);
    return this.cal.getTime();
  }
}
