package com.binwoo.oauth.config;

import com.binwoo.oauth.token.AuthTokenEnhancer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Jwt Token配置.
 *
 * @author hleluo
 * @date 2019/8/31 16:17
 */
@Configuration
public class AuthTokenConfig {

  /**
   * token秘钥.
   */
  @Value("${oauth.jwt.signing-key}")
  private String signingKey;

  /**
   * token方式.
   *
   * @return TokenStore
   */
  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  /**
   * token秘钥设置.
   *
   * @return JwtAccessTokenConverter
   */
  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
    accessTokenConverter.setSigningKey(signingKey);
    return accessTokenConverter;
  }

  /**
   * token额外数据.
   *
   * @return TokenEnhancer
   */
  @Bean
  public TokenEnhancer tokenEnhancer() {
    return new AuthTokenEnhancer();
  }

  /**
   * token服务.
   *
   * @return DefaultTokenServices
   */
  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    //是否支持刷新Token.
    defaultTokenServices.setSupportRefreshToken(true);
    return defaultTokenServices;
  }

}
