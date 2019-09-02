package com.binwoo.oauth.security;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * 自定义客户端信息.
 *
 * @author hleluo
 * @date 2019/8/29 23:27
 */
@Service
public class ClientDetailsServiceImpl extends InMemoryClientDetailsService {

  @Override
  public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
    if(!"aaa".equals(s)) {
      return null;
    }
    BaseClientDetails details = new BaseClientDetails();
    details.setClientId(s);
    details.setClientSecret(new BCryptPasswordEncoder().encode("aaa"));
    details.setScope(Arrays.asList("write", "read"));
    details.setAuthorizedGrantTypes(
        Arrays.asList("authorization_code", "password", "client_credentials", "refresh_token"));
    details.setResourceIds(Collections.singleton("123"));
    details.setAccessTokenValiditySeconds(7200);
    details.setRefreshTokenValiditySeconds(8000);
    return details;
  }
}
