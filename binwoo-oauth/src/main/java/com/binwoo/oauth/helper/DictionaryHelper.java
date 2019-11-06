package com.binwoo.oauth.helper;

import com.binwoo.oauth.entity.DictType;
import com.binwoo.oauth.repository.DictTypeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 字典帮助类.
 *
 * @author admin
 * @date 2019/11/6 16:40
 */
@Component
public class DictionaryHelper {

  private final DictTypeRepository dictTypeRepository;

  public DictionaryHelper(DictTypeRepository dictTypeRepository) {
    this.dictTypeRepository = dictTypeRepository;
  }

  /**
   * 获取所有后代节点，包括自己.
   *
   * @param dictType 字典类型
   * @return 字典类型列表
   */
  public List<DictType> getPosterityWithSelf(DictType dictType) {
    List<DictType> results = getPosterityWithoutSelf(dictType);
    results.add(dictType);
    return results;
  }

  /**
   * 获取所有后代节点，包括自己.
   *
   * @param dictTypes 字典类型列表
   * @return 字典类型列表
   */
  public List<DictType> getPosterityWithSelf(List<DictType> dictTypes) {
    List<DictType> results = new ArrayList<>();
    if (CollectionUtils.isEmpty(dictTypes)) {
      return results;
    }
    results.addAll(dictTypes);
    List<String> ids = dictTypes.stream().map(DictType::getId).collect(Collectors.toList());
    List<DictType> children = dictTypeRepository.findByParentIdIn(ids);
    if (!CollectionUtils.isEmpty(children)) {
      results.addAll(getPosterityWithSelf(children));
    }
    return results;
  }

  /**
   * 获取所有后代节点，包括自己.
   *
   * @param id 字典类型id
   * @return 字典类型列表
   */
  public List<DictType> getPosterityWithSelfById(String id) {
    if (StringUtils.isEmpty(id)) {
      return new ArrayList<>();
    }
    DictType dictType = dictTypeRepository.findById(id).orElse(null);
    return getPosterityWithSelf(dictType);
  }

  /**
   * 获取所有后代节点，包括自己.
   *
   * @param ids 字典类型id列表
   * @return 字典类型列表
   */
  public List<DictType> getPosterityWithSelfByIds(List<String> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return new ArrayList<>();
    }
    List<DictType> dictTypes = dictTypeRepository.findByIdIn(ids);
    return getPosterityWithSelf(dictTypes);
  }

  /**
   * 获取所有后代节点，不包括自己.
   *
   * @param dictType 字典类型
   * @return 字典类型列表
   */
  public List<DictType> getPosterityWithoutSelf(DictType dictType) {
    List<DictType> results = new ArrayList<>();
    if (dictType != null) {
      List<DictType> children = dictTypeRepository.findByParentId(dictType.getId());
      results.addAll(getPosterityWithSelf(children));
    }
    return results;
  }

}
