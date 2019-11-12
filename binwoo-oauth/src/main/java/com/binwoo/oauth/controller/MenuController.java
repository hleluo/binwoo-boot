package com.binwoo.oauth.controller;

import com.binwoo.common.http.response.HttpResponse;
import com.binwoo.common.http.response.HttpResponseBuilder;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Menu;
import com.binwoo.oauth.req.BaseDeleteReq;
import com.binwoo.oauth.req.MenuPagerReq;
import com.binwoo.oauth.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
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
 * 菜单模块.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Api(tags = {"菜单模块"}, authorizations = {@Authorization("Authorization")})
@Slf4j
@RestController
@RequestMapping("/api/menus")
public class MenuController {

  private final MenuService menuService;

  @Autowired
  public MenuController(MenuService menuService) {
    this.menuService = menuService;
  }

  /**
   * 保存菜单信息.
   *
   * @param menu 菜单信息
   * @return 菜单信息
   */
  @ApiOperation("保存菜单信息")
  @PostMapping
  public HttpResponse<Menu> save(@RequestBody Menu menu) {
    log.info("save param = {}", menu);
    menu = menuService.save(menu);
    log.info("save response = {}", menu);
    return HttpResponseBuilder.save(menu);
  }

  /**
   * 查询菜单信息.
   *
   * @param req 查询参数
   * @return 菜单信息列表
   */
  @ApiOperation("查询菜单信息")
  @GetMapping
  public HttpResponse<PageList<Menu>> getByPager(MenuPagerReq req) {
    log.info("getByPager param = {}", req);
    PageList<Menu> pageList = menuService.getByPager(req);
    log.info("getByPager response = {}", pageList);
    return HttpResponseBuilder.query(pageList);
  }

  /**
   * 删除菜单信息.
   *
   * @param id 菜单id
   * @return 是否成功
   */
  @ApiOperation("删除菜单信息")
  @ApiImplicitParam(name = "id", value = "菜单id", required = true)
  @DeleteMapping("/{id}")
  public HttpResponse<Boolean> delete(@PathVariable String id) {
    log.info("delete id = {}", id);
    boolean success = menuService.delete(id);
    log.info("delete response = {}", success);
    return HttpResponseBuilder.delete(success);
  }

  /**
   * 批量删除菜单信息.
   *
   * @param req 菜单ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除菜单信息")
  @PatchMapping("/delete")
  public HttpResponse<Boolean> delete(@RequestBody BaseDeleteReq req) {
    log.info("delete param = {}", req);
    boolean success = menuService.delete(req.getIds());
    log.info("delete response = {}", success);
    return HttpResponseBuilder.delete(success);
  }

  /**
   * 查询菜单树形列表.
   *
   * @return 菜单树形列表
   */
  @ApiOperation("查询菜单树形列表")
  @GetMapping("/getTree")
  public HttpResponse<List<Menu>> getTree() {
    log.info("getTree param is empty");
    List<Menu> menus = menuService.getTree();
    log.info("getTree response = {}", menus);
    return HttpResponseBuilder.query(menus);
  }
}
