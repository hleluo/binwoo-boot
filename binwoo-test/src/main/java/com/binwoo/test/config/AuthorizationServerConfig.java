package com.binwoo.test.config;

import com.binwoo.framework.oauth.AuthorizationServerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.binwoo.test.config.
 *
 * @author luoj
 * @date 2019/8/20 11:33
 */
@Configuration
public class AuthorizationServerConfig {

  @Bean
  public AuthorizationServerConfigurer configurer() {
    AuthorizationServerConfigurer configurer = new AuthorizationServerConfigurer();
    configurer.setEnable(true);
    return configurer;
  }

}
