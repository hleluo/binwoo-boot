package com.binwoo.oauth.security;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author hleluo
 * @date 2019/8/29 23:27
 */
@Slf4j
@Service
public class IntegratorUserDetailsServiceImpl implements UserDetailsService {

  private List<AuthTokenIntegrator> integrators;

  @Autowired(required = false)
  public void setIntegrators(List<AuthTokenIntegrator> integrators) {
    this.integrators = integrators;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    AuthTokenParam param = AuthTokenParamContext.get();
    //判断是否是集成登录
    if (param == null) {
      param = new AuthTokenParam();
    }
    param.setUsername(s);
    User user = this.authenticate(param);
    if (user == null) {
      throw new UsernameNotFoundException("用户名或密码错误");
    }
    return user;
  }

  private User authenticate(AuthTokenParam param) {
    if (this.integrators != null) {
      for (AuthTokenIntegrator integrator : integrators) {
        if (integrator.support(param)) {
          return integrator.authenticate(param);
        }
      }
    }
    return null;
  }
}
