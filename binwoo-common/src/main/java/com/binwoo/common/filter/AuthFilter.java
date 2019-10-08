package com.binwoo.common.filter;

import com.alibaba.fastjson.JSON;
import com.binwoo.common.http.exception.HttpBaseExceptionCode;
import com.binwoo.common.http.response.HttpResponseBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器.
 *
 * @author admin
 * @date 2019/10/8 15:07
 */
public class AuthFilter implements Filter {

  private boolean tokenEnable = true;
  private String[] tokenExcludes;
  private String tokenSecret;
  private String authUrl;
  private String resourceId;
  private boolean resourceEnable = false;
  private boolean apiEnable = false;
  private String[] apiExcludes;
  private boolean logEnable = false;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    AuthContext context = new AuthContext();
    context.setRequest((HttpServletRequest) request);
    if (tokenEnable) {
      //token验证
      AuthTokenVerifier tokenVerifier = new AuthTokenVerifier(context.getRequest());
      boolean success = tokenVerifier.verify(tokenExcludes, tokenSecret);
      context.setUsername(tokenVerifier.getUsername());
      context.setClientId(tokenVerifier.getClientId());
      if (success) {
        if (tokenVerifier.isExpired()) {
          write(response, HttpBaseExceptionCode.TOKEN_EXPIRED);
          return;
        }
      } else {
        write(response, HttpBaseExceptionCode.TOKEN_INVALID);
        return;
      }
    }
    if ((resourceEnable || apiEnable) && authUrl == null) {
      write(response, HttpBaseExceptionCode.AUTH_URL_NULL);
      return;
    }
    if (resourceEnable && resourceId == null) {
      write(response, HttpBaseExceptionCode.RESOURCE_ID_NULL);
      return;
    }
    try {
      AuthContextHolder.set(context);
      chain.doFilter(request, response);
    } finally {
      AuthContextHolder.clear();
    }
  }

  @Override
  public void destroy() {

  }

  private void write(ServletResponse servletResponse, HttpBaseExceptionCode code)
      throws IOException {
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=utf-8");
    PrintWriter printWriter = response.getWriter();
    String json = JSON.toJSONString(HttpResponseBuilder.failure(code));
    printWriter.write(json);
    printWriter.flush();
  }
}
