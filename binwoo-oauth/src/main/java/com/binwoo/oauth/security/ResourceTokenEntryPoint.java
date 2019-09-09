package com.binwoo.oauth.security;

import com.binwoo.framework.http.exception.HttpExceptionCode;
import com.binwoo.framework.http.response.HttpResponse;
import com.binwoo.framework.http.response.HttpResponseBuilder;
import com.binwoo.framework.util.JwtUtils;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

/**
 * 自定义Token异常信息,token,比如token过期/验证错误.
 *
 * @author hleluo
 * @date 2019/9/2 23:12
 */
public class ResourceTokenEntryPoint implements AuthenticationEntryPoint {

  /**
   * token秘钥.
   */
  @Value("${oauth.jwt.signing-key}")
  private String signingKey;

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {
    String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    HttpExceptionCode code;
    if (StringUtils.isEmpty(authorization)) {
      code = HttpAuthExceptionCode.TOKEN_INVALID;
    } else {
      String token = authorization.trim().substring(authorization.indexOf(" ")).trim();
      JwtUtils.Result result = JwtUtils.parse(token, signingKey);
      if (result.isValid()) {
        code = result.isExpired() ? HttpAuthExceptionCode.TOKEN_EXPIRED
            : HttpAuthExceptionCode.AUTHENTICATION_ERROR;
      } else {
        code = HttpAuthExceptionCode.TOKEN_INVALID;
      }
    }
    HttpResponse<String> response = HttpResponseBuilder.failure(code);
    Map<String, Object> map = HttpResponseBuilder.toMapWithoutNullValue(response);
    httpServletResponse.setContentType("application/json");
    httpServletResponse.setStatus(HttpStatus.OK.value());
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(httpServletResponse.getOutputStream(), map);
  }
}
