package com.binwoo.framework.http.response;

import com.binwoo.framework.http.exception.HttpCodeManager;
import com.binwoo.framework.http.exception.HttpExceptionCode;

/**
 * com.code.framework.http.
 *
 * @author luoj
 * @date 2019/8/5 17:12
 */
public class HttpResponseBuilder {

  /**
   * 从本地Properties获取消息，如获取失败，返回code码.
   *
   * @param code 异常代码
   * @return 消息
   */
  private static String getMessage(HttpExceptionCode code) {
    String msg = HttpCodeManager.getValue(code.getValue());
    if (msg == null || "".equals(msg.trim())) {
      return code.getValue();
    }
    return msg;
  }

  /**
   * 失败结果.
   *
   * @param code 异常代码
   * @return HttpResponse
   */
  public static HttpResponse<String> failure(HttpExceptionCode code) {
    return HttpResponse.failure(code.getRet(), getMessage(code));
  }

  /**
   * 失败结果.
   *
   * @param code 异常代码
   * @param args 消息format参数
   * @return HttpResponse
   */
  public static HttpResponse<String> failure(HttpExceptionCode code, Object... args) {
    String msg = getMessage(code);
    return HttpResponse.failure(code.getRet(), String.format(msg, args));
  }

}
