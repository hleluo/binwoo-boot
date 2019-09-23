package com.binwoo.oauth.controller;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.HttpResponse;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.App;
import com.binwoo.oauth.req.AppPagerReq;
import com.binwoo.oauth.req.BaseDeleteReq;
import com.binwoo.oauth.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用模块.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Api(tags = {"应用模块"})
@Slf4j
@RestController
@RequestMapping("/api/apps")
public class AppController {

  private final AppService appService;

  @Autowired
  public AppController(AppService appService) {
    this.appService = appService;
  }

  /**
   * 保存应用信息.
   *
   * @param app 应用信息
   * @return 应用信息
   */
  @ApiOperation("保存应用信息")
  @PostMapping
  public HttpResponse<App> save(@RequestBody App app) throws HttpException {
    log.info("save param = {}", app);
    app = appService.save(app);
    log.info("save response = {}", app);
    return HttpResponse.success(app);
  }

  /**
   * 查询应用信息.
   *
   * @param req 查询参数
   * @return 应用信息列表
   */
  @ApiOperation("查询应用信息")
  @GetMapping
  public HttpResponse<PageList<App>> getByPager(@RequestBody AppPagerReq req) {
    log.info("getByPager param = {}", req);
    PageList<App> pageList = appService.getByPager(req);
    log.info("getByPager response = {}", pageList);
    return HttpResponse.success(pageList);
  }

  /**
   * 删除应用信息.
   *
   * @param id 应用id
   * @return 是否成功
   */
  @ApiOperation("删除应用信息")
  @DeleteMapping("/{id}")
  public HttpResponse<Boolean> delete(@PathVariable String id) {
    log.info("delete id = {}", id);
    boolean success = appService.delete(id);
    log.info("delete response = {}", success);
    return HttpResponse.success(success);
  }

  /**
   * 批量删除应用信息.
   *
   * @param req 应用ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除应用信息")
  @DeleteMapping
  public HttpResponse<Boolean> delete(@RequestBody BaseDeleteReq req) {
    log.info("delete param = {}", req);
    boolean success = appService.delete(req.getIds());
    log.info("delete response = {}", success);
    return HttpResponse.success(success);
  }
}
