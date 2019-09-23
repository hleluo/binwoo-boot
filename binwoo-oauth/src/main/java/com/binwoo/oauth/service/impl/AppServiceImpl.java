package com.binwoo.oauth.service.impl;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.App;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.repository.AppRepository;
import com.binwoo.oauth.repository.AuthorityRepository;
import com.binwoo.oauth.repository.MenuRepository;
import com.binwoo.oauth.repository.RoleRepository;
import com.binwoo.oauth.req.AppPagerReq;
import com.binwoo.oauth.service.AppService;
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
 * 应用服务实现.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
@Service
public class AppServiceImpl implements AppService {

  private final AppRepository appRepository;
  private final RoleRepository roleRepository;
  private final AuthorityRepository authorityRepository;
  private final MenuRepository menuRepository;

  /**
   * 构造函数.
   *
   * @param appRepository 应用仓库
   * @param roleRepository 角色仓库
   * @param authorityRepository 权职仓库
   * @param menuRepository 菜单仓库
   */
  @Autowired
  public AppServiceImpl(AppRepository appRepository, RoleRepository roleRepository,
      AuthorityRepository authorityRepository, MenuRepository menuRepository) {
    this.appRepository = appRepository;
    this.roleRepository = roleRepository;
    this.authorityRepository = authorityRepository;
    this.menuRepository = menuRepository;
  }

  @Override
  public App save(App app) throws HttpException {
    App source = appRepository.findByCodeAndType(app.getCode(), app.getType());
    if (source != null && !source.getId().equals(app.getId())) {
      throw new HttpException(HttpAuthExceptionCode.APP_EXIST);
    }
    return appRepository.save(app);
  }

  @Override
  public PageList<App> getByPager(AppPagerReq req) {
    Page<App> page = appRepository.findAll(new Specification<App>() {
      @Override
      public Predicate toPredicate(Root<App> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return PageListUtils.convert(page);
  }

  @Override
  public boolean delete(String id) {
    if (!StringUtils.isEmpty(id)) {
      menuRepository.deleteByAppId(id);
      roleRepository.deleteMenuByAppId(id);
      roleRepository.deleteUserByAppId(id);
      roleRepository.deleteByAppId(id);
      authorityRepository.deleteUserByAppId(id);
      authorityRepository.deleteClientByAppId(id);
      authorityRepository.deleteByAppId(id);
      appRepository.deleteById(id);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      menuRepository.deleteByAppIdIn(ids);
      roleRepository.deleteMenuByAppIdIn(ids);
      roleRepository.deleteUserByAppIdIn(ids);
      roleRepository.deleteByAppIdIn(ids);
      authorityRepository.deleteUserByAppIdIn(ids);
      authorityRepository.deleteClientByAppIdIn(ids);
      authorityRepository.deleteByAppIdIn(ids);
      appRepository.deleteByIdIn(ids);
    }
    return true;
  }
}
