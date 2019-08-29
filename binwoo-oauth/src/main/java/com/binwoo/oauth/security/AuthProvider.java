package com.binwoo.oauth.security;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Component;

/**
 * @author hleluo
 * @date 2019/8/29 23:13
 */
@Slf4j
@Component
public class AuthProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;

  @Autowired
  public AuthProvider(CustomUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = (String) authentication.getCredentials();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (null == userDetails) {
      throw new UsernameNotFoundException("");
    }
    if (!userDetails.isEnabled()) {
      throw new DisabledException("");
    }
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if (!encoder.matches(password, userDetails.getPassword())) {
      throw new InvalidGrantException("password error");
    }
    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
    return new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return UsernamePasswordAuthenticationToken.class.equals(aClass);
  }
}
