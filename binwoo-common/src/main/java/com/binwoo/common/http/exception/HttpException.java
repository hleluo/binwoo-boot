package com.binwoo.common.http.exception;

/**
 * HTTP基础异常.
 *
 * @author hleluo
 * @date 2019/9/3 21:18
 */
public class HttpException extends Exception {

  /**
   * 异常代码.
   */
  private HttpExceptionCode code;

  /**
   * 构造函数.
   *
   * @param code 异常代码
   */
  public HttpException(HttpExceptionCode code) {
    super();
    this.code = code;
  }

  public HttpExceptionCode getCode() {
    return code;
  }

  public void setCode(HttpExceptionCode code) {
    this.code = code;
  }
}
