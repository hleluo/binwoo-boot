package com.binwoo.oauth.service;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Client;
import com.binwoo.oauth.req.ClientPagerReq;
import java.util.List;
import java.util.Set;

/**
 * 客户端服务接口.
 *
 * @author admin
 * @date 2019/9/4 17:00
 */
public interface ClientService {

  /**
   * 保存客户端信息.
   *
   * @param client 客户端信息
   * @return 客户端信息
   * @throws HttpException 异常
   */
  Client save(Client client) throws HttpException;

  /**
   * 分页查询客户端信息.
   *
   * @param req 查询参数
   * @return 客户端分页列表
   */
  PageList<Client> getByPager(ClientPagerReq req);

  /**
   * 删除客户端信息.
   *
   * @param id 客户端id
   * @return 是否成功
   */
  boolean delete(String id);

  /**
   * 删除客户端信息.
   *
   * @param ids 客户端id列表
   * @return 是否成功
   */
  boolean delete(List<String> ids);

  /**
   * 通过id获取客户端信息.
   *
   * @param id 客户端id
   * @return 客户端信息
   */
  Client getById(String id);

  /**
   * 更新客户端权职.
   *
   * @param id 客户端id
   * @param authorityIds 权职id列表
   * @return 是否成功
   */
  boolean updateAuthorities(String id, Set<String> authorityIds);

  /**
   * 更新客户端组.
   *
   * @param id 客户端id
   * @param groupIds 组id列表
   * @return 是否成功
   */
  boolean updateGroups(String id, Set<String> groupIds);

  /**
   * 更新客户端资源.
   *
   * @param id 客户端id
   * @param resourceIds 资源id列表
   * @return 是否成功
   */
  boolean updateResources(String id, Set<String> resourceIds);

}
