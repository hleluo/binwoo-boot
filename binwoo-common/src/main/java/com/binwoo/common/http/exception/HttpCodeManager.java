package com.binwoo.common.http.exception;

import com.binwoo.common.util.PropertyUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Http错误代码管理器.
 *
 * @author luoj
 * @date 2019/8/5 17:12
 */
public class HttpCodeManager {

  private static Properties properties;

  static {
    properties = new Properties();
  }

  /**
   * 设置properties资源.
   *
   * @param filepath 资源路径，如/code_message.properties
   */
  public static void setResource(String filepath) {
    try {
      properties = PropertyUtils.load(filepath);
    } catch (IOException e) {
      properties = new Properties();
    }
  }

  /**
   * 设置properties资源.
   *
   * @param input 资源文件流.
   */
  public static void setResource(InputStream input) {
    try {
      properties = PropertyUtils.load(input);
    } catch (IOException e) {
      properties = new Properties();
    }
  }

  /**
   * 获取properties资源.
   *
   * @return properties资源
   */
  public static Properties getProperties() {
    return properties;
  }

  /**
   * 根据code获取消息.
   *
   * @param code code
   * @return 消息
   */
  public static String getValue(String code) {
    return properties.getProperty(code);
  }

  /**
   * 根据code获取消息.
   *
   * @param code code
   * @param defaultValue 默认消息
   * @return 消息
   */
  public static String getValue(String code, String defaultValue) {
    return properties.getProperty(code, defaultValue);
  }

}
