package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Client;
import org.springframework.stereotype.Repository;

/**
 * 用户仓库.
 *
 * @author admin
 * @date 2019/9/5 16:56
 */
@Repository
public interface ClientRepository extends BaseRepository<Client> {

  /**
   * 根据客户端id查询客户端信息.
   *
   * @param code 客户端标识
   * @return 客户端信息.
   */
  Client findByCode(String code);

}
