package com.binwoo.oauth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义Auth异常.
 *
 * @author hleluo
 * @date 2019/8/31 23:06
 */
@JsonSerialize(using = AuthJsonSerializer.class)
public class AuthException extends OAuth2Exception {

  private AuthExceptionFlag flag;

  /**
   * 构造函数.
   *
   * @param msg 消息
   * @param t 异常
   */
  public AuthException(String msg, Throwable t) {
    super(msg, t);
  }

  /**
   * 构造函数.
   *
   * @param msg 消息.
   */
  public AuthException(String msg) {
    super(msg);
  }

  /**
   * 构造函数.
   *
   * @param flag 标识.
   * @param msg 消息.
   */
  public AuthException(AuthExceptionFlag flag, String msg) {
    super(msg);
    this.flag = flag;
  }

  public AuthExceptionFlag getFlag() {
    return flag;
  }

  public void setFlag(AuthExceptionFlag flag) {
    this.flag = flag;
  }
}
