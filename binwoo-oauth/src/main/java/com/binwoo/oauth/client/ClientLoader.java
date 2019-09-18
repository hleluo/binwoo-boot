package com.binwoo.oauth.client;

import com.binwoo.oauth.entity.Client;
import com.binwoo.oauth.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  public ClientLoader(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
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

}
