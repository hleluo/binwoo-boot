package com.binwoo.oauth.service;

import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Role;
import com.binwoo.oauth.req.RolePagerReq;
import java.util.List;

/**
 * 角色服务.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
public interface RoleService {

  /**
   * 保存角色信息.
   *
   * @param role 角色信息
   * @return 角色信息
   */
  Role save(Role role);

  /**
   * 分页查询角色信息.
   *
   * @param req 查询参数
   * @return 角色分页列表
   */
  PageList<Role> getByPager(RolePagerReq req);

  /**
   * 删除角色信息.
   *
   * @param id 角色id
   * @return 是否成功
   */
  boolean delete(String id);

  /**
   * 删除角色信息.
   *
   * @param ids 角色id列表
   * @return 是否成功
   */
  boolean delete(List<String> ids);

}
