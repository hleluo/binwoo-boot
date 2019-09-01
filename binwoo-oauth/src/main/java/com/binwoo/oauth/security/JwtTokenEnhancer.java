package com.binwoo.oauth.security;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * @author hleluo
 * @date 2019/8/31 16:18
 */
public class JwtTokenEnhancer implements TokenEnhancer {

  private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
  private static final String GRANT_TYPE_PASSWORD = "password";

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken,
      OAuth2Authentication oAuth2Authentication) {
    String grantType = oAuth2Authentication.getOAuth2Request().getGrantType();
    //只有如下两种模式才能获取到当前用户信息.
    if (GRANT_TYPE_AUTHORIZATION_CODE.equals(grantType) || GRANT_TYPE_PASSWORD.equals(grantType)) {
      String username = oAuth2Authentication.getUserAuthentication().getName();

    }
    return oAuth2AccessToken;
  }
}
