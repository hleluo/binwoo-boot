package com.binwoo.oauth.service.impl;

import com.binwoo.oauth.repository.AuthorityRepository;
import com.binwoo.oauth.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 权职服务实现.
 *
 * @author admin
 * @date 2019/9/18 17:12
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

  private final AuthorityRepository authorityRepository;

  /**
   * 构造函数.
   *
   * @param authorityRepository 权职仓库
   */
  @Autowired
  public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
    this.authorityRepository = authorityRepository;
  }
}
