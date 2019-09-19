package com.binwoo.oauth.service.impl;

import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.Api;
import com.binwoo.oauth.repository.ApiRepository;
import com.binwoo.oauth.req.ApiPagerReq;
import com.binwoo.oauth.service.ApiService;
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
 * 接口服务实现.
 *
 * @author admin
 * @date 2019/9/18 17:12
 */
@Service
public class ApiServiceImpl implements ApiService {

  private final ApiRepository apiRepository;

  /**
   * 构造函数.
   *
   * @param apiRepository 接口仓库
   */
  @Autowired
  public ApiServiceImpl(ApiRepository apiRepository) {
    this.apiRepository = apiRepository;
  }

  @Override
  public Api save(Api api) {
    return apiRepository.save(api);
  }

  @Override
  public PageList<Api> getByPager(ApiPagerReq req) {
    Page<Api> page = apiRepository.findAll(new Specification<Api>() {
      @Override
      public Predicate toPredicate(Root<Api> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return PageListUtils.convert(page);
  }

  @Override
  public boolean delete(String id) {
    if (!StringUtils.isEmpty(id)) {
      apiRepository.deleteGroupById(id);
      apiRepository.deleteById(id);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      apiRepository.deleteGroupByIdIn(ids);
      apiRepository.deleteByIdIn(ids);
    }
    return true;
  }
}
