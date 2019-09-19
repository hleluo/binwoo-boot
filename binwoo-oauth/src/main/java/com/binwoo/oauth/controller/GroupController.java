package com.binwoo.oauth.controller;

import com.binwoo.framework.http.response.HttpResponse;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.Group;
import com.binwoo.oauth.req.BaseDeleteReq;
import com.binwoo.oauth.req.GroupPagerReq;
import com.binwoo.oauth.service.GroupService;
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
 * 组模块.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Api(tags = {"组模块"})
@Slf4j
@RestController
@RequestMapping("/api/groups")
public class GroupController {

  private final GroupService groupService;

  @Autowired
  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  /**
   * 保存组信息.
   *
   * @param group 组信息
   * @return 组信息
   */
  @ApiOperation("保存组信息")
  @PostMapping
  public HttpResponse<Group> save(@RequestBody Group group) {
    log.info("save param = {}", group);
    group = groupService.save(group);
    log.info("save response = {}", group);
    return HttpResponse.success(group);
  }

  /**
   * 查询组信息.
   *
   * @param req 查询参数
   * @return 组信息列表
   */
  @ApiOperation("查询组信息")
  @GetMapping
  public HttpResponse<PageList<Group>> getByPager(@RequestBody GroupPagerReq req) {
    log.info("getByPager param = {}", req);
    PageList<Group> pageList = groupService.getByPager(req);
    log.info("getByPager response = {}", pageList);
    return HttpResponse.success(pageList);
  }

  /**
   * 删除组信息.
   *
   * @param id 组id
   * @return 是否成功
   */
  @ApiOperation("删除组信息")
  @DeleteMapping("/{id}")
  public HttpResponse<Boolean> delete(@PathVariable String id) {
    log.info("delete id = {}", id);
    boolean success = groupService.delete(id);
    log.info("delete response = {}", success);
    return HttpResponse.success(success);
  }

  /**
   * 批量删除组信息.
   *
   * @param req 组ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除组信息")
  @DeleteMapping
  public HttpResponse<Boolean> delete(@RequestBody BaseDeleteReq req) {
    log.info("delete param = {}", req);
    boolean success = groupService.delete(req.getIds());
    log.info("delete response = {}", success);
    return HttpResponse.success(success);
  }
}
