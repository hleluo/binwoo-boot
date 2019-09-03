package com.binwoo.oauth.integrate;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
    String password = new BCryptPasswordEncoder().encode("111111");
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("A"));
    return new User("root", password, authorities);
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
