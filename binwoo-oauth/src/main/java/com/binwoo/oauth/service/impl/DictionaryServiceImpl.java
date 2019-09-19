package com.binwoo.oauth.service.impl;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.oauth.entity.Dictionary;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.repository.DictionaryRepository;
import com.binwoo.oauth.service.DictionaryService;
import java.util.List;
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

  private final DictionaryRepository dictionaryRepository;

  /**
   * 构造函数.
   *
   * @param dictionaryRepository 字典仓库
   */
  @Autowired
  public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
    this.dictionaryRepository = dictionaryRepository;
  }

  @Override
  public Dictionary save(Dictionary dictionary) throws HttpException {
    Dictionary source = dictionaryRepository.findByCode(dictionary.getCode());
    if (source != null && !source.getId().equals(dictionary.getId())) {
      throw new HttpException(HttpAuthExceptionCode.DICTIONARY_EXIST);
    }
    return dictionaryRepository.save(dictionary);
  }

  @Override
  public boolean delete(String id) {
    if (!StringUtils.isEmpty(id)) {
      dictionaryRepository.deleteById(id);
      dictionaryRepository.deleteByTypeId(id);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      dictionaryRepository.deleteByIdIn(ids);
      dictionaryRepository.deleteByTypeIdIn(ids);
    }
    return true;
  }
}
