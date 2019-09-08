package com.binwoo.oauth.config;

import com.binwoo.oauth.security.ResourceTokenEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 资源服务适配器.
 *
 * @author hleluo
 * @date 2019/8/29 23:41
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  /**
   * API拦截.
   */
  private static final String API_REQUEST_INTERCEPT = "/api/**";

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
  private final AccessDeniedHandler accessDeniedHandler;

  /**
   * 构造函数.
   *
   * @param tokenServices token服务.
   */
  @Autowired
  public ResourceServerConfig(ResourceServerTokenServices tokenServices,
      AccessDeniedHandler accessDeniedHandler) {
    this.tokenServices = tokenServices;
    this.accessDeniedHandler = accessDeniedHandler;
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(resourceId).tokenServices(tokenServices)
        //Token异常处理.
        .authenticationEntryPoint(new ResourceTokenEntryPoint())
        //无权限访问异常.
        .accessDeniedHandler(accessDeniedHandler);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    //忽略验证的URL.
    String[] excludes = exclude.split(",");
    String[] intercepts = new String[excludes.length + 1];
    System.arraycopy(excludes, 0, intercepts, 0, excludes.length);
    intercepts[intercepts.length - 1] = API_REQUEST_INTERCEPT;
    http.requestMatchers().antMatchers(intercepts)
        .and()
        .authorizeRequests()
        //配置忽略验证的URL.
        .antMatchers(excludes).permitAll()
        .antMatchers(API_REQUEST_INTERCEPT).authenticated();
  }
}
