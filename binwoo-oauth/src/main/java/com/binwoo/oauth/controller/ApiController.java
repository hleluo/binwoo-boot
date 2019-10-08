package com.binwoo.oauth.controller;

import com.binwoo.common.http.response.HttpResponse;
import com.binwoo.common.http.response.HttpResponseBuilder;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Api;
import com.binwoo.oauth.req.ApiPagerReq;
import com.binwoo.oauth.req.BaseDeleteReq;
import com.binwoo.oauth.service.ApiService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接口模块.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@io.swagger.annotations.Api(tags = {"接口模块"}, authorizations = {@Authorization("Authorization")})
@Slf4j
@RestController
@RequestMapping("/api/apis")
public class ApiController {

  private final ApiService apiService;

  @Autowired
  public ApiController(ApiService apiService) {
    this.apiService = apiService;
  }

  /**
   * 保存接口信息.
   *
   * @param api 接口信息
   * @return 接口信息
   */
  @ApiOperation("保存接口信息")
  @PostMapping
  public HttpResponse<Api> save(@RequestBody Api api) {
    log.info("save param = {}", api);
    api = apiService.save(api);
    log.info("save response = {}", api);
    return HttpResponseBuilder.save(api);
  }

  /**
   * 查询接口信息.
   *
   * @param req 查询参数
   * @return 接口信息列表
   */
  @ApiOperation("查询接口信息")
  @GetMapping
  public HttpResponse<PageList<Api>> getByPager(ApiPagerReq req) {
    log.info("getByPager param = {}", req);
    PageList<Api> pageList = apiService.getByPager(req);
    log.info("getByPager response = {}", pageList);
    return HttpResponseBuilder.query(pageList);
  }

  /**
   * 删除接口信息.
   *
   * @param id 接口id
   * @return 是否成功
   */
  @ApiOperation("删除接口信息")
  @ApiImplicitParam(name = "id", value = "接口id", required = true)
  @DeleteMapping("/{id}")
  public HttpResponse<Boolean> delete(@PathVariable String id) {
    log.info("delete id = {}", id);
    boolean success = apiService.delete(id);
    log.info("delete response = {}", success);
    return HttpResponseBuilder.delete(success);
  }

  /**
   * 批量删除接口信息.
   *
   * @param req 接口ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除接口信息")
  @PatchMapping
  public HttpResponse<Boolean> delete(@RequestBody BaseDeleteReq req) {
    log.info("delete param = {}", req);
    boolean success = apiService.delete(req.getIds());
    log.info("delete response = {}", success);
    return HttpResponseBuilder.delete(success);
  }
}
