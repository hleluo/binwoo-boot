package com.binwoo.oauth.service;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.Resource;
import com.binwoo.oauth.req.ResourcePagerReq;
import java.util.List;

/**
 * 资源服务.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
public interface ResourceService {

  /**
   * 保存资源信息.
   *
   * @param resource 资源信息
   * @return 资源信息
   * @throws HttpException 异常
   */
  Resource save(Resource resource) throws HttpException;

  /**
   * 分页查询资源信息.
   *
   * @param req 查询参数
   * @return 资源分页列表
   */
  PageList<Resource> getByPager(ResourcePagerReq req);

  /**
   * 删除资源信息.
   *
   * @param id 资源id
   * @return 是否成功
   */
  boolean delete(String id);

  /**
   * 删除资源信息.
   *
   * @param ids 资源id列表
   * @return 是否成功
   */
  boolean delete(List<String> ids);

}
