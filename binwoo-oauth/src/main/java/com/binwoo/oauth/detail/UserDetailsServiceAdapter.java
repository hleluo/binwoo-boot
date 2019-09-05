package com.binwoo.oauth.detail;

import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.exception.AuthException;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import java.util.Collections;
import java.util.Date;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 用户详情适配器.
 *
 * @author admin
 * @date 2019/9/5 18:22
 */
public class UserDetailsServiceAdapter {

  /**
   * User转换.
   *
   * @param user 用户信息
   * @return 权限用户信息
   */
  public org.springframework.security.core.userdetails.User format(User user) {
    if (user == null) {
      throw new UsernameNotFoundException("username not found");
    }
    if (user.isDisable()) {
      //用户被禁用
      throw new AuthException(HttpAuthExceptionCode.USER_DISABLED.name());
    }
    if (user.isDeleted()) {
      //用户被删除
      throw new AuthException(HttpAuthExceptionCode.USER_DELETED.name());
    }
    if (user.getExpireTime() != null && user.getExpireTime().getTime() > new Date().getTime()) {
      //用户已过期
      throw new AuthException(HttpAuthExceptionCode.USER_DELETED.name());
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), Collections.emptyList());
  }

}
