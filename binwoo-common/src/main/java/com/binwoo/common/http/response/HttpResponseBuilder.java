package com.binwoo.common.http.response;

import com.binwoo.common.http.exception.HttpCodeManager;
import com.binwoo.common.http.exception.HttpExceptionCode;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * com.code.common.http.
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

  /**
   * HttpResponse转Map.
   *
   * @param response HttpResponse
   * @param withoutNullValue 是否忽略NULL值
   * @return Map
   */
  private static Map<String, Object> toMap(HttpResponse<String> response,
      boolean withoutNullValue) {
    Map<String, Object> result = new HashMap<String, Object>();
    if (response != null) {
      Field[] fields = response.getClass().getDeclaredFields();
      for (Field field : fields) {
        field.setAccessible(true);
        String key = field.getName();
        Object value;
        try {
          value = field.get(response);
        } catch (IllegalAccessException e) {
          value = null;
        }
        if (withoutNullValue && value == null) {
          continue;
        }
        result.put(key, value);
      }
    }
    return result;
  }

  /**
   * HttpResponse转Map.
   *
   * @param response HttpResponse
   * @return Map
   */
  public static Map<String, Object> toMap(HttpResponse<String> response) {
    return toMap(response, false);
  }

  /**
   * HttpResponse转Map，忽略NULL值.
   *
   * @param response HttpResponse
   * @return Map
   */
  public static Map<String, Object> toMapWithoutNullValue(HttpResponse<String> response) {
    return toMap(response, true);
  }

}
