package com.binwoo.framework.response;

import com.binwoo.framework.util.PropertyUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * com.code.framework.http.
 *
 * @author luoj
 * @date 2019/8/5 17:12
 */
public class HttpRetProperty {

  private static Properties properties;

  static {
    properties = new Properties();
  }

  /**
   * 设置properties资源.
   *
   * @param filepath 资源路径，如/ret_message.properties
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
   * 根据RET码获取消息.
   *
   * @param ret RET码
   * @return 消息
   */
  public static String getValue(int ret) {
    return properties.getProperty(String.valueOf(ret));
  }

  /**
   * 根据RET码获取消息.
   *
   * @param ret RET码
   * @param defaultValue 默认消息
   * @return 消息
   */
  public static String getValue(int ret, String defaultValue) {
    return properties.getProperty(String.valueOf(ret), defaultValue);
  }

}
