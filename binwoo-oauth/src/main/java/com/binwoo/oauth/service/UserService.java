package com.binwoo.oauth.service;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.req.UserQueryReq;
import java.util.List;

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
  PageList<User> query(UserQueryReq req);

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

}
