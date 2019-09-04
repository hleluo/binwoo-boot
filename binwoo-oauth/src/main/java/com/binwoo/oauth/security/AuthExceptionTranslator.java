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
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
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
    HttpAuthExceptionCode code;
    if (e instanceof InvalidTokenException) {
      //无效Token.
      code = HttpAuthExceptionCode.INVALID_TOKEN;
    } else if (e instanceof UsernameNotFoundException) {
      // 用户不存在.
      code = HttpAuthExceptionCode.USER_NOT_EXIST;
    } else if (e instanceof InvalidGrantException) {
      // 账号密码错误.
      code = HttpAuthExceptionCode.INVALID_GRANT;
    } else if (e instanceof InvalidScopeException) {
      // 无效Scope.
      code = HttpAuthExceptionCode.INVALID_SCOPE;
    } else if (e instanceof OAuth2Exception) {
      // 授权异常.
      code = HttpAuthExceptionCode.AUTHENTICATION_ERROR;
    } else if (e instanceof DisabledException) {
      // 账号禁用，不可访问.
      code = HttpAuthExceptionCode.USER_DISABLED;
    } else if (e instanceof AuthenticationException) {
      try {
        // 用户自定义异常.
        code = HttpAuthExceptionCode.valueOf(e.getMessage());
      } catch (IllegalArgumentException ex) {
        // 用户认证异常.
        code = HttpAuthExceptionCode.AUTHENTICATION_ERROR;
      }
    } else if (e instanceof AccessDeniedException) {
      // 拒绝访问.
      code = HttpAuthExceptionCode.ACCESS_DENIED;
    } else if (e instanceof HttpRequestMethodNotSupportedException) {
      // 请求方式不支持.
      code = HttpAuthExceptionCode.REQUEST_METHOD_NOT_SUPPORTED;
    } else {
      // 服务器内部错误.
      code = HttpAuthExceptionCode.INTERNAL_SERVER_ERROR;
    }
    return ResponseEntity.status(HttpStatus.OK).body(new AuthException(code));
  }
}
