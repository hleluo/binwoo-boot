package com.binwoo.oauth.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * 异常处理.
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
      InvalidTokenException invalidTokenException = (InvalidTokenException) e;
      return ResponseEntity.status(invalidTokenException.getHttpErrorCode())
          .body(new AuthException(e.getMessage()));
    } else if (e instanceof InvalidGrantException) {
      InvalidGrantException invalidGrantException = (InvalidGrantException) e;
      return ResponseEntity.status(invalidGrantException.getHttpErrorCode())
          .body(new AuthException("54345"));
    } else if (e instanceof OAuth2Exception) {
      OAuth2Exception auth2Exception = (OAuth2Exception) e;
      return ResponseEntity.status(auth2Exception.getHttpErrorCode())
          .body(new AuthException(e.getMessage()));
    } else if (e instanceof DisabledException) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new AuthException(e.getMessage()));
    } else if (e instanceof AuthenticationException) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new AuthException(e.getMessage()));
    } else if (e instanceof AccessDeniedException) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new AuthException(e.getMessage()));
    } else if (e instanceof HttpRequestMethodNotSupportedException) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new AuthException(e.getMessage()));
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new AuthException(e.getMessage()));
  }
}
