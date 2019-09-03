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
   * 密码错误.
   */
  PASSWORD_ERROR(10001);

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
