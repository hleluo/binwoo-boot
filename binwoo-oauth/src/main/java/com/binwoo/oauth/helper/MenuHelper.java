package com.binwoo.oauth.helper;

import com.binwoo.oauth.entity.Menu;
import com.binwoo.oauth.repository.MenuRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  /**
   * 列表转树形结构.
   *
   * @param sources 列表
   * @return 树形结构
   */
  public List<Menu> listToTree(List<Menu> sources) {
    if (CollectionUtils.isEmpty(sources)) {
      return new ArrayList<>();
    }
    List<Menu> results = new ArrayList<>();
    Map<String, List<Menu>> mapParentId = new HashMap<>();
    for (Menu menu : sources) {
      if (StringUtils.isEmpty(menu.getParentId())) {
        //根节点
        results.add(menu);
      } else {
        //后代节点
        if (mapParentId.containsKey(menu.getParentId())) {
          mapParentId.get(menu.getParentId()).add(menu);
        } else {
          List<Menu> values = new ArrayList<>();
          values.add(menu);
          mapParentId.put(menu.getParentId(), values);
        }
      }
    }
    results.sort((c1, c2) -> c1.getPriority().compareTo(c2.getPriority()));
    results.forEach(child -> findPosterity(child, mapParentId));
    return results;
  }

  /**
   * 查找某个节点的子孙点.
   *
   * @param node 节点
   * @param mapParentId 父节id与子节点关系
   */
  private void findPosterity(Menu node, Map<String, List<Menu>> mapParentId) {
    if (node == null) {
      return;
    }
    List<Menu> children = mapParentId.get(node.getId());
    if (!CollectionUtils.isEmpty(children)) {
      //排序
      children.sort((c1, c2) -> c1.getPriority().compareTo(c2.getPriority()));
      node.setChildren(children);
      node.getChildren().forEach(child -> findPosterity(child, mapParentId));
    }
  }

}
