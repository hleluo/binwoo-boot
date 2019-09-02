package com.binwoo.oauth.config;

import com.binwoo.oauth.security.AuthTokenEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * 资源服务适配器.
 *
 * @author hleluo
 * @date 2019/8/29 23:41
 */
@Configuration
@EnableResourceServer
public class AuthResourceConfig extends ResourceServerConfigurerAdapter {

  /**
   * 资源id.
   */
  @Value("${oauth.resource.id}")
  private String resourceId;
  /**
   * 忽略验证的URL，多个都好隔开.
   */
  @Value("${oauth.resource.exclude}")
  private String exclude;

  /**
   * 配置详见JwtTokenConfig.
   */
  private final ResourceServerTokenServices tokenServices;

  /**
   * 构造函数.
   *
   * @param tokenServices token服务.
   */
  @Autowired
  public AuthResourceConfig(ResourceServerTokenServices tokenServices) {
    this.tokenServices = tokenServices;
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(resourceId).tokenServices(tokenServices)
        .authenticationEntryPoint(new AuthTokenEntryPoint());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    //忽略验证的URL.
    String[] excludes = exclude.split(",");
    http.authorizeRequests()
        //配置忽略验证的URL.
        .antMatchers(excludes).permitAll()
        .anyRequest().authenticated()
        .and()
        //跨域配置.
        .csrf().disable();
  }
}
