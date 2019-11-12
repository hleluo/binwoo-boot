package com.binwoo.oauth.controller;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.HttpResponse;
import com.binwoo.common.http.response.HttpResponseBuilder;
import com.binwoo.oauth.entity.DictOption;
import com.binwoo.oauth.entity.DictType;
import com.binwoo.oauth.req.BaseDeleteReq;
import com.binwoo.oauth.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dictionary Controller.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Api(tags = {"字典模块"}, authorizations = {@Authorization("Authorization")})
@Slf4j
@RestController
@RequestMapping("/api/dictionaries")
public class DictionaryController {

  private final DictionaryService dictionaryService;

  @Autowired
  public DictionaryController(DictionaryService dictionaryService) {
    this.dictionaryService = dictionaryService;
  }

  /**
   * 保存字典类型信息.
   *
   * @param dictType 字典类型信息
   * @return 字典类型信息
   */
  @ApiOperation("保存字典类型信息")
  @PostMapping("/types")
  public HttpResponse<DictType> saveType(@Validated @RequestBody DictType dictType)
      throws HttpException {
    log.info("saveType param = {}", dictType);
    dictType = dictionaryService.saveType(dictType);
    log.info("saveType response = {}", dictType);
    return HttpResponseBuilder.save(dictType);
  }

  /**
   * 查询字典类型树形列表.
   *
   * @return 字典类型树形列表
   */
  @ApiOperation("查询字典类型树形列表")
  @GetMapping("/types/tree")
  public HttpResponse<List<DictType>> getTypesTree() {
    log.info("getTypeTree param is empty");
    List<DictType> dictTypes = dictionaryService.getTypesTree();
    log.info("getTypeTree response = {}", dictTypes);
    return HttpResponseBuilder.query(dictTypes);
  }

  /**
   * 删除字典类型信息.
   *
   * @param id 字典类型id
   * @return 是否成功
   */
  @ApiOperation("删除字典类型信息")
  @ApiImplicitParam(name = "id", value = "字典类型id", required = true)
  @DeleteMapping("/types/{id}")
  public HttpResponse<Boolean> deleteType(@PathVariable String id) {
    log.info("deleteType id = {}", id);
    boolean success = dictionaryService.deleteType(id);
    log.info("deleteType response = {}", success);
    return HttpResponseBuilder.delete(success);
  }

  /**
   * 批量删除字典类型信息.
   *
   * @param req 字典类型ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除字典类型信息")
  @PatchMapping("/types/delete")
  public HttpResponse<Boolean> deleteType(@Validated @RequestBody BaseDeleteReq req) {
    log.info("deleteType param = {}", req);
    boolean success = dictionaryService.deleteType(req.getIds());
    log.info("deleteType response = {}", success);
    return HttpResponseBuilder.delete(success);
  }

  /**
   * 保存字典选项信息.
   *
   * @param dictOption 字典选项信息
   * @return 字典选项信息
   */
  @ApiOperation("保存字典选项信息")
  @PostMapping("/options")
  public HttpResponse<DictOption> saveOption(@Validated @RequestBody DictOption dictOption)
      throws HttpException {
    log.info("saveOption param = {}", dictOption);
    dictOption = dictionaryService.saveOption(dictOption);
    log.info("saveOption response = {}", dictOption);
    return HttpResponseBuilder.save(dictOption);
  }

  /**
   * 删除字典选项信息.
   *
   * @param id 字典选项id
   * @return 是否成功
   */
  @ApiOperation("删除字典选项信息")
  @ApiImplicitParam(name = "id", value = "字典选项id", required = true)
  @DeleteMapping("/options/{id}")
  public HttpResponse<Boolean> deleteOption(@PathVariable String id) {
    log.info("deleteOption id = {}", id);
    boolean success = dictionaryService.deleteOption(id);
    log.info("deleteOption response = {}", success);
    return HttpResponseBuilder.delete(success);
  }

  /**
   * 批量删除字典选项信息.
   *
   * @param req 字典选项ID列表
   * @return 是否成功
   */
  @ApiOperation("批量删除字典选项信息")
  @PatchMapping("/options/delete")
  public HttpResponse<Boolean> deleteOption(@Validated @RequestBody BaseDeleteReq req) {
    log.info("deleteOption param = {}", req);
    boolean success = dictionaryService.deleteOption(req.getIds());
    log.info("deleteOption response = {}", success);
    return HttpResponseBuilder.delete(success);
  }
}
