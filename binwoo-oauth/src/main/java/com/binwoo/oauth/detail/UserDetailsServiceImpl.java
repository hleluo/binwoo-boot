package com.binwoo.oauth.detail;

import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.integrate.AuthTokenParamContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 用户详细信息服务.
 *
 * @author hleluo
 * @date 2019/8/29 23:27
 */
//@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserDetailsServiceAdapter adapter;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = new User();
    user.setUsername(s);
    return adapter.format(user, AuthTokenParamContext.get());
  }
}
