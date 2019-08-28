package com.binwoo.framework.listener;

import javax.servlet.ServletContext;

/**
 * com.binwoo.framework.listener.
 *
 * @author luoj
 * @date 2019/8/20 17:23
 */
public class ServletContextManager {

  private ServletContext servletContext;

  public ServletContext getServletContext() {
    return servletContext;
  }

  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  private static class Manager {

    private static ServletContextManager INSTANCE = new ServletContextManager();

  }

  public static ServletContextManager getInstance() {
    return ServletContextManager.Manager.INSTANCE;
  }

}
