package com.binwoo.oauth.helper;

import com.binwoo.oauth.entity.Menu;
import com.binwoo.oauth.repository.MenuRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 字典帮助类.
 *
 * @author admin
 * @date 2019/11/6 16:40
 */
@Component
public class MenuHelper {

  private final MenuRepository menuRepository;

  public MenuHelper(MenuRepository menuRepository) {
    this.menuRepository = menuRepository;
  }

  /**
   * 获取所有后代节点，包括自己.
   *
   * @param menu 菜单
   * @return 菜单列表
   */
  public List<Menu> getPosterityWithSelf(Menu menu) {
    List<Menu> results = getPosterityWithoutSelf(menu);
    results.add(menu);
    return results;
  }

  /**
   * 获取所有后代节点，包括自己.
   *
   * @param menus 菜单列表
   * @return 菜单列表
   */
  public List<Menu> getPosterityWithSelf(List<Menu> menus) {
    List<Menu> results = new ArrayList<>();
    if (CollectionUtils.isEmpty(menus)) {
      return results;
    }
    results.addAll(menus);
    List<String> ids = menus.stream().map(Menu::getId).collect(Collectors.toList());
    List<Menu> children = menuRepository.findByParentIdIn(ids);
    if (!CollectionUtils.isEmpty(children)) {
      results.addAll(getPosterityWithSelf(children));
    }
    return results;
  }

  /**
   * 获取所有后代节点，包括自己.
   *
   * @param id 字典类型id
   * @return 字典类型列表
   */
  public List<Menu> getPosterityWithSelfById(String id) {
    if (StringUtils.isEmpty(id)) {
      return new ArrayList<>();
    }
    Menu menu = menuRepository.findById(id).orElse(null);
    return getPosterityWithSelf(menu);
  }

  /**
   * 获取所有后代节点，包括自己.
   *
   * @param ids 菜单id列表
   * @return 菜单列表
   */
  public List<Menu> getPosterityWithSelfByIds(List<String> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return new ArrayList<>();
    }
    List<Menu> menus = menuRepository.findByIdIn(ids);
    return getPosterityWithSelf(menus);
  }

  /**
   * 获取所有后代节点，不包括自己.
   *
   * @param menu 菜单
   * @return 菜单列表
   */
  public List<Menu> getPosterityWithoutSelf(Menu menu) {
    List<Menu> results = new ArrayList<>();
    if (menu != null) {
      List<Menu> children = menuRepository.findByParentId(menu.getId());
      results.addAll(getPosterityWithSelf(children));
    }
    return results;
  }

}
