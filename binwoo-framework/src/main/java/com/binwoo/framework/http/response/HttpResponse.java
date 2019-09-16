package com.binwoo.framework.http.response;

import java.io.Serializable;

/**
 * com.code.framework.http.
 *
 * @author luoj
 * @date 2019/8/5 17:12
 */
public class HttpResponse<T> implements Serializable {

  private static final String MSG_SUCCESS = "SUCCESS";

  private Integer ret = 0;
  private String msg = MSG_SUCCESS;
  private T body;

  private HttpResponse() {
  }

  private HttpResponse(Integer ret, String msg, T body) {
    this.ret = ret;
    this.msg = msg;
    this.body = body;
  }

  public Integer getRet() {
    return ret;
  }

  public void setRet(Integer ret) {
    this.ret = ret;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getBody() {
    return body;
  }

  public void setBody(T body) {
    this.body = body;
  }

  /**
   * 成功.
   *
   * @param body 数据
   * @param <T> 实体
   * @return 结果
   */
  public static <T> HttpResponse<T> success(T body) {
    return new HttpResponse<T>(0, MSG_SUCCESS, body);
  }

  /**
   * 成功.
   *
   * @param ret 代码
   * @param body 数据
   * @param <T> 实体
   * @return 结果
   */
  public static <T> HttpResponse<T> success(int ret, T body) {
    return new HttpResponse<T>(ret, MSG_SUCCESS, body);
  }

  /**
   * 成功.
   *
   * @param ret 代码
   * @param msg 消息
   * @param body 数据
   * @param <T> 实体
   * @return 结果
   */
  public static <T> HttpResponse<T> success(int ret, String msg, T body) {
    return new HttpResponse<T>(ret, msg, body);
  }

  /**
   * 成功.
   *
   * @param ret 代码
   * @param msg 消息
   * @param body 数据
   * @param args format参数
   * @param <T> 实体
   * @return 结果
   */
  public static <T> HttpResponse<T> success(int ret, String msg, T body, Object... args) {
    return new HttpResponse<T>(ret, String.format(msg, args), body);
  }

  /**
   * 构建.
   *
   * @param ret 代码
   * @param msg 消息
   * @param body 数据
   * @param <T> 实体
   * @return 结果
   */
  public static <T> HttpResponse<T> build(int ret, String msg, T body) {
    return new HttpResponse<T>(ret, msg, body);
  }

  /**
   * 构建.
   *
   * @param ret 代码
   * @param msg 消息
   * @param body 数据
   * @param args format参数
   * @param <T> 实体
   * @return 结果
   */
  public static <T> HttpResponse<T> build(int ret, String msg, T body, Object... args) {
    return new HttpResponse<T>(ret, String.format(msg, args), body);
  }

  /**
   * 失败.
   *
   * @param ret 代码
   * @param msg 消息
   * @param <T> 实体
   * @return 结果
   */
  public static <T> HttpResponse<T> failure(int ret, String msg) {
    return new HttpResponse<T>(ret, msg, null);
  }

  /**
   * 失败.
   *
   * @param ret 代码
   * @param msg 消息
   * @param args format参数
   * @param <T> 实体
   * @return 结果
   */
  public static <T> HttpResponse<T> failure(int ret, String msg, Object... args) {
    return new HttpResponse<T>(ret, String.format(msg, args), null);
  }

}
