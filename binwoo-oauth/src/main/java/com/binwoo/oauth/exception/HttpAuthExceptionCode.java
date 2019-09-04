package com.binwoo.oauth.exception;

import com.binwoo.framework.http.exception.HttpExceptionCode;

/**
 * 自定义异常标识枚举.
 *
 * @author admin
 * @date 2019/9/3 18:20
 */
public enum HttpAuthExceptionCode implements HttpExceptionCode {
  /**
   * 用户不存在.
   */
  USER_NOT_EXIST(10000),
  /**
   * 账号密码错误.
   */
  INVALID_GRANT(10001),
  /**
   * 认证失败.
   */
  AUTHENTICATION_ERROR(10001),
  /**
   * 无效Scope.
   */
  INVALID_SCOPE(10001),
  /**
   * 用户已被禁用.
   */
  USER_DISABLED(10002),
  /**
   * 用户已被删除.
   */
  USER_DELETED(10003),
  /**
   * 无权限访问.
   */
  ACCESS_DENIED(10003),
  /**
   * 无效Token.
   */
  INVALID_TOKEN(10003),
  /**
   * 请求方式不支持.
   */
  REQUEST_METHOD_NOT_SUPPORTED(10004),
  /**
   * 服务器内部错误.
   */
  INTERNAL_SERVER_ERROR(10005);

  private int ret;

  HttpAuthExceptionCode(int ret) {
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
