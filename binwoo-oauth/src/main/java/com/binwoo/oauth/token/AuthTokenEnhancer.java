package com.binwoo.oauth.token;

import com.binwoo.oauth.integrate.AuthTokenParam;
import com.binwoo.oauth.integrate.AuthTokenParamContext;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.util.CollectionUtils;

/**
 * Jwt Token额外信息扩展.
 *
 * @author hleluo
 * @date 2019/8/31 16:18
 */
public class AuthTokenEnhancer implements TokenEnhancer {

  /**
   * code模式.
   */
  private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
  /**
   * client认证模式.
   */
  private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
  /**
   * 刷新Token.
   */
  private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
  /**
   * 密码模式.
   */
  private static final String GRANT_TYPE_PASSWORD = "password";

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    AuthTokenParam param = AuthTokenParamContext.get();
    String grantType = authentication.getOAuth2Request().getGrantType();
    grantType = grantType == null ? param.getGrantType() : grantType;
    Map<String, Object> additionalInfo = null;
    if (GRANT_TYPE_AUTHORIZATION_CODE.equals(grantType) || GRANT_TYPE_PASSWORD.equals(grantType)) {
      //只有如下两种模式才能获取到当前用户信息.
      String username = authentication.getUserAuthentication().getName();
      additionalInfo = buildForUser(username, param);
    } else if (GRANT_TYPE_CLIENT_CREDENTIALS.equals(grantType)) {
      // 获取client_id.
      String clientId = authentication.getOAuth2Request().getClientId();
      additionalInfo = buildForClient(clientId, param);
    } else if (GRANT_TYPE_REFRESH_TOKEN.equals(grantType)) {
      //刷新token，获取username或client_id.
      String clientId = authentication.getOAuth2Request().getClientId();
      String username = authentication.getUserAuthentication() == null ? null
          : authentication.getUserAuthentication().getName();
      if (username == null) {
        additionalInfo = buildForClient(clientId, param);
      } else {
        additionalInfo = buildForUser(username, param);
      }
    }
    if (!CollectionUtils.isEmpty(additionalInfo)) {
      ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    }
    return accessToken;
  }

  /**
   * 构建用户认证的额外信息.
   *
   * @param username 用户名
   * @param param 参数
   * @return 额外信息
   */
  private Map<String, Object> buildForUser(String username, AuthTokenParam param) {
    Map<String, Object> map = new HashMap<>();
    map.put("username", username);
    return map;
  }

  /**
   * 构建客户端认证的额外信息.
   *
   * @param clientId 客户端id
   * @param param 参数
   * @return 额外信息
   */
  private Map<String, Object> buildForClient(String clientId, AuthTokenParam param) {
    Map<String, Object> map = new HashMap<>();
    map.put("clientId", clientId);
    return map;
  }

}
