package com.binwoo.oauth.service;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.oauth.entity.DictOption;
import com.binwoo.oauth.entity.DictType;
import java.util.List;

/**
 * 字典服务.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
public interface DictionaryService {

  /**
   * 保存字典类型信息.
   *
   * @param dictType 字典类型信息
   * @return 字典类型信息
   */
  DictType saveType(DictType dictType) throws HttpException;

  /**
   * 保存字典选项信息.
   *
   * @param dictOption 字典选项信息
   * @return 字典选项信息
   */
  DictOption saveOption(DictOption dictOption) throws HttpException;

  /**
   * 删除字典类型信息.
   *
   * @param id 字典类型id
   * @return 是否成功
   */
  boolean deleteType(String id);

  /**
   * 删除字典类型信息.
   *
   * @param ids 字典类型id列表
   * @return 是否成功
   */
  boolean deleteType(List<String> ids);

  /**
   * 删除字典选项信息.
   *
   * @param id 字典选项id
   * @return 是否成功
   */
  boolean deleteOption(String id);

  /**
   * 删除字典选项信息.
   *
   * @param ids 字典选项id列表
   * @return 是否成功
   */
  boolean deleteOption(List<String> ids);

  /**
   * 获取字典类型树形列表.
   *
   * @return 字典类型树形列表
   */
  List<DictType> getTypesTree();

}
