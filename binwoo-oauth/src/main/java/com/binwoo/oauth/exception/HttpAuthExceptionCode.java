package com.binwoo.oauth.exception;

import com.binwoo.common.http.exception.HttpExceptionCode;

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
  GRANT_TYPE_UNSUPPORTED(10100),
  /**
   * 无效Scope.
   */
  SCOPE_INVALID(10101),
  /**
   * 认证失败.
   */
  AUTHENTICATION_ERROR(10102),
  /**
   * 无权限访问.
   */
  ACCESS_DENIED(10103),
  /**
   * 客户端不存在.
   */
  CLIENT_NOT_EXIST(10200),
  /**
   * 客户端被禁用.
   */
  CLIENT_DISABLED(10201),
  /**
   * 客户端被删除.
   */
  CLIENT_DELETED(10202),
  /**
   * 客户端已过期.
   */
  CLIENT_EXPIRED(10203),
  /**
   * 密码错误.
   */
  CLIENT_INVALID(10204),
  /**
   * 客户端已存在.
   */
  CLIENT_EXIST(10205),
  /**
   * 客户端秘钥为空.
   */
  CLIENT_SECRET_NULL(10206),
  /**
   * 用户不存在.
   */
  USER_NOT_EXIST(10300),
  /**
   * 账号密码错误.
   */
  GRANT_INVALID(10301),
  /**
   * 用户已被禁用.
   */
  USER_DISABLED(10302),
  /**
   * 用户已被删除.
   */
  USER_DELETED(10303),
  /**
   * 用户已过期.
   */
  USER_EXPIRED(10304),
  /**
   * 用户未被激活.
   */
  USER_UNACTIVATED(10305),
  /**
   * 用户名已存在.
   */
  USERNAME_EXIST(10306),
  /**
   * 用户密码为空.
   */
  USER_PASSWORD_NULL(10307),
  /**
   * 无效Token.
   */
  TOKEN_INVALID(10400),
  /**
   * 过期Token.
   */
  TOKEN_EXPIRED(10401),
  /**
   * 请求方式不支持.
   */
  REQUEST_METHOD_NOT_SUPPORTED(10000),
  /**
   * 服务器内部错误.
   */
  INTERNAL_SERVER_ERROR(10001),
  /**
   * 应用已存在.
   */
  APP_EXIST(11000),
  /**
   * 资源已存在.
   */
  RESOURCE_EXIST(11010),
  /**
   * 权职已存在.
   */
  AUTHORITY_EXIST(11020),
  /**
   * 字典Code已存在.
   */
  DICTIONARY_CODE_EXIST(11030);

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
