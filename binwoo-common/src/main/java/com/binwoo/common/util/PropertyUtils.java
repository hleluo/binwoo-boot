package com.binwoo.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * com.binwoo.society.util.
 *
 * @author luoj
 * @date 2019/8/15 16:45
 */
public class PropertyUtils {

  /**
   * 加载Properties文件.
   *
   * @param filepath 文件路径
   * @return Properties信息
   * @throws IOException IOException
   */
  public static Properties load(String filepath) throws IOException {
    try (InputStream input = new FileInputStream(filepath)) {
      return load(input);
    }
  }

  /**
   * 加载Properties文件.
   *
   * @param input 文件流
   * @return Properties信息
   * @throws IOException IOException
   */
  public static Properties load(InputStream input) throws IOException {
    Properties props = new Properties();
    props.load(input);
    return props;
  }

}
