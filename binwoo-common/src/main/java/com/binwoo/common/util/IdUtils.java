package com.binwoo.common.util;

import java.util.UUID;

/**
 * ID工具类.
 *
 * @author admin
 * @date 2019/10/14 11:04
 */
public class IdUtils {

  /**
   * 生成随机uuid，如a5c8a5e8-df2b-4706-bea4-08d0939410e3.
   *
   * @return uuid
   */
  public static String randomUuid() {
    return UUID.randomUUID().toString();
  }

  /**
   * 生成随机uuid，如b17f24ff026d40949c85a24f4f375d42.
   *
   * @return uuid
   */
  public static String simpleUuid() {
    return randomUuid().replaceAll("-", "");
  }

}
