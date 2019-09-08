package com.binwoo.oauth.token;

import com.binwoo.oauth.integrate.AuthTokenParam;
import com.binwoo.oauth.integrate.AuthTokenParamContext;
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
public class AuthTokenEnhancer implements TokenEnhancer {

  /**
   * code模式.
   */
  private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
  /**
   * 密码模式.
   */
  private static final String GRANT_TYPE_PASSWORD = "password";
  /**
   * 设备类型参数.
   */
  private static final String PARAM_KEY_DEVICE_TYPE = "device_type";

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
      OAuth2Authentication authentication) {
    AuthTokenParam param = AuthTokenParamContext.get();
    String deviceType = param.getParameterValue(PARAM_KEY_DEVICE_TYPE);
    /*if (!StringUtils.isEmpty(deviceType)) {

    }*/
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
