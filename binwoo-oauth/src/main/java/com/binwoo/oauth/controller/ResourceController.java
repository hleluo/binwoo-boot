package com.binwoo.oauth.controller;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.HttpResponse;
import com.binwoo.common.http.response.HttpResponseBuilder;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Resource;
import com.binwoo.oauth.req.BaseDeleteReq;
import com.binwoo.oauth.req.ResourcePagerReq;
import com.binwoo.oauth.service.ResourceService;
import io.swagger.annotations.Api;
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
 * 资源模块.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Api(tags = {"资源模块"}, authorizations = {@Authorization("Authorization")})
@Slf4j
@RestController
@RequestMapping("/api/resources")
public class ResourceController {

  private final ResourceService resourceService;

  @Autowired
  public ResourceController(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  /**
   * 保存资源信息.
   *
   * @param resource 资源信息
   * @return 资源信息
   */
  @ApiOperation("保存资源信息")
  @PostMapping
  public HttpResponse<Resource> save(@RequestBody Resource resource) throws HttpException {
    log.info("save param = {}", resource);
    resource = resourceService.save(resource);
    log.info("save response = {}", resource);
    return HttpResponseBuilder.save(resource);
  }

  /**
   * 查询资源信息.
   *
   * @param req 查询参数
   * @return 资源信息列表
   */
  @ApiOperation("查询资源信息")
  @GetMapping
  public HttpResponse<PageList<Resource>> getByPager(ResourcePagerReq req) {
    log.info("getByPager param = {}", req);
    PageList<Resource> pageList = resourceService.getByPager(req);
    log.info("getByPager response = {}", pageList);
    return HttpResponseBuilder.query(pageList);
  }

  /**
   * 删除资源信息.
   *
   * @param id 资源id
   * @return 是否成功
   */
  @ApiOperation("删除资源信息")
  @ApiImplicitParam(name = "id", value = "资源id", required = true)
  @DeleteMapping("/{id}")
  public HttpResponse<Boolean> delete(@PathVariable String id) {
    log.info("delete id = {}", id);
    boolean success = resourceService.delete(id);
    log.info("delete response = {}", success);
    return HttpResponseBuilder.delete(success);
  }

  /**
   * 批量删除资源信息.
   *
   * @param req 资源ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除资源信息")
  @PatchMapping
  public HttpResponse<Boolean> delete(@RequestBody BaseDeleteReq req) {
    log.info("delete param = {}", req);
    boolean success = resourceService.delete(req.getIds());
    log.info("delete response = {}", success);
    return HttpResponseBuilder.delete(success);
  }
}
