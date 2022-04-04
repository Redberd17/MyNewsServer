package com.nefedova.MyNewsSpringBoot.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

  public static String getCurrentDateUTC() {
    TimeZone tz = TimeZone.getTimeZone("Europe/Moscow");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    df.setTimeZone(tz);
    return df.format(new Date());
  }

}
