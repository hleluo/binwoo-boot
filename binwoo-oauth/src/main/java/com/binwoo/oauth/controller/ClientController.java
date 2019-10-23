package com.binwoo.oauth.controller;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.HttpResponse;
import com.binwoo.common.http.response.HttpResponseBuilder;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Client;
import com.binwoo.oauth.req.BaseDeleteReq;
import com.binwoo.oauth.req.ClientAuthorityReq;
import com.binwoo.oauth.req.ClientGroupReq;
import com.binwoo.oauth.req.ClientPagerReq;
import com.binwoo.oauth.req.ClientResourceReq;
import com.binwoo.oauth.service.ClientService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端模块.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Api(tags = {"客户端模块"}, authorizations = {@Authorization("Authorization")})
@Slf4j
@RestController
@RequestMapping("/api/clients")
public class ClientController {

  private final ClientService clientService;

  @Autowired
  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  /**
   * 保存客户端信息.
   *
   * @param client 客户端信息
   * @return 客户端信息
   */
  @ApiOperation("保存客户端信息")
  @PostMapping
  public HttpResponse<Client> save(@RequestBody Client client) throws HttpException {
    log.info("save param = {}", client);
    client = clientService.save(client);
    log.info("save response = {}", client);
    return HttpResponseBuilder.save(client);
  }

  /**
   * 查询客户端信息.
   *
   * @param req 查询参数
   * @return 客户端信息列表
   */
  @ApiOperation("查询客户端信息")
  @GetMapping
  public HttpResponse<PageList<Client>> getByPager(ClientPagerReq req) {
    log.info("getByPager param = {}", req);
    PageList<Client> pageList = clientService.getByPager(req);
    log.info("getByPager response = {}", pageList);
    return HttpResponseBuilder.query(pageList);
  }

  /**
   * 删除客户端信息.
   *
   * @param id 客户端id
   * @return 是否成功
   */
  @ApiOperation("删除客户端信息")
  @ApiImplicitParam(name = "id", value = "客户端id", required = true)
  @DeleteMapping("/{id}")
  public HttpResponse<Boolean> delete(@PathVariable String id) {
    log.info("delete id = {}", id);
    boolean success = clientService.delete(id);
    log.info("delete response = {}", success);
    return HttpResponseBuilder.delete(success);
  }

  /**
   * 批量删除客户端信息.
   *
   * @param req 客户端ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除客户端信息")
  @PatchMapping("/delete")
  public HttpResponse<Boolean> delete(@RequestBody BaseDeleteReq req) {
    log.info("delete param = {}", req);
    boolean success = clientService.delete(req.getIds());
    log.info("delete response = {}", success);
    return HttpResponseBuilder.delete(success);
  }

  /**
   * 根据id查询客户端信息.
   *
   * @param id id
   * @return 客户端信息
   */
  @ApiOperation("根据id查询客户端信息")
  @GetMapping("/{id}")
  public HttpResponse<Client> getById(@PathVariable String id) {
    log.info("getById param = {}", id);
    Client user = clientService.getById(id);
    log.info("getById response = {}", user);
    return HttpResponseBuilder.query(user);
  }

  /**
   * 更新客户端权职.
   *
   * @param req 客户端权职设置参数
   * @return 是否成功
   */
  @ApiOperation("更新权职")
  @PutMapping("/authority")
  public HttpResponse<Boolean> updateAuthorities(@RequestBody ClientAuthorityReq req) {
    log.info("updateAuthorities param = {}", req);
    boolean success = clientService.updateAuthorities(req.getId(), req.getAuthorityIds());
    log.info("updateAuthorities response = {}", success);
    return HttpResponseBuilder.update(success);
  }

  /**
   * 更新客户端组.
   *
   * @param req 客户端组设置参数
   * @return 是否成功
   */
  @ApiOperation("更新组")
  @PutMapping("/group")
  public HttpResponse<Boolean> updateGroups(@RequestBody ClientGroupReq req) {
    log.info("updateGroups param = {}", req);
    boolean success = clientService.updateGroups(req.getId(), req.getGroupIds());
    log.info("updateGroups response = {}", success);
    return HttpResponseBuilder.update(success);
  }

  /**
   * 更新客户端资源.
   *
   * @param req 客户端资源设置参数
   * @return 是否成功
   */
  @ApiOperation("更新资源")
  @PutMapping("/resource")
  public HttpResponse<Boolean> updateResources(@RequestBody ClientResourceReq req) {
    log.info("updateResources param = {}", req);
    boolean success = clientService.updateResources(req.getId(), req.getResourceIds());
    log.info("updateResources response = {}", success);
    return HttpResponseBuilder.update(success);
  }
}
