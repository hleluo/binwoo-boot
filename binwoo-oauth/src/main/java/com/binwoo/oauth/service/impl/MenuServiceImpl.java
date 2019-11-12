package com.binwoo.oauth.service.impl;

import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Menu;
import com.binwoo.oauth.helper.MenuHelper;
import com.binwoo.oauth.repository.MenuRepository;
import com.binwoo.oauth.req.MenuPagerReq;
import com.binwoo.oauth.service.MenuService;
import com.binwoo.oauth.util.PageListUtils;
import java.util.List;
import java.util.stream.Collectors;
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
  private final MenuHelper menuHelper;

  /**
   * 构造函数.
   *
   * @param menuRepository 菜单仓库
   * @param menuHelper 菜单帮助类
   */
  @Autowired
  public MenuServiceImpl(MenuRepository menuRepository, MenuHelper menuHelper) {
    this.menuRepository = menuRepository;
    this.menuHelper = menuHelper;
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
      List<Menu> menus = menuHelper.getPosterityWithSelfById(id);
      return deleteMenus(menus);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      List<Menu> menus = menuHelper.getPosterityWithSelfByIds(ids);
      return deleteMenus(menus);
    }
    return true;
  }

  @Override
  public List<Menu> getTree() {
    List<Menu> menus = (List<Menu>) menuRepository.findAll();
    return menuHelper.listToTree(menus);
  }

  /**
   * 删除菜单列表.
   *
   * @param menus 菜单列表
   * @return 是否成功
   */
  private boolean deleteMenus(List<Menu> menus) {
    if (!CollectionUtils.isEmpty(menus)) {
      List<String> ids = menus.stream().map(Menu::getId).collect(Collectors.toList());
      menuRepository.deleteRoleByIdIn(ids);
      menuRepository.deleteByIdIn(ids);
    }
    return true;
  }
}
