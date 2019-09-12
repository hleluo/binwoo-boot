package com.binwoo.oauth.client;

import com.binwoo.oauth.entity.Client;
import com.binwoo.oauth.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 客户端信息加载器.
 *
 * @author hleluo
 * @date 2019/9/12 21:04
 */
@Component
public class ClientLoader {

  private final ClientRepository clientRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public ClientLoader(ClientRepository clientRepository,
      PasswordEncoder passwordEncoder) {
    this.clientRepository = clientRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * 加载客户端信息.
   *
   * @param clientId 客户端ID
   * @return 客户端信息
   */
  public Client load(String clientId) {
    return clientRepository.findByCode(clientId);
  }

  /**
   * 验证客户端密码.
   *
   * @param client 客户端信息
   * @param secret 密码
   * @return 是否匹配
   */
  public boolean isMatched(Client client, String secret) {
    if (client == null) {
      return false;
    }
    return passwordEncoder.matches(secret, client.getSecret());
  }

}
