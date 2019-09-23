package com.binwoo.oauth.service.impl;

import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Menu;
import com.binwoo.oauth.repository.MenuRepository;
import com.binwoo.oauth.req.MenuPagerReq;
import com.binwoo.oauth.service.MenuService;
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
 * 菜单服务实现.
 *
 * @author admin
 * @date 2019/9/18 17:12
 */
@Service
public class MenuServiceImpl implements MenuService {

  private final MenuRepository menuRepository;

  /**
   * 构造函数.
   *
   * @param menuRepository 菜单仓库
   */
  @Autowired
  public MenuServiceImpl(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  @Override
  public Menu save(Menu menu) {
    return menuRepository.save(menu);
  }

  @Override
  public PageList<Menu> getByPager(MenuPagerReq req) {
    Page<Menu> page = menuRepository.findAll(new Specification<Menu>() {
      @Override
      public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return PageListUtils.convert(page);
  }

  @Override
  public boolean delete(String id) {
    if (!StringUtils.isEmpty(id)) {
      menuRepository.deleteRoleById(id);
      menuRepository.deleteById(id);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      menuRepository.deleteRoleByIdIn(ids);
      menuRepository.deleteByIdIn(ids);
    }
    return true;
  }
}
