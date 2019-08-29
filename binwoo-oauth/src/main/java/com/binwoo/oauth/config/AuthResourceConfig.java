package com.binwoo.oauth.config;

import com.binwoo.oauth.security.AuthEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author hleluo
 * @date 2019/8/29 23:41
 */
@Configuration
@EnableResourceServer
public class AuthResourceConfig extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.authenticationEntryPoint(new AuthEntryPoint());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    //如果 启用 http.addFilterBefore(oAuth2AuthenticationFilter,AbstractPreAuthenticatedProcessingFilter.class) 代码 则需要启用下面被注释的代码
    OAuth2AuthenticationProcessingFilter oAuth2AuthenticationFilter = new OAuth2AuthenticationProcessingFilter();
    OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
    oAuth2AuthenticationEntryPoint.setExceptionTranslator(webResponseExceptionTranslator());
    oAuth2AuthenticationFilter.setAuthenticationEntryPoint(oAuth2AuthenticationEntryPoint);
    OAuth2AuthenticationManager oAuth2AuthenticationManager = new OAuth2AuthenticationManager();
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore);
    oAuth2AuthenticationManager.setTokenServices(defaultTokenServices);
    oAuth2AuthenticationFilter.setAuthenticationManager(oAuth2AuthenticationManager);

    // 配置那些资源需要保护的
    http.requestMatchers().antMatchers("/api/**")
        .and()
        .authorizeRequests()
        .antMatchers("/api/**").authenticated()
        .and()
        //权限认证失败业务处理
        .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
        //认证失败的业务处理
        .authenticationEntryPoint(customAuthenticationEntryPoint());
    //自定义token过滤 token校验失败后自定义返回数据格式
    http.addFilterBefore(permitAuthenticationFilter,
        AbstractPreAuthenticatedProcessingFilter.class);

  }
}
