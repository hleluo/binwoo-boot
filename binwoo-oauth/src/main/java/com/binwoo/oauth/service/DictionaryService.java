package com.binwoo.oauth.service;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.oauth.entity.Dictionary;
import java.util.List;

/**
 * 字典服务.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
public interface DictionaryService {

  /**
   * 保存字典信息.
   *
   * @param dictionary 字典信息
   * @return 字典信息
   */
  Dictionary save(Dictionary dictionary) throws HttpException;

  /**
   * 删除字典信息.
   *
   * @param id 字典id
   * @return 是否成功
   */
  boolean delete(String id);

  /**
   * 删除字典信息.
   *
   * @param ids 字典id列表
   * @return 是否成功
   */
  boolean delete(List<String> ids);

}
