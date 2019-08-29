package com.binwoo.oauth.config;

import com.binwoo.oauth.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * OAuth Server Config.
 *
 * @author hleluo
 * @date 2019/8/29 22:28
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

  @Value("${oauth.server.grant-type}")
  private String grantType;
  @Value("${oauth.jwt.signing-key}")
  private String signingKey;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private WebResponseExceptionTranslator webResponseExceptionTranslator;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    String[] grantTypes = grantType.split(",");
    clients.inMemory().authorizedGrantTypes(grantTypes);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .tokenStore(tokenStore())
        .accessTokenConverter(jwtAccessTokenConverter())
        .authenticationManager(authenticationManager)
        .exceptionTranslator(customWebResponseExceptionTranslator)
        .userDetailsService(userDetailsService)
        //开启刷新token
        .reuseRefreshTokens(true)
        .tokenServices(tokenServices());
    ;
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security
        //允许表单认证  这段代码在授权码模式下会导致无法根据code　获取token
        .allowFormAuthenticationForClients()
        .tokenKeyAccess("permitAll()")
        //isAuthenticated():排除anonymous；isFullyAuthenticated():排除anonymous以及remember-me
        .checkTokenAccess("isAuthenticated()")
        //client password加密即oauth_client_details表的client_secret字段
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(signingKey);
    return converter;
  }

  /**
   * 重写默认的资源服务token
   */
  @Bean
  public DefaultTokenServices tokenServices() {
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
    defaultTokenServices.setTokenStore(tokenStore());
    defaultTokenServices.setSupportRefreshToken(true);
    defaultTokenServices.setAccessTokenValiditySeconds(7200);
    return defaultTokenServices;
  }
}
