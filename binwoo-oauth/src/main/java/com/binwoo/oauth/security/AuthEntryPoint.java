package com.binwoo.oauth.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 自定义AuthenticationEntryPoint实现类.
 *
 * @author hleluo
 * @date 2019/8/29 23:06
 */
@Slf4j
public class AuthEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    try {
//https://www.jianshu.com/p/2d344075d395
      //https://github.com/jeesun/oauthserver/tree/master/api/src/main/java/com/simon/common/config
    } catch (Exception e) {
      throw new ServletException();
    }
  }

}
