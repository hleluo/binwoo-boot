package com.binwoo.oauth.detail;

import com.binwoo.oauth.entity.Group.Type;
import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.exception.AuthException;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.integrate.AuthTokenParam;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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

  private final EntityManager entityManager;

  @Autowired
  public UserDetailsServiceAdapter(EntityManager entityManager) {
    this.entityManager = entityManager;
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
      throw new AuthException(HttpAuthExceptionCode.USER_DELETED.name());
    }
    List<String> roles = getRoles(user.getUsername(), param);
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
   * 获取用户角色列表.
   *
   * @param username 用户名
   * @param param 参数
   * @return 角色列表
   */
  private List<String> getRoles(String username, AuthTokenParam param) {
    if (param == null) {
      return null;
    }
    String sql = "SELECT DISTINCT tg.id FROM t_user tu ";
    sql += "RIGHT JOIN t_user_group tug ON tu.id = tug.user_id ";
    sql += "LEFT JOIN t_group tg ON tug.group_id = tg.id ";
    sql += String.format("WHERE tu.username = '%s'", username);
    sql += String.format("AND tg.type = '%s' ", Type.ROLE.name());
    if (StringUtils.isEmpty(param.getDomain())) {
      sql += "AND tg.domain IS NULL ";
    } else {
      sql += String.format("AND tg.domain = '%s' ", param.getDomain());
    }
    if (StringUtils.isEmpty(param.getPlatform())) {
      sql += "AND tg.platform IS NULL ";
    } else {
      sql += String.format("AND tg.platform = '%s' ", param.getPlatform());
    }
    Query query = entityManager.createNativeQuery(sql, String.class);
    return query.getResultList();
  }

}
