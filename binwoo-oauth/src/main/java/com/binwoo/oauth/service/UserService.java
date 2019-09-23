package com.binwoo.oauth.service;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.req.UserPagerReq;
import java.util.List;
import java.util.Set;

/**
 * 用户服务接口.
 *
 * @author admin
 * @date 2019/9/4 17:00
 */
public interface UserService {

  /**
   * 保存用户信息.
   *
   * @param user 用户信息
   * @return 用户信息
   * @throws HttpException 异常
   */
  User save(User user) throws HttpException;

  /**
   * 分页查询用户信息.
   *
   * @param req 查询参数
   * @return 用户分页列表
   */
  PageList<User> getByPager(UserPagerReq req);

  /**
   * 删除用户信息.
   *
   * @param id 用户id
   * @return 是否成功
   */
  boolean delete(String id);

  /**
   * 删除用户信息.
   *
   * @param ids 用户id列表
   * @return 是否成功
   */
  boolean delete(List<String> ids);

  /**
   * 通过id或用户名获取用户信息.
   *
   * @param key id或用户名
   * @return 用户信息
   */
  User getByIdOrUsername(String key);

  /**
   * 更新用户角色列表.
   *
   * @param id 用户ID
   * @param roleIds 角色id列表
   * @return 是否成功
   */
  boolean updateRoles(String id, Set<String> roleIds);

  /**
   * 更新用户权职.
   *
   * @param id 用户id
   * @param authorityIds 权职id列表
   * @return 是否成功
   */
  boolean updateAuthorities(String id, Set<String> authorityIds);

  /**
   * 更新用户组.
   *
   * @param id 用户id
   * @param groupIds 组id列表
   * @return 是否成功
   */
  boolean updateGroups(String id, Set<String> groupIds);

}
