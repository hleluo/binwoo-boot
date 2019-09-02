package com.binwoo.oauth.security;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * Jwt Token额外信息扩展.
 *
 * @author hleluo
 * @date 2019/8/31 16:18
 */
public class JwtTokenEnhancer implements TokenEnhancer {

  /**
   * code模式.
   */
  private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
  /**
   * 密码模式.
   */
  private static final String GRANT_TYPE_PASSWORD = "password";

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    String grantType = authentication.getOAuth2Request().getGrantType();
    //只有如下两种模式才能获取到当前用户信息.
    if (GRANT_TYPE_AUTHORIZATION_CODE.equals(grantType) || GRANT_TYPE_PASSWORD.equals(grantType)) {
      String username = authentication.getUserAuthentication().getName();
      final Map<String, Object> additionalInfo = new HashMap<>();
      additionalInfo.put("username", username);
      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    }
    return accessToken;
  }
}
