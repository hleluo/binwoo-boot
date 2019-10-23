package com.binwoo.oauth.client;

import com.binwoo.common.http.response.HttpResponseBuilder;
import com.binwoo.oauth.entity.Client;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.util.JacksonUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

  private final ClientLoader clientLoader;

  /**
   * 构造函数.
   *
   * @param clientLoader 加载器.
   */
  public ClientFilter(ClientLoader clientLoader) {
    this.clientLoader = clientLoader;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    String clientId = request.getParameter(PARAM_KEY_CLIENT_ID);
    if (clientId == null) {
      write(servletResponse, HttpAuthExceptionCode.CLIENT_NOT_EXIST);
      return;
    }
    Client client = clientLoader.load(clientId);
    if (client == null) {
      write(servletResponse, HttpAuthExceptionCode.CLIENT_NOT_EXIST);
      return;
    }
    if (client.isDisable()) {
      write(servletResponse, HttpAuthExceptionCode.CLIENT_DISABLED);
      return;
    }
    if (client.isDeleted()) {
      write(servletResponse, HttpAuthExceptionCode.CLIENT_DELETED);
      return;
    }
    if (client.getExpireTime() != null && client.getExpireTime().isBefore(LocalDateTime.now())) {
      write(servletResponse, HttpAuthExceptionCode.CLIENT_EXPIRED);
      return;
    }
    String clientSecret = request.getParameter(PARAM_KEY_CLIENT_SECRET);
    if (!clientSecret.equals(client.getSecret())) {
      write(servletResponse, HttpAuthExceptionCode.CLIENT_INVALID);
      return;
    }
    try {
      ClientParam param = new ClientParam();
      param.setClientId(clientId);
      param.setClient(client);
      ClientParamContext.set(param);
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      ClientParamContext.clear();
    }
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
