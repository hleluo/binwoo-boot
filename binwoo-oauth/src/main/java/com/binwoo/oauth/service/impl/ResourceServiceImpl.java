package com.binwoo.oauth.service.impl;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.Resource;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.repository.ApiRepository;
import com.binwoo.oauth.repository.GroupRepository;
import com.binwoo.oauth.repository.ResourceRepository;
import com.binwoo.oauth.req.ResourcePagerReq;
import com.binwoo.oauth.service.ResourceService;
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
 * 资源服务实现.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
@Service
public class ResourceServiceImpl implements ResourceService {

  private final ResourceRepository resourceRepository;
  private final ApiRepository apiRepository;
  private final GroupRepository groupRepository;

  /**
   * 构造函数.
   *
   * @param resourceRepository 资源仓库
   * @param apiRepository 接口仓库
   * @param groupRepository 组仓库
   */
  @Autowired
  public ResourceServiceImpl(ResourceRepository resourceRepository,
      ApiRepository apiRepository, GroupRepository groupRepository) {
    this.resourceRepository = resourceRepository;
    this.apiRepository = apiRepository;
    this.groupRepository = groupRepository;
  }

  @Override
  public Resource save(Resource resource) throws HttpException {
    Resource source = resourceRepository.findByCode(resource.getCode());
    if (source != null && !source.getId().equals(resource.getId())) {
      throw new HttpException(HttpAuthExceptionCode.RESOURCE_EXIST);
    }
    return resourceRepository.save(resource);
  }

  @Override
  public PageList<Resource> getByPager(ResourcePagerReq req) {
    Page<Resource> page = resourceRepository.findAll(new Specification<Resource>() {
      @Override
      public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return PageListUtils.convert(page);
  }

  @Override
  public boolean delete(String id) {
    if (!StringUtils.isEmpty(id)) {
      resourceRepository.deleteClientById(id);
      apiRepository.deleteByResourceId(id);
      groupRepository.deleteApiByResourceId(id);
      groupRepository.deleteUserByResourceId(id);
      groupRepository.deleteClientByResourceId(id);
      groupRepository.deleteByResourceId(id);
      resourceRepository.deleteById(id);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      resourceRepository.deleteClientByIdIn(ids);
      apiRepository.deleteByResourceIdIn(ids);
      groupRepository.deleteApiByResourceIdIn(ids);
      groupRepository.deleteUserByResourceIdIn(ids);
      groupRepository.deleteClientByResourceIdIn(ids);
      groupRepository.deleteByResourceIdIn(ids);
      resourceRepository.deleteByIdIn(ids);
    }
    return true;
  }
}
