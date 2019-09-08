package com.binwoo.oauth.integrate;

import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 用户名密码验证方式.
 *
 * @author hleluo
 * @date 2019/9/1 18:40
 */
@Component
@Primary
public class AuthTokenPasswordIntegrator implements AuthTokenIntegrator {

  private final UserRepository userRepository;

  @Autowired
  public AuthTokenPasswordIntegrator(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User authenticate(AuthTokenParam param) {
    return userRepository.findByUsername(param.getUsername());
  }

  @Override
  public void prepare(AuthTokenParam param) {

  }

  @Override
  public boolean support(AuthTokenParam param) {
    return StringUtils.isEmpty(param.getAuthType()) || AuthTokenParam.AUTH_TYPE_PASSWORD
        .equals(param.getAuthType());
  }

  @Override
  public void complete(AuthTokenParam param) {

  }
}
