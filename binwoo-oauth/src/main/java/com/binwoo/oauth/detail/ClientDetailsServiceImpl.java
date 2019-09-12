package com.binwoo.oauth.detail;

import com.binwoo.oauth.entity.Client;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.integrate.AuthTokenParam;
import com.binwoo.oauth.integrate.AuthTokenParamContext;
import com.binwoo.oauth.repository.ClientRepository;
import com.binwoo.oauth.repository.ResourceRepository;
import com.binwoo.oauth.repository.RoleRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
  private RoleRepository roleRepository;
  @Autowired
  private ResourceRepository resourceRepository;

  @Override
  public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
    Client client = clientRepository.findByCode(s);
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
    details.setClientSecret(client.getSecret());
    if (!StringUtils.isEmpty(client.getScope())) {
      details.setScope(Arrays.asList(client.getScope().trim().split(",")));
    }
    if (!StringUtils.isEmpty(client.getGrantType())) {
      details.setAuthorizedGrantTypes(Arrays.asList(client.getGrantType().trim().split(",")));
    }
    details.setResourceIds(getResourceIds(client.getCode()));
    details.setAccessTokenValiditySeconds(client.getAccessTokenExpire());
    details.setRefreshTokenValiditySeconds(client.getRefreshTokenExpire());

    AuthTokenParam param = AuthTokenParamContext.get();
    List<String> roles = getRoles(client.getCode(), param);
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
   * @param code 客户端标识
   * @return 资源id列表
   */
  private List<String> getResourceIds(String code) {
    return resourceRepository.selectClientResourceIds(code);
  }

  /**
   * 获取客户端角色列表.
   *
   * @param code 客户端id
   * @param param 参数
   * @return 角色列表
   */
  private List<String> getRoles(String code, AuthTokenParam param) {
    if (param == null) {
      return roleRepository.selectClientRole(code);
    }
    if (StringUtils.isEmpty(param.getDomain()) && StringUtils.isEmpty(param.getPlatform())) {
      return roleRepository.selectClientRole(code);
    } else {
      if (StringUtils.isEmpty(param.getDomain())) {
        return roleRepository.selectClientRoleByPlatform(code, param.getPlatform());
      } else if (StringUtils.isEmpty(param.getPlatform())) {
        return roleRepository.selectClientRoleByDomain(code, param.getDomain());
      } else {
        return roleRepository.selectClientRole(code, param.getDomain(), param.getPlatform());
      }
    }
  }
}
