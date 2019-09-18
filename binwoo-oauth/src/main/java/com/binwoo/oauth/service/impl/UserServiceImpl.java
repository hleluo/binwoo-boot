package com.binwoo.oauth.service.impl;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.exception.SqlException;
import com.binwoo.oauth.repository.SqlRepository;
import com.binwoo.oauth.repository.UserRepository;
import com.binwoo.oauth.req.UserPagerReq;
import com.binwoo.oauth.service.UserService;
import com.binwoo.oauth.util.PageListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 用户服务实现.
 *
 * @author admin
 * @date 2019/9/4 17:01
 */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final SqlRepository sqlRepository;

  /**
   * 构造函数.
   *
   * @param userRepository 用户仓库
   * @param passwordEncoder 加密方式
   * @param sqlRepository SQL仓库
   */
  @Autowired
  public UserServiceImpl(UserRepository userRepository,
      PasswordEncoder passwordEncoder, SqlRepository sqlRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.sqlRepository = sqlRepository;
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public User save(User user) throws HttpException {
    User source = userRepository.findByUsername(user.getUsername());
    if (source != null && !source.getId().equals(user.getId())) {
      throw new HttpException(HttpAuthExceptionCode.USERNAME_EXIST);
    }
    if (StringUtils.isEmpty(user.getId())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    } else {
      //修改用户信息，检测密码是有有修改.
      if (StringUtils.isEmpty(user.getPassword())) {
        user.setPassword(source == null ? null : source.getPassword());
      } else {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
      }
    }
    return userRepository.save(user);
  }

  @Override
  public PageList<User> getByPager(UserPagerReq req) {
    Page<User> page = userRepository.findAll(new Specification<User>() {
      @Override
      public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return PageListUtils.convert(page);
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public boolean delete(String id) {
    if (id != null) {
      userRepository.updateDeletedById(id);
      userRepository.deleteRoleById(id);
      userRepository.deleteGroupById(id);
      userRepository.deleteAuthorityById(id);
    }
    return true;
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      userRepository.updateDeletedByIdIn(ids);
      userRepository.deleteRoleByIdIn(ids);
      userRepository.deleteGroupByIdIn(ids);
      userRepository.deleteAuthorityByIdIn(ids);
    }
    return true;
  }

  @Override
  public User getByIdOrUsername(String key) {
    return userRepository.findFirstByIdOrUsername(key, key);
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public boolean updateRoles(String id, Set<String> roleIds) {
    userRepository.deleteRoleById(id);
    if (!CollectionUtils.isEmpty(roleIds)) {
      String sql = "INSERT INTO t_user_role (user_id,role_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String roleId : roleIds) {
        parameters.add(sqlRepository.buildParams(id, roleId));
      }
      sqlRepository.batch(sql, parameters);
    }
    return true;
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public boolean updateAuthorities(String id, Set<String> authorityIds) {
    userRepository.deleteAuthorityById(id);
    if (!CollectionUtils.isEmpty(authorityIds)) {
      String sql = "INSERT INTO t_user_authority (user_id,authority_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String authorityId : authorityIds) {
        parameters.add(sqlRepository.buildParams(id, authorityId));
      }
      sqlRepository.batch(sql, parameters);
    }
    return true;
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public boolean updateGroups(String id, Set<String> groupIds) {
    userRepository.deleteGroupById(id);
    if (!CollectionUtils.isEmpty(groupIds)) {
      String sql = "INSERT INTO t_user_group (user_id,group_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String groupId : groupIds) {
        parameters.add(sqlRepository.buildParams(id, groupId));
      }
      sqlRepository.batch(sql, parameters);
    }
    return true;
  }
}
