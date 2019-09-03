package com.binwoo.oauth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 自定义Token异常信息,token,比如token过期/验证错误.
 *
 * @author hleluo
 * @date 2019/9/2 23:12
 */
public class ResourceTokenEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {
    Map map = new HashMap();
    map.put("code", "401");
    map.put("message", e.getMessage());
    httpServletResponse.setContentType("application/json");
    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(httpServletResponse.getOutputStream(), map);
    } catch (Exception ex) {
      throw new ServletException();
    }
  }
}
