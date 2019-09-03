package com.binwoo.oauth.security;

import com.binwoo.oauth.exception.AuthException;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * 登录抛异常 比如用户或者密码/scope报错接住的异常.
 *
 * @author hleluo
 * @date 2019/8/31 20:25
 */
@Component
public class AuthExceptionTranslator implements WebResponseExceptionTranslator {

  @Override
  public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
    e.printStackTrace();
    if (e instanceof InvalidTokenException) {
      //无效Token.
      InvalidTokenException invalidTokenException = (InvalidTokenException) e;
      return ResponseEntity.status(invalidTokenException.getHttpErrorCode())
          .body(new AuthException(e.getMessage()));
    } else if (e instanceof UsernameNotFoundException) {
      // 用户不存在.
      return ResponseEntity.status(HttpStatus.OK)
          .body(new AuthException(HttpAuthExceptionCode.USER_NOT_EXIST, e.getMessage()));
    } else if (e instanceof InvalidGrantException) {
      // 账号密码错误.
      return ResponseEntity.status(HttpStatus.OK)
          .body(new AuthException(HttpAuthExceptionCode.PASSWORD_ERROR, e.getMessage()));
    } else if (e instanceof OAuth2Exception) {
      // 授权异常.
      OAuth2Exception auth2Exception = (OAuth2Exception) e;
      return ResponseEntity.status(auth2Exception.getHttpErrorCode())
          .body(new AuthException(e.getMessage()));
    } else if (e instanceof DisabledException) {
      // 账号禁用，不可访问.
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new AuthException(e.getMessage()));
    } else if (e instanceof AuthenticationException) {
      // 权限异常.
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new AuthException(e.getMessage()));
    } else if (e instanceof AccessDeniedException) {
      // 拒绝访问.
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new AuthException(e.getMessage()));
    } else if (e instanceof HttpRequestMethodNotSupportedException) {
      // 请求方式不支持.
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new AuthException(e.getMessage()));
    }
    // 服务器内部错误.
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new AuthException(e.getMessage()));
  }
}
