package com.binwoo.oauth.detail;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    if (!"root".equals(s)) {
      throw new UsernameNotFoundException("user not exist");
    }
    String password = new BCryptPasswordEncoder().encode("111111");
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("A"));
    User user = new User("root", password, authorities);
    return user;
  }
}
