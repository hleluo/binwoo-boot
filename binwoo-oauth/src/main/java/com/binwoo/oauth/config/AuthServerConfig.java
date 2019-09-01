package com.binwoo.oauth.config;

import com.binwoo.oauth.security.AuthExceptionTranslator;
import com.binwoo.oauth.security.AuthTokenEndpointFilter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * OAuth Server Config.
 *
 * @author hleluo
 * @date 2019/8/29 22:28
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

  /**
   * 客户端id.
   */
  @Value("${oauth.server.client-id}")
  private String clientId;
  /**
   * 客户端密码.
   */
  @Value("${oauth.server.secret}")
  private String secret;
  /**
   * 支持的GrantType，多个都好隔开.
   */
  @Value("${oauth.server.grant-type}")
  private String grantType;
  /**
   * 作用域，多个逗号隔开.
   */
  @Value("${oauth.server.scope}")
  private String scope;
  /**
   * 资源id，多个逗号隔开.
   */
  @Value("${oauth.server.resource-id}")
  private String resourceId;

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
  private final JwtAccessTokenConverter jwtAccessTokenConverter;
  /**
   * token额外信息，详见JwtTokenConfig.
   */
  private final TokenEnhancer tokenEnhancer;
  /**
   * 加密方式，详见WebSecurityConfig.
   */
  private final PasswordEncoder passwordEncoder;
  /**
   * 异常拦截及数据自定义.
   */
  private final AuthExceptionTranslator authExceptionTranslator;
  /**
   * 验证拦截.
   */
  private final AuthTokenEndpointFilter authTokenEndpointFilter;

  @Autowired
  public AuthServerConfig(AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService, TokenStore tokenStore,
      JwtAccessTokenConverter jwtAccessTokenConverter, TokenEnhancer tokenEnhancer,
      PasswordEncoder passwordEncoder, AuthExceptionTranslator authExceptionTranslator,
      AuthTokenEndpointFilter authTokenEndpointFilter) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.tokenStore = tokenStore;
    this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    this.tokenEnhancer = tokenEnhancer;
    this.passwordEncoder = passwordEncoder;
    this.authExceptionTranslator = authExceptionTranslator;
    this.authTokenEndpointFilter = authTokenEndpointFilter;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    String[] grantTypes = grantType.split(",");
    String[] scopes = scope.split(",");
    String[] resourceIds = resourceId.split(",");
    clients.inMemory().withClient(clientId)
        //账号密码加密的同时，客户端密码也要加密.
        .secret(passwordEncoder.encode(secret))
        .authorizedGrantTypes(grantTypes)
        .scopes(scopes)
        .resourceIds(resourceIds)
        .accessTokenValiditySeconds(7200)
        .refreshTokenValiditySeconds(7400);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    List<TokenEnhancer> enhancers = new ArrayList<>();
    //token额外信息.
    enhancers.add(tokenEnhancer);
    enhancers.add(jwtAccessTokenConverter);
    enhancerChain.setTokenEnhancers(enhancers);
    endpoints.tokenStore(tokenStore)
        .accessTokenConverter(jwtAccessTokenConverter)
        .tokenEnhancer(enhancerChain)
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
        //异常拦截处理.
        .exceptionTranslator(authExceptionTranslator);
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
