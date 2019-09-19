package com.binwoo.oauth.service;

import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.Api;
import com.binwoo.oauth.req.ApiPagerReq;
import java.util.List;

/**
 * 接口服务.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
public interface ApiService {

  /**
   * 保存接口信息.
   *
   * @param api 接口信息
   * @return 接口信息
   */
  Api save(Api api);

  /**
   * 分页查询接口信息.
   *
   * @param req 查询参数
   * @return 接口分页列表
   */
  PageList<Api> getByPager(ApiPagerReq req);

  /**
   * 删除接口信息.
   *
   * @param id 接口id
   * @return 是否成功
   */
  boolean delete(String id);

  /**
   * 删除接口信息.
   *
   * @param ids 接口id列表
   * @return 是否成功
   */
  boolean delete(List<String> ids);

}
