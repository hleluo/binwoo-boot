package com.binwoo.oauth.service.impl;

import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.Role;
import com.binwoo.oauth.repository.RoleRepository;
import com.binwoo.oauth.req.RolePagerReq;
import com.binwoo.oauth.service.RoleService;
import com.binwoo.oauth.util.PageListUtils;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 角色服务实现.
 *
 * @author admin
 * @date 2019/9/18 17:12
 */
@Service
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  /**
   * 构造函数.
   *
   * @param roleRepository 角色仓库
   */
  @Autowired
  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role save(Role role) {
    return roleRepository.save(role);
  }

  @Override
  public PageList<Role> getByPager(RolePagerReq req) {
    Page<Role> page = roleRepository.findAll(new Specification<Role>() {
      @Override
      public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return PageListUtils.convert(page);
  }

  @Override
  public boolean delete(String id) {
    if (!StringUtils.isEmpty(id)) {
      roleRepository.deleteUserById(id);
      roleRepository.deleteMenuById(id);
      roleRepository.deleteById(id);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      roleRepository.deleteUserByIdIn(ids);
      roleRepository.deleteMenuByIdIn(ids);
      roleRepository.deleteByIdIn(ids);
    }
    return true;
  }
}
