package com.binwoo.common.http.exception;

/**
 * 自定义异常标识枚举.
 *
 * @author admin
 * @date 2019/9/3 18:20
 */
public enum HttpBaseExceptionCode implements HttpExceptionCode {

  /**
   * 无效Token.
   */
  TOKEN_INVALID(10400),
  /**
   * 过期Token.
   */
  TOKEN_EXPIRED(10401),
  /**
   * authUrl为空.
   */
  AUTH_URL_NULL(10402),
  /**
   * resourceId为空.
   */
  RESOURCE_ID_NULL(10403),
  /**
   * resource不可访问.
   */
  RESOURCE_NO_ACCESS(10404),
  /**
   * 接口不可访问.
   */
  API_NO_ACCESS(10405),
  /**
   * 服务器内部错误.
   */
  INTERNAL_SERVER_ERROR(10001);

  private int ret;

  HttpBaseExceptionCode(int ret) {
    this.ret = ret;
  }

  @Override
  public int getRet() {
    return ret;
  }

  @Override
  public String getValue() {
    return name();
  }
}
