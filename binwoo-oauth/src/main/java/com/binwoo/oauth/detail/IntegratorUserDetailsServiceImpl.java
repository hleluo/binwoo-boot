package com.binwoo.oauth.detail;

import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.integrate.AuthTokenIntegrator;
import com.binwoo.oauth.integrate.AuthTokenParam;
import com.binwoo.oauth.integrate.AuthTokenParamContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 多登录方式集成用户信息服务.
 *
 * @author hleluo
 * @date 2019/8/29 23:27
 */
@Service
public class IntegratorUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserDetailsServiceAdapter adapter;

  /**
   * 集成器列表.
   */
  private List<AuthTokenIntegrator> integrators;

  @Autowired(required = false)
  public void setIntegrators(List<AuthTokenIntegrator> integrators) {
    this.integrators = integrators;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    AuthTokenParam param = AuthTokenParamContext.get();
    if (param == null) {
      param = new AuthTokenParam();
    }
    param.setUsername(s);
    User user = this.authenticate(param);
    return adapter.format(user, param);
  }

  /**
   * 调用指定登录验证.
   *
   * @param param 登录参数
   * @return 用户
   */
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
