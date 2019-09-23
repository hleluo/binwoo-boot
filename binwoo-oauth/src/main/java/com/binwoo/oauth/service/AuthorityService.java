package com.binwoo.oauth.service;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Authority;
import com.binwoo.oauth.req.AuthorityPagerReq;
import java.util.List;

/**
 * 权职服务.
 *
 * @author admin
 * @date 2019/9/18 17:12
 */
public interface AuthorityService {

  /**
   * 保存权职信息.
   *
   * @param authority 权职信息
   * @return 权职信息
   */
  Authority save(Authority authority) throws HttpException;

  /**
   * 分页查询权职信息.
   *
   * @param req 查询参数
   * @return 权职分页列表
   */
  PageList<Authority> getByPager(AuthorityPagerReq req);

  /**
   * 删除权职信息.
   *
   * @param id 权职id
   * @return 是否成功
   */
  boolean delete(String id);

  /**
   * 删除权职信息.
   *
   * @param ids 权职id列表
   * @return 是否成功
   */
  boolean delete(List<String> ids);

}
