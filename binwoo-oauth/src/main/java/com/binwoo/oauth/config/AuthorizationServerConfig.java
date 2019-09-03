package com.binwoo.oauth.config;

import com.binwoo.oauth.detail.ClientDetailsServiceImpl;
import com.binwoo.oauth.security.AuthExceptionTranslator;
import com.binwoo.oauth.security.AuthTokenEndpointFilter;
import com.binwoo.oauth.token.AuthTokenGranter;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 授权服务配置.
 *
 * @author hleluo
 * @date 2019/8/29 22:28
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  /**
   * 详见WebSecurityConfig.
   */
  private final AuthenticationManager authenticationManager;
  /**
   * 详见WebSecurityConfig.
   */
  private final UserDetailsService userDetailsService;
  /**
   * 详见JwtTokenConfig.
   */
  private final TokenStore tokenStore;
  /**
   * 详见JwtTokenConfig.
   */
  private final JwtAccessTokenConverter accessTokenConverter;
  /**
   * 详见JwtTokenConfig.
   */
  private final TokenEnhancer tokenEnhancer;
  /**
   * 异常拦截及数据自定义.
   */
  private final AuthExceptionTranslator authExceptionTranslator;
  /**
   * 验证拦截.
   */
  private final AuthTokenEndpointFilter authTokenEndpointFilter;

  /**
   * 客户端信息服务.
   *
   * @return 客户端信息服务.
   */
  @Bean
  public ClientDetailsService clientDetails() {
    return new ClientDetailsServiceImpl();
  }

  /**
   * 构造函数.
   *
   * @param authenticationManager 认证管理器
   * @param userDetailsService 用户详情服务
   * @param tokenStore token存储
   * @param accessTokenConverter token转换器
   * @param tokenEnhancer token信息扩展
   * @param authExceptionTranslator 异常捕捉
   * @param authTokenEndpointFilter token认证拦截
   */
  @Autowired
  public AuthorizationServerConfig(AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService, TokenStore tokenStore,
      JwtAccessTokenConverter accessTokenConverter, TokenEnhancer tokenEnhancer,
      AuthExceptionTranslator authExceptionTranslator,
      AuthTokenEndpointFilter authTokenEndpointFilter) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.tokenStore = tokenStore;
    this.accessTokenConverter = accessTokenConverter;
    this.tokenEnhancer = tokenEnhancer;
    this.authExceptionTranslator = authExceptionTranslator;
    this.authTokenEndpointFilter = authTokenEndpointFilter;

  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    //自定义客户端信息.
    clients.withClientDetails(clientDetails());
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    //token额外信息.
    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    enhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, accessTokenConverter));
    endpoints
        .tokenStore(tokenStore)
        .accessTokenConverter(accessTokenConverter)
        .tokenEnhancer(enhancerChain)
        // 指定认证管理器.
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
        //允许 GET、POST 请求获取 token，即访问端点：oauth/token.
        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
        //登录异常拦截处理.
        .exceptionTranslator(authExceptionTranslator);
    //自定义任何情况下都返回refresh_token，如无refresh_token权限则不返回.
    endpoints.tokenGranter(new AuthTokenGranter(endpoints, authenticationManager).build());
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security
        //允许表单认证  这段代码在授权码模式下会导致无法根据code　获取token
        .allowFormAuthenticationForClients()
        .tokenKeyAccess("permitAll()")
        //isAuthenticated():排除anonymous；isFullyAuthenticated():排除anonymous以及remember-me
        .checkTokenAccess("isAuthenticated()")
        //token认证拦截.
        .addTokenEndpointAuthenticationFilter(authTokenEndpointFilter);
  }

}
