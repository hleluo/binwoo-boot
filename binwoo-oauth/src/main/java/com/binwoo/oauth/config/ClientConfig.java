package com.binwoo.oauth.config;

import com.binwoo.oauth.client.ClientFilter;
import com.binwoo.oauth.client.ClientLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 客户端配置器.
 *
 * @author admin
 * @date 2019/9/12 17:40
 */
@Configuration
public class ClientConfig {

  /**
   * OAuth拦截.
   */
  private static final String[] URL_PATTERNS = {"/oauth/token"};

  private final ClientLoader clientLoader;

  @Autowired
  public ClientConfig(ClientLoader clientLoader) {
    this.clientLoader = clientLoader;
  }

  /**
   * 客户端拦截器.
   *
   * @return 客户端拦截器
   */
  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean<ClientFilter> bean = new FilterRegistrationBean<ClientFilter>();
    bean.setFilter(new ClientFilter(clientLoader));
    bean.addUrlPatterns(URL_PATTERNS);
    bean.setOrder(Integer.MIN_VALUE);
    return bean;
  }

}
