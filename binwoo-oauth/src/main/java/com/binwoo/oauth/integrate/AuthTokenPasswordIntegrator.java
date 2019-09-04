package com.binwoo.oauth.integrate;

import com.binwoo.oauth.entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

  @Override
  public User authenticate(AuthTokenParam param) {
    User user = new User();
    user.setUsername(param.getUsername());
    user.setPassword(new BCryptPasswordEncoder().encode("111111"));
    user.setDisable(false);
    return user;
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
