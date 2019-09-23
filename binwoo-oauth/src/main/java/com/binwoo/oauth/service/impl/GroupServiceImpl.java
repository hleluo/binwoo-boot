package com.binwoo.oauth.service.impl;

import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Group;
import com.binwoo.oauth.repository.GroupRepository;
import com.binwoo.oauth.req.GroupPagerReq;
import com.binwoo.oauth.service.GroupService;
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
 * 组服务实现.
 *
 * @author admin
 * @date 2019/9/18 17:12
 */
@Service
public class GroupServiceImpl implements GroupService {

  private final GroupRepository groupRepository;

  /**
   * 构造函数.
   *
   * @param groupRepository 组仓库
   */
  @Autowired
  public GroupServiceImpl(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  @Override
  public Group save(Group group) {
    return groupRepository.save(group);
  }

  @Override
  public PageList<Group> getByPager(GroupPagerReq req) {
    Page<Group> page = groupRepository.findAll(new Specification<Group>() {
      @Override
      public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return PageListUtils.convert(page);
  }

  @Override
  public boolean delete(String id) {
    if (!StringUtils.isEmpty(id)) {
      groupRepository.deleteUserById(id);
      groupRepository.deleteClientById(id);
      groupRepository.deleteApiById(id);
      groupRepository.deleteById(id);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      groupRepository.deleteUserByIdIn(ids);
      groupRepository.deleteClientByIdIn(ids);
      groupRepository.deleteApiByIdIn(ids);
      groupRepository.deleteByIdIn(ids);
    }
    return true;
  }
}
