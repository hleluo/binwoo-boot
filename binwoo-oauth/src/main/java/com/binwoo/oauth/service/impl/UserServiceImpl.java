package com.binwoo.oauth.service.impl;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.repository.UserRepository;
import com.binwoo.oauth.req.UserPagerReq;
import com.binwoo.oauth.service.UserService;
import com.binwoo.oauth.util.PageListUtils;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
  private final EntityManager manager;

  /**
   * 构造函数.
   *
   * @param userRepository 用户仓库
   * @param passwordEncoder 加密方式
   * @param manager 管理器
   */
  @Autowired
  public UserServiceImpl(UserRepository userRepository,
      PasswordEncoder passwordEncoder, EntityManager manager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.manager = manager;
  }

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
        if (source == null) {
          throw new HttpException(HttpAuthExceptionCode.USER_PASSWORD_NULL);
        }
        user.setPassword(source.getPassword());
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

  @Override
  public boolean delete(String id) {
    if (!StringUtils.isEmpty(id)) {
      userRepository.deleteRoleById(id);
      userRepository.deleteGroupById(id);
      userRepository.deleteAuthorityById(id);
      userRepository.updateDeletedById(id);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      userRepository.deleteRoleByIdIn(ids);
      userRepository.deleteGroupByIdIn(ids);
      userRepository.deleteAuthorityByIdIn(ids);
      userRepository.updateDeletedByIdIn(ids);
    }
    return true;
  }

  @Override
  public User getByIdOrUsername(String key) {
    return userRepository.findFirstByIdOrUsername(key, key);
  }

  @Override
  public boolean updateRoles(String id, Set<String> roleIds) {
    userRepository.updateRoles(manager, id, roleIds);
    return true;
  }

  @Override
  public boolean updateAuthorities(String id, Set<String> authorityIds) {
    userRepository.updateAuthorities(manager, id, authorityIds);
    return true;
  }

  @Override
  public boolean updateGroups(String id, Set<String> groupIds) {
    userRepository.updateGroups(manager, id, groupIds);
    return true;
  }
}
