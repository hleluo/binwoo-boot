package com.binwoo.framework.oauth;

import com.binwoo.framework.listener.ServletContextManager;
import com.binwoo.framework.servlet.AuthorizationTokenServlet;
import javax.servlet.ServletRegistration;

/**
 * com.binwoo.framework.oauth.
 *
 * @author luoj
 * @date 2019/8/20 11:15
 */
public class AuthorizationServerManager {

  private AuthorizationLoginHandler loginHandler;
  private boolean enable = false;

  public AuthorizationServerManager() {
    this.loginHandler = new AuthorizationLoginHandler();
  }

  public AuthorizationLoginHandler getLoginHandler() {
    return loginHandler;
  }

  public void setLoginHandler(AuthorizationLoginHandler loginHandler) {
    this.loginHandler = loginHandler;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
    init();
  }

  private void init() {
    /*if (this.enable) {
      ServletRegistration.Dynamic dynamic = ServletContextManager.getInstance().getServletContext()
          .addServlet(AuthorizationTokenServlet.class.getSimpleName(),
              new AuthorizationTokenServlet());
      dynamic.addMapping(AuthorizationConstant.SERVLET_MAPPING_TOKEN);
    }*/
  }

  private static class Manager {

    private static AuthorizationServerManager INSTANCE = new AuthorizationServerManager();

  }

  public static AuthorizationServerManager getInstance() {
    return Manager.INSTANCE;
  }
}
