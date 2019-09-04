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

  private HttpAuthExceptionCode code;

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
   * @param code 异常代码.
   */
  public AuthException(HttpAuthExceptionCode code) {
    super(code.name());
    this.code = code;
  }

  public HttpAuthExceptionCode getCode() {
    return code;
  }

  public void setCode(HttpAuthExceptionCode code) {
    this.code = code;
  }
}
