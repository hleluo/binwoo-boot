package com.binwoo.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * com.sutpc.accident.utils.
 *
 * @author luoj
 * @date 2019/8/12 18:38
 */
public class WebUtils {

  /**
   * 获取IP地址.
   *
   * @param request 请求
   * @return IP地址
   */
  public static String getIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    final String ipUnknown = "unknown";
    final String ipNull = "null";
    if (ip == null || ip.length() == 0 || ipUnknown.equalsIgnoreCase(ip) || ipNull
        .equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || ipUnknown.equalsIgnoreCase(ip) || ipNull
        .equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || ipUnknown.equalsIgnoreCase(ip) || ipNull
        .equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || ipUnknown.equalsIgnoreCase(ip) || ipNull
        .equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || ipUnknown.equalsIgnoreCase(ip) || ipNull
        .equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (ip != null && ip.length() > 0) {
      if (ip.indexOf(",") > 0) {
        ip = ip.substring(0, ip.indexOf(","));
      }
    }
    return ip;
  }

}
