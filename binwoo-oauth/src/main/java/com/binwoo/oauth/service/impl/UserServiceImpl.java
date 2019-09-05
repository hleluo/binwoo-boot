package com.binwoo.oauth.service.impl;

import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.repository.UserRepository;
import com.binwoo.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现.
 *
 * @author admin
 * @date 2019/9/4 17:01
 */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User save() {
    return null;
  }
}
