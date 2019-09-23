package com.binwoo.oauth.controller;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.HttpResponse;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Authority;
import com.binwoo.oauth.req.AuthorityPagerReq;
import com.binwoo.oauth.req.BaseDeleteReq;
import com.binwoo.oauth.service.AuthorityService;
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
 * 权职模块.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Api(tags = {"权职模块"})
@Slf4j
@RestController
@RequestMapping("/api/authorities")
public class AuthorityController {

  private final AuthorityService authorityService;

  @Autowired
  public AuthorityController(AuthorityService authorityService) {
    this.authorityService = authorityService;
  }

  /**
   * 保存权职信息.
   *
   * @param authority 权职信息
   * @return 权职信息
   */
  @ApiOperation("保存权职信息")
  @PostMapping
  public HttpResponse<Authority> save(@RequestBody Authority authority) throws HttpException {
    log.info("save param = {}", authority);
    authority = authorityService.save(authority);
    log.info("save response = {}", authority);
    return HttpResponse.success(authority);
  }

  /**
   * 查询权职信息.
   *
   * @param req 查询参数
   * @return 权职信息列表
   */
  @ApiOperation("查询权职信息")
  @GetMapping
  public HttpResponse<PageList<Authority>> getByPager(@RequestBody AuthorityPagerReq req) {
    log.info("getByPager param = {}", req);
    PageList<Authority> pageList = authorityService.getByPager(req);
    log.info("getByPager response = {}", pageList);
    return HttpResponse.success(pageList);
  }

  /**
   * 删除权职信息.
   *
   * @param id 权职id
   * @return 是否成功
   */
  @ApiOperation("删除权职信息")
  @DeleteMapping("/{id}")
  public HttpResponse<Boolean> delete(@PathVariable String id) {
    log.info("delete id = {}", id);
    boolean success = authorityService.delete(id);
    log.info("delete response = {}", success);
    return HttpResponse.success(success);
  }

  /**
   * 批量删除权职信息.
   *
   * @param req 权职ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除权职信息")
  @DeleteMapping
  public HttpResponse<Boolean> delete(@RequestBody BaseDeleteReq req) {
    log.info("delete param = {}", req);
    boolean success = authorityService.delete(req.getIds());
    log.info("delete response = {}", success);
    return HttpResponse.success(success);
  }
}
