package com.binwoo.oauth.detail;

import com.binwoo.oauth.client.ClientParam;
import com.binwoo.oauth.client.ClientParamContext;
import com.binwoo.oauth.entity.Client;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.integrate.AuthTokenParam;
import com.binwoo.oauth.integrate.AuthTokenParamContext;
import com.binwoo.oauth.repository.AuthorityRepository;
import com.binwoo.oauth.repository.ClientRepository;
import com.binwoo.oauth.repository.ResourceRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 自定义客户端信息.
 *
 * @author hleluo
 * @date 2019/8/29 23:27
 */
@Service
public class ClientDetailsServiceImpl extends InMemoryClientDetailsService {

  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private AuthorityRepository authorityRepository;
  @Autowired
  private ResourceRepository resourceRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
    ClientParam param = ClientParamContext.get();
    if (param == null) {
      param = new ClientParam();
      ClientParamContext.set(param);
    }
    param.setClientId(s);
    Client client = param.getClient();
    if (client == null || !client.getCode().equals(s)) {
      client = clientRepository.findByCode(s);
      param.setClient(client);
      param.setResourceIds(null);
      param.setAuthorities(null);
    }
    if (client == null) {
      throw new ClientRegistrationException(HttpAuthExceptionCode.CLIENT_NOT_EXIST.name());
    }
    if (client.isDisable()) {
      //客户端被禁用
      throw new ClientRegistrationException(HttpAuthExceptionCode.CLIENT_DISABLED.name());
    }
    if (client.isDeleted()) {
      //客户端被删除
      throw new ClientRegistrationException(HttpAuthExceptionCode.CLIENT_DELETED.name());
    }
    if (client.getExpireTime() != null && client.getExpireTime().getTime() > System
        .currentTimeMillis()) {
      //客户端已过期
      throw new ClientRegistrationException(HttpAuthExceptionCode.CLIENT_EXPIRED.name());
    }
    BaseClientDetails details = new BaseClientDetails();
    details.setClientId(client.getCode());
    details.setClientSecret(passwordEncoder.encode(client.getSecret()));
    if (!StringUtils.isEmpty(client.getScope())) {
      details.setScope(Arrays.asList(client.getScope().trim().split(",")));
    }
    if (!StringUtils.isEmpty(client.getGrantType())) {
      details.setAuthorizedGrantTypes(Arrays.asList(client.getGrantType().trim().split(",")));
    }
    if (param.getResourceIds() == null) {
      param.setResourceIds(getResourceIds(client.getCode()));
    }
    details.setResourceIds(param.getResourceIds());
    details.setAccessTokenValiditySeconds(client.getAccessTokenExpire());
    details.setRefreshTokenValiditySeconds(client.getRefreshTokenExpire());
    if (param.getAuthorities() == null) {
      param.setAuthorities(getAuthorities(client.getCode(), AuthTokenParamContext.get()));
    }
    List<String> roles = param.getAuthorities();
    List<GrantedAuthority> authorities = new ArrayList<>();
    if (!CollectionUtils.isEmpty(roles)) {
      for (String role : roles) {
        authorities.add(new SimpleGrantedAuthority(role));
      }
    }
    details.setAuthorities(authorities);
    return details;
  }

  /**
   * 获取客户端资源列表.
   *
   * @param clientId 客户端标识
   * @return 资源id列表
   */
  private List<String> getResourceIds(String clientId) {
    return resourceRepository.selectClientResourceIds(clientId);
  }

  /**
   * 获取客户端权职列表.
   *
   * @param clientId 客户端id
   * @param param 参数
   * @return 权职列表
   */
  private List<String> getAuthorities(String clientId, AuthTokenParam param) {
    if (param == null) {
      return authorityRepository.selectClientRoleByApp(clientId);
    }
    if (StringUtils.isEmpty(param.getAppCode()) && StringUtils.isEmpty(param.getAppType())) {
      return authorityRepository.selectClientRoleByApp(clientId);
    } else {
      if (StringUtils.isEmpty(param.getAppCode())) {
        return authorityRepository.selectClientRoleByAppType(clientId, param.getAppType());
      } else if (StringUtils.isEmpty(param.getAppType())) {
        return authorityRepository.selectClientRoleByAppCode(clientId, param.getAppCode());
      } else {
        return authorityRepository
            .selectClientRoleByApp(clientId, param.getAppCode(), param.getAppType());
      }
    }
  }
}
