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
   * 不支持的grant_type.
   */
  GRANT_TYPE_UNSUPPORTED(10000),
  /**
   * 无效Scope.
   */
  SCOPE_INVALID(10001),
  /**
   * 认证失败.
   */
  AUTHENTICATION_ERROR(10002),
  /**
   * 无权限访问.
   */
  ACCESS_DENIED(10003),
  /**
   * 用户不存在.
   */
  USER_NOT_EXIST(10004),
  /**
   * 账号密码错误.
   */
  GRANT_INVALID(10005),
  /**
   * 用户已被禁用.
   */
  USER_DISABLED(10006),
  /**
   * 用户已被删除.
   */
  USER_DELETED(10007),
  /**
   * 无效Token.
   */
  TOKEN_INVALID(10008),
  /**
   * 过期Token.
   */
  TOKEN_EXPIRED(10009),
  /**
   * 请求方式不支持.
   */
  REQUEST_METHOD_NOT_SUPPORTED(10010),
  /**
   * 服务器内部错误.
   */
  INTERNAL_SERVER_ERROR(10011);

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
