package com.binwoo.framework.oauth;

/**
 * com.binwoo.framework.oauth.
 *
 * @author luoj
 * @date 2019/8/20 11:15
 */
public class AuthorizationServerConfigurer {

  private AuthorizationServerManager manager;

  public AuthorizationServerConfigurer() {
    manager = AuthorizationServerManager.getInstance();
  }

  public void setLoginHandler(AuthorizationLoginHandler loginHandler) {
    manager.setLoginHandler(loginHandler);
  }

  public void setEnable(boolean enable) {
    manager.setEnable(enable);
  }

}
