package com.binwoo.oauth.service.impl;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Authority;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.repository.AuthorityRepository;
import com.binwoo.oauth.req.AuthorityPagerReq;
import com.binwoo.oauth.service.AuthorityService;
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
 * 权职服务实现.
 *
 * @author admin
 * @date 2019/9/18 17:12
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

  private final AuthorityRepository authorityRepository;

  /**
   * 构造函数.
   *
   * @param authorityRepository 权职仓库
   */
  @Autowired
  public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
    this.authorityRepository = authorityRepository;
  }

  @Override
  public Authority save(Authority authority) throws HttpException {
    Authority source = null;
    if (StringUtils.isEmpty(authority.getAppId())) {
      source = authorityRepository.findByAppIdIsNullAndCode(authority.getCode());
    } else {
      source = authorityRepository.findByAppIdAndCode(authority.getAppId(), authority.getCode());
    }
    if (source != null && !source.getId().equals(authority.getId())) {
      throw new HttpException(HttpAuthExceptionCode.AUTHORITY_EXIST);
    }
    return authorityRepository.save(authority);
  }

  @Override
  public PageList<Authority> getByPager(AuthorityPagerReq req) {
    Page<Authority> page = authorityRepository.findAll(new Specification<Authority>() {
      @Override
      public Predicate toPredicate(Root<Authority> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return PageListUtils.convert(page);
  }

  @Override
  public boolean delete(String id) {
    if (!StringUtils.isEmpty(id)) {
      authorityRepository.deleteUserById(id);
      authorityRepository.deleteClientById(id);
      authorityRepository.deleteById(id);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      authorityRepository.deleteUserByIdIn(ids);
      authorityRepository.deleteClientByIdIn(ids);
      authorityRepository.deleteByIdIn(ids);
    }
    return true;
  }
}
