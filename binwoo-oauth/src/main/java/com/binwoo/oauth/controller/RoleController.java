package com.binwoo.oauth.controller;

import com.binwoo.common.http.response.HttpResponse;
import com.binwoo.common.http.response.HttpResponseBuilder;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Role;
import com.binwoo.oauth.req.BaseDeleteReq;
import com.binwoo.oauth.req.RolePagerReq;
import com.binwoo.oauth.service.RoleService;
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
 * 角色模块.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Api(tags = {"角色模块"}, authorizations = {@Authorization("Authorization")})
@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RoleController {

  private final RoleService roleService;

  @Autowired
  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  /**
   * 保存角色信息.
   *
   * @param role 角色信息
   * @return 角色信息
   */
  @ApiOperation("保存角色信息")
  @PostMapping
  public HttpResponse<Role> save(@RequestBody Role role) {
    log.info("save param = {}", role);
    role = roleService.save(role);
    log.info("save response = {}", role);
    return HttpResponseBuilder.save(role);
  }

  /**
   * 查询角色信息.
   *
   * @param req 查询参数
   * @return 角色信息列表
   */
  @ApiOperation("查询角色信息")
  @GetMapping
  public HttpResponse<PageList<Role>> getByPager(RolePagerReq req) {
    log.info("getByPager param = {}", req);
    PageList<Role> pageList = roleService.getByPager(req);
    log.info("getByPager response = {}", pageList);
    return HttpResponseBuilder.query(pageList);
  }

  /**
   * 删除角色信息.
   *
   * @param id 角色id
   * @return 是否成功
   */
  @ApiOperation("删除角色信息")
  @ApiImplicitParam(name = "id", value = "角色id", required = true)
  @DeleteMapping("/{id}")
  public HttpResponse<Boolean> delete(@PathVariable String id) {
    log.info("delete id = {}", id);
    boolean success = roleService.delete(id);
    log.info("delete response = {}", success);
    return HttpResponseBuilder.delete(success);
  }

  /**
   * 批量删除角色信息.
   *
   * @param req 角色ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除角色信息")
  @PatchMapping("/delete")
  public HttpResponse<Boolean> delete(@RequestBody BaseDeleteReq req) {
    log.info("delete param = {}", req);
    boolean success = roleService.delete(req.getIds());
    log.info("delete response = {}", success);
    return HttpResponseBuilder.delete(success);
  }
}
