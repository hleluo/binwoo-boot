package com.binwoo.oauth.token;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * JWT Token生成规则.
 *
 * @author hleluo
 * @date 2019/8/29 23:27
 */
public class AuthTokenGranter {

  private AuthorizationServerEndpointsConfigurer endpoints;
  private AuthenticationManager authenticationManager;

  /**
   * 构造函数.
   *
   * @param endpoints 权限配置节点
   * @param authenticationManager 认证管理器
   */
  public AuthTokenGranter(AuthorizationServerEndpointsConfigurer endpoints,
      AuthenticationManager authenticationManager) {
    this.endpoints = endpoints;
    this.authenticationManager = authenticationManager;
  }

  /**
   * 构建Token生成器.
   *
   * @return TokenGranter
   */
  public TokenGranter build() {
    return new CompositeTokenGranter(buildTokenGranters(endpoints));
  }

  /**
   * 针对所有验证返回refresh_token.
   *
   * @param endpoints 权限配置节点
   * @return TokenGranter列表
   */
  private List<TokenGranter> buildTokenGranters(
      AuthorizationServerEndpointsConfigurer endpoints) {
    ClientDetailsService clientDetails = endpoints.getClientDetailsService();
    AuthorizationCodeServices authorizationCodeServices = endpoints.getAuthorizationCodeServices();
    OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetails);
    AuthorizationServerTokenServices tokenServices = endpoints.getTokenServices();
    AuthorizationCodeTokenGranter authorizationCodeTokenGranter = new AuthorizationCodeTokenGranter(
        tokenServices, authorizationCodeServices, clientDetails, requestFactory);
    RefreshTokenGranter refreshTokenGranter = new RefreshTokenGranter(tokenServices, clientDetails,
        requestFactory);

    ClientCredentialsTokenGranter clientCredentialsTokenGranter = new ClientCredentialsTokenGranter(
        tokenServices, clientDetails, requestFactory);
    // 设置返回refresh_token
    clientCredentialsTokenGranter.setAllowRefresh(true);
    List<TokenGranter> tokenGranters = new ArrayList<>();
    tokenGranters.add(authorizationCodeTokenGranter);
    tokenGranters.add(refreshTokenGranter);
    ImplicitTokenGranter implicitTokenGranter = new ImplicitTokenGranter(tokenServices,
        clientDetails, requestFactory);
    tokenGranters.add(implicitTokenGranter);
    tokenGranters.add(clientCredentialsTokenGranter);
    if (authenticationManager != null) {
      tokenGranters.add(
          new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetails,
              requestFactory));
    }
    return tokenGranters;
  }
}
