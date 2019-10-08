package com.binwoo.common.filter;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限上下文.
 *
 * @author admin
 * @date 2019/10/8 15:15
 */
public class AuthContext {

  private HttpServletRequest request;
  private String clientId;
  private String username;

  public HttpServletRequest getRequest() {
    return request;
  }

  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
