package com.binwoo.oauth.service.impl;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.repository.UserRepository;
import com.binwoo.oauth.req.UserQueryReq;
import com.binwoo.oauth.service.UserService;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

  @Autowired
  public UserServiceImpl(UserRepository userRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User save(User user) throws HttpException {
    User source = userRepository.findByUsername(user.getUsername());
    if (source != null && !source.getId().equals(user.getId())) {
      throw new HttpException(HttpAuthExceptionCode.USERNAME_EXIST);
    }
    if (StringUtils.isEmpty(user.getId())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    return userRepository.save(user);
  }

  @Override
  public PageList<User> query(UserQueryReq req) {
    Page<User> page = userRepository.findAll(new Specification<User>() {
      @Override
      public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return new PageList<>(page.getNumber(), page.getSize(), page.getTotalPages(),
        page.getTotalElements(), page.getContent());
  }
}
