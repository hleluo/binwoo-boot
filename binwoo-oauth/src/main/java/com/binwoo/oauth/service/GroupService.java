package com.binwoo.oauth.service;

import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Group;
import com.binwoo.oauth.req.GroupPagerReq;
import java.util.List;

/**
 * 组服务.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
public interface GroupService {

  /**
   * 保存组信息.
   *
   * @param group 组信息
   * @return 组信息
   */
  Group save(Group group);

  /**
   * 分页查询组信息.
   *
   * @param req 查询参数
   * @return 组分页列表
   */
  PageList<Group> getByPager(GroupPagerReq req);

  /**
   * 删除组信息.
   *
   * @param id 组id
   * @return 是否成功
   */
  boolean delete(String id);

  /**
   * 删除组信息.
   *
   * @param ids 组id列表
   * @return 是否成功
   */
  boolean delete(List<String> ids);

}
