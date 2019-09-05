package com.binwoo.oauth.detail;

import com.binwoo.oauth.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户详细信息服务.
 *
 * @author hleluo
 * @date 2019/8/29 23:27
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = new User();
    user.setUsername(s);
    return new UserDetailsServiceAdapter().format(user);
  }
}
