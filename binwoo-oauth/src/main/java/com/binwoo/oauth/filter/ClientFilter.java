package com.binwoo.oauth.filter;

import com.binwoo.framework.http.response.HttpResponseBuilder;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.repository.ClientRepository;
import com.binwoo.oauth.util.JacksonUtils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 客户端信息验证拦截器.
 *
 * @author admin
 * @date 2019/9/12 17:33
 */
public class ClientFilter implements Filter {

  /**
   * 客户端账号.
   */
  private static final String PARAM_KEY_CLIENT_ID = "client_id";
  /**
   * 客户端密码.
   */
  private static final String PARAM_KEY_CLIENT_SECRET = "client_secret";

  private ClientRepository clientRepository;

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    if (clientRepository == null) {
      ServletContext servletContext = request.getSession().getServletContext();
      WebApplicationContext cxt = WebApplicationContextUtils
          .getWebApplicationContext(servletContext);
      if (cxt != null) {
        clientRepository = cxt.getBean(ClientRepository.class);
      }
    }
    String clientId = request.getParameter(PARAM_KEY_CLIENT_ID);
    String clientSecret = request.getParameter(PARAM_KEY_CLIENT_SECRET);
    //write(servletResponse, HttpAuthExceptionCode.ACCESS_DENIED);
    filterChain.doFilter(servletRequest, servletResponse);
  }

  private void write(ServletResponse servletResponse, HttpAuthExceptionCode code)
      throws IOException {
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=utf-8");
    PrintWriter printWriter = response.getWriter();
    String json = JacksonUtils.toJsonString(HttpResponseBuilder.failure(code));
    printWriter.write(json == null ? "{}" : json);
    printWriter.flush();
  }
}
