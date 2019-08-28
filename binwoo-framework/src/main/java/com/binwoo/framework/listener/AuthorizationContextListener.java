package com.binwoo.framework.listener;

import com.binwoo.framework.oauth.AuthorizationConstant;
import com.binwoo.framework.servlet.AuthorizationTokenServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

/**
 * com.binwoo.framework.servlet.
 *
 * @author luoj
 * @date 2019/8/20 11:05
 */
@WebListener
public class AuthorizationContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext sc = sce.getServletContext();
//    ServletContextManager.getInstance().setServletContext(sc);
    ServletRegistration.Dynamic dynamic = sc
        .addServlet(AuthorizationTokenServlet.class.getSimpleName(),
            AuthorizationTokenServlet.class);
    dynamic.addMapping(AuthorizationConstant.SERVLET_MAPPING_TOKEN);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

  }
}
