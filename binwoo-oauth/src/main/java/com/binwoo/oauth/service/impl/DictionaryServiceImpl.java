package com.binwoo.oauth.service.impl;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.oauth.entity.DictOption;
import com.binwoo.oauth.entity.DictType;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.helper.DictionaryHelper;
import com.binwoo.oauth.repository.DictOptionRepository;
import com.binwoo.oauth.repository.DictTypeRepository;
import com.binwoo.oauth.service.DictionaryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 字典服务实现.
 *
 * @author admin
 * @date 2019/9/18 17:12
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

  private final DictTypeRepository dictTypeRepository;
  private final DictOptionRepository dictOptionRepository;
  private final DictionaryHelper dictionaryHelper;

  /**
   * 构造函数.
   *
   * @param dictTypeRepository 字典类型仓库
   * @param dictOptionRepository 字典选项仓库
   * @param dictionaryHelper 字典帮助类
   */
  @Autowired
  public DictionaryServiceImpl(DictTypeRepository dictTypeRepository,
      DictOptionRepository dictOptionRepository, DictionaryHelper dictionaryHelper) {
    this.dictTypeRepository = dictTypeRepository;
    this.dictOptionRepository = dictOptionRepository;
    this.dictionaryHelper = dictionaryHelper;
  }

  @Override
  public DictType saveType(DictType dictType) throws HttpException {
    DictType source = dictTypeRepository.findByCode(dictType.getCode());
    if (source != null && !source.getId().equals(dictType.getId())) {
      throw new HttpException(HttpAuthExceptionCode.DICTIONARY_CODE_EXIST);
    }
    return dictTypeRepository.save(dictType);
  }

  @Override
  public DictOption saveOption(DictOption dictOption) throws HttpException {
    DictOption source = dictOptionRepository.findByCode(dictOption.getCode());
    if (source != null && !source.getId().equals(dictOption.getId())) {
      throw new HttpException(HttpAuthExceptionCode.DICTIONARY_CODE_EXIST);
    }
    return dictOptionRepository.save(dictOption);
  }

  @Override
  public boolean deleteType(String id) {
    if (!StringUtils.isEmpty(id)) {
      List<DictType> dictTypes = dictionaryHelper.getPosterityWithSelfById(id);
      return deleteTypes(dictTypes);
    }
    return true;
  }

  @Override
  public boolean deleteType(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      List<DictType> dictTypes = dictionaryHelper.getPosterityWithSelfByIds(ids);
      return deleteTypes(dictTypes);
    }
    return true;
  }

  @Override
  public boolean deleteOption(String id) {
    if (!StringUtils.isEmpty(id)) {
      dictOptionRepository.deleteById(id);
    }
    return true;
  }

  @Override
  public boolean deleteOption(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      dictOptionRepository.deleteByIdIn(ids);
    }
    return true;
  }

  @Override
  public List<DictType> getTypesTree() {
    List<DictType> dictTypes = (List<DictType>) dictTypeRepository.findAll();
    return dictionaryHelper.listToTree(dictTypes);
  }

  /**
   * 删除字典类型列表.
   *
   * @param dictTypes 字典类型列表
   * @return 是否成功
   */
  private boolean deleteTypes(List<DictType> dictTypes) {
    if (!CollectionUtils.isEmpty(dictTypes)) {
      List<String> ids = dictTypes.stream().map(DictType::getId).collect(Collectors.toList());
      dictOptionRepository.deleteByTypeIdIn(ids);
      dictTypeRepository.deleteByIdIn(ids);
    }
    return true;
  }


}
