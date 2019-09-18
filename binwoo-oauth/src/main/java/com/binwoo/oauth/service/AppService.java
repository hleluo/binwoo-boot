package com.binwoo.oauth.service;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.App;
import com.binwoo.oauth.req.AppPagerReq;
import java.util.List;

/**
 * 应用服务.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
public interface AppService {

  /**
   * 保存应用信息.
   *
   * @param app 应用信息
   * @return 应用信息
   * @throws HttpException 异常
   */
  App save(App app) throws HttpException;

  /**
   * 分页查询应用信息.
   *
   * @param req 查询参数
   * @return 应用分页列表
   */
  PageList<App> getByPager(AppPagerReq req);

  /**
   * 删除应用信息.
   *
   * @param id 应用id
   * @return 是否成功
   */
  boolean delete(String id);

  /**
   * 删除应用信息.
   *
   * @param ids 应用id列表
   * @return 是否成功
   */
  boolean delete(List<String> ids);

}
