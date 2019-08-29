package com.binwoo.oauth.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author hleluo
 * @date 2019/8/29 23:27
 */
@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = new User("a", ",", null);
    return user;
  }
}
