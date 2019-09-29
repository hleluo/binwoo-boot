package com.binwoo.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间工具类.
 *
 * @author admin
 * @date 2019/9/29 14:47
 */
public class TimeUtils {

  /**
   * 日期格式(yyyy-MM-dd HH:mm:ss).
   **/
  public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

  /**
   * LocalDateTime转Date.
   *
   * @param time LocalDateTime
   * @return Date
   */
  public static Date toDate(LocalDateTime time) {
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zdt = time.atZone(zoneId);
    return Date.from(zdt.toInstant());
  }

  /**
   * 时间字符串转Date.
   *
   * @param date 时间格式字符串
   * @param format 转换格式
   * @return Date
   */
  public static Date toDate(String date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      return sdf.parse(date);
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * Date转LocalDateTime.
   *
   * @param date Date
   * @return LocalDateTime
   */
  public static LocalDateTime toLocalDateTime(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  /**
   * 时间字符串转LocalDateTime.
   *
   * @param date 时间格式字符串
   * @param format 转换格式
   * @return LocalDateTime
   */
  public static LocalDateTime toLocalDateTime(String date, String format) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
    return LocalDateTime.parse(date, dtf);
  }

  /**
   * 时间转字符串.
   *
   * @param date Date
   * @param format 转换格式
   * @return 字符串
   */
  public static String format(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  /**
   * 时间转字符串.
   *
   * @param time LocalDateTime
   * @param format 转换格式
   * @return 字符串
   */
  public static String format(LocalDateTime time, String format) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
    return dtf.format(time);
  }

}
