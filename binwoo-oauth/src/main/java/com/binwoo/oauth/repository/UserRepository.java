package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户仓库.
 *
 * @author admin
 * @date 2019/9/5 16:56
 */
@Repository
public interface UserRepository extends BaseRepository<User> {

  /**
   * 根据用户名查询用户信息.
   *
   * @param username 用户名
   * @return 用户信息.
   */
  User findByUsername(String username);

}
