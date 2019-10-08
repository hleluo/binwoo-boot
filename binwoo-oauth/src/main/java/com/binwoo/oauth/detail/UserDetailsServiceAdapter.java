package com.binwoo.oauth.detail;

import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.exception.AuthException;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.integrate.AuthTokenParam;
import com.binwoo.oauth.repository.AuthorityRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 用户详情适配器.
 *
 * @author admin
 * @date 2019/9/5 18:22
 */
@Component
public class UserDetailsServiceAdapter {

  private final AuthorityRepository authorityRepository;

  @Autowired
  public UserDetailsServiceAdapter(AuthorityRepository authorityRepository) {
    this.authorityRepository = authorityRepository;
  }

  /**
   * User转换.
   *
   * @param user 用户信息
   * @return 权限用户信息
   */
  public org.springframework.security.core.userdetails.User format(User user,
      AuthTokenParam param) {
    if (user == null) {
      throw new UsernameNotFoundException(HttpAuthExceptionCode.USER_NOT_EXIST.name());
    }
    if (user.isDisable()) {
      //用户被禁用
      throw new AuthException(HttpAuthExceptionCode.USER_DISABLED.name());
    }
    if (user.isDeleted()) {
      //用户被删除
      throw new AuthException(HttpAuthExceptionCode.USER_DELETED.name());
    }
    if (user.getExpireTime() != null && user.getExpireTime().getTime() > System
        .currentTimeMillis()) {
      //用户已过期
      throw new AuthException(HttpAuthExceptionCode.USER_EXPIRED.name());
    }
    if (!user.isActive()) {
      //用户未激活
      throw new AuthException(HttpAuthExceptionCode.USER_UNACTIVATED.name());
    }
    List<String> roles = getAuthorities(user.getUsername(), param);
    List<GrantedAuthority> authorities = new ArrayList<>();
    if (!CollectionUtils.isEmpty(roles)) {
      for (String role : roles) {
        authorities.add(new SimpleGrantedAuthority(role));
      }
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), authorities);
  }

  /**
   * 获取用户权职列表.
   *
   * @param username 用户名
   * @param param 参数
   * @return 权职列表
   */
  private List<String> getAuthorities(String username, AuthTokenParam param) {
    if (param == null) {
      return authorityRepository.selectUserRoleByApp(username);
    }
    if (StringUtils.isEmpty(param.getAppCode()) && StringUtils.isEmpty(param.getAppType())) {
      return authorityRepository.selectUserRoleByApp(username);
    } else {
      if (StringUtils.isEmpty(param.getAppCode())) {
        return authorityRepository.selectUserRoleByAppType(username, param.getAppType());
      } else if (StringUtils.isEmpty(param.getAppType())) {
        return authorityRepository.selectUserRoleByAppCode(username, param.getAppCode());
      } else {
        return authorityRepository
            .selectUserRoleByApp(username, param.getAppCode(), param.getAppType());
      }
    }
  }

}
