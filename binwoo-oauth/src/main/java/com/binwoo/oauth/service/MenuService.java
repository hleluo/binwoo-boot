package com.binwoo.oauth.service;

import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.Menu;
import com.binwoo.oauth.req.MenuPagerReq;
import java.util.List;

/**
 * 菜单服务.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
public interface MenuService {

  /**
   * 保存菜单信息.
   *
   * @param menu 菜单信息
   * @return 菜单信息
   */
  Menu save(Menu menu);

  /**
   * 分页查询菜单信息.
   *
   * @param req 查询参数
   * @return 菜单分页列表
   */
  PageList<Menu> getByPager(MenuPagerReq req);

  /**
   * 删除菜单信息.
   *
   * @param id 菜单id
   * @return 是否成功
   */
  boolean delete(String id);

  /**
   * 删除菜单信息.
   *
   * @param ids 菜单id列表
   * @return 是否成功
   */
  boolean delete(List<String> ids);

}
