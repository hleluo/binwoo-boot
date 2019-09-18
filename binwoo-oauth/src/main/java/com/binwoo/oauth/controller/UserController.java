package com.binwoo.oauth.controller;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.HttpResponse;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.req.BaseDeleteReq;
import com.binwoo.oauth.req.UserAuthorityReq;
import com.binwoo.oauth.req.UserGroupReq;
import com.binwoo.oauth.req.UserPagerReq;
import com.binwoo.oauth.req.UserRoleReq;
import com.binwoo.oauth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Api(tags = {"用户模块"})
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * 保存用户信息.
   *
   * @param user 用户信息
   * @return 用户信息
   */
  @ApiOperation("保存用户信息：修改用户信息时，password不为空时设置新的密码")
  @PostMapping
  public HttpResponse<User> save(@RequestBody User user) throws HttpException {
    log.info("save param = {}", user);
    user = userService.save(user);
    log.info("save response = {}", user);
    return HttpResponse.success(user);
  }

  /**
   * 查询用户信息.
   *
   * @param req 查询参数
   * @return 用户信息列表
   */
  @ApiOperation("查询用户信息")
  @GetMapping
  public HttpResponse<PageList<User>> getByPager(@RequestBody UserPagerReq req) {
    log.info("getByPager param = {}", req);
    PageList<User> pageList = userService.getByPager(req);
    log.info("getByPager response = {}", pageList);
    return HttpResponse.success(pageList);
  }

  /**
   * 删除用户信息.
   *
   * @param id 用户id
   * @return 是否成功
   */
  @ApiOperation("删除用户信息")
  @DeleteMapping("/{id}")
  public HttpResponse<Boolean> delete(@PathVariable String id) {
    log.info("delete id = {}", id);
    boolean success = userService.delete(id);
    log.info("delete response = {}", success);
    return HttpResponse.success(success);
  }

  /**
   * 批量删除用户信息.
   *
   * @param req 用户ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除用户信息")
  @DeleteMapping
  public HttpResponse<Boolean> delete(@RequestBody BaseDeleteReq req) {
    log.info("delete param = {}", req);
    boolean success = userService.delete(req.getIds());
    log.info("delete response = {}", success);
    return HttpResponse.success(success);
  }

  /**
   * 根据id或用户名查询用户信息.
   *
   * @param key id或用户名
   * @return 用户信息
   */
  @ApiOperation("根据id或用户名查询用户信息")
  @GetMapping("/{key}")
  public HttpResponse<User> getByIdOrUsername(@PathVariable String key) {
    log.info("getByIdOrUsername param = {}", key);
    User user = userService.getByIdOrUsername(key);
    log.info("getByIdOrUsername response = {}", user);
    return HttpResponse.success(user);
  }

  /**
   * 更新用户角色.
   *
   * @param req 用户角色设置参数
   * @return 是否成功
   */
  @ApiOperation("更新角色")
  @PutMapping("/{id}/roles")
  public HttpResponse<Boolean> updateRoles(@PathVariable String id, @RequestBody UserRoleReq req) {
    log.info("updateRoles param = {}", req);
    boolean success = userService.updateRoles(id, req.getRoleIds());
    log.info("updateRoles response = {}", success);
    return HttpResponse.success(success);
  }

  /**
   * 更新用户权职.
   *
   * @param req 用户权职设置参数
   * @return 是否成功
   */
  @ApiOperation("更新权职")
  @PutMapping("/{id}/authorities")
  public HttpResponse<Boolean> updateAuthorities(@PathVariable String id,
      @RequestBody UserAuthorityReq req) {
    log.info("updateAuthorities param = {}", req);
    boolean success = userService.updateAuthorities(id, req.getAuthorityIds());
    log.info("updateAuthorities response = {}", success);
    return HttpResponse.success(success);
  }

  /**
   * 更新用户组.
   *
   * @param req 用户组设置参数
   * @return 是否成功
   */
  @ApiOperation("更新组")
  @PutMapping("/{id}/groups")
  public HttpResponse<Boolean> updateGroups(@PathVariable String id,
      @RequestBody UserGroupReq req) {
    log.info("updateGroups param = {}", req);
    boolean success = userService.updateGroups(id, req.getGroupIds());
    log.info("updateGroups response = {}", success);
    return HttpResponse.success(success);
  }
}
