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

  private static final String MSG_SUCCESS_DEFAULT = "SUCCESS";
  private static final String MSG_SUCCESS_QUERY = "SUCCESS_QUERY";
  private static final String MSG_SUCCESS_SAVE = "SUCCESS_SAVE";
  private static final String MSG_SUCCESS_INSERT = "SUCCESS_INSERT";
  private static final String MSG_SUCCESS_UPDATE = "SUCCESS_UPDATE";
  private static final String MSG_SUCCESS_DELETE = "SUCCESS_DELETE";

  /**
   * 从本地Properties获取消息，如获取失败，返回code码.
   *
   * @param code 异常代码
   * @return 消息
   */
  private static String getMessage(HttpExceptionCode code) {
    return getMessage(code.getValue());
  }

  /**
   * 从本地Properties获取消息，如获取失败，返回code码.
   *
   * @param code 异常代码字符串
   * @return 消息
   */
  private static String getMessage(String code) {
    String msg = HttpCodeManager.getValue(code);
    if (msg == null || "".equals(msg.trim())) {
      return code;
    }
    return msg;
  }

  /**
   * 操作成功.
   *
   * @param body 消息体
   * @return HttpResponse
   */
  public static <T> HttpResponse<T> success(T body) {
    return HttpResponse.success(getMessage(MSG_SUCCESS_DEFAULT), body);
  }

  /**
   * 查询成功.
   *
   * @param body 消息体
   * @return HttpResponse
   */
  public static <T> HttpResponse<T> query(T body) {
    return HttpResponse.success(getMessage(MSG_SUCCESS_QUERY), body);
  }

  /**
   * 保存成功.
   *
   * @param body 消息体
   * @return HttpResponse
   */
  public static <T> HttpResponse<T> save(T body) {
    return HttpResponse.success(getMessage(MSG_SUCCESS_SAVE), body);
  }

  /**
   * 插入成功.
   *
   * @param body 消息体
   * @return HttpResponse
   */
  public static <T> HttpResponse<T> insert(T body) {
    return HttpResponse.success(getMessage(MSG_SUCCESS_INSERT), body);
  }

  /**
   * 更新成功.
   *
   * @param body 消息体
   * @return HttpResponse
   */
  public static <T> HttpResponse<T> update(T body) {
    return HttpResponse.success(getMessage(MSG_SUCCESS_UPDATE), body);
  }

  /**
   * 删除成功.
   *
   * @param body 消息体
   * @return HttpResponse
   */
  public static <T> HttpResponse<T> delete(T body) {
    return HttpResponse.success(getMessage(MSG_SUCCESS_DELETE), body);
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
