package com.binwoo.oauth.service.impl;

import com.binwoo.oauth.repository.AppRepository;
import com.binwoo.oauth.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 应用服务实现.
 *
 * @author admin
 * @date 2019/9/18 17:19
 */
@Service
public class AppServiceImpl implements AppService {

  private final AppRepository appRepository;

  @Autowired
  public AppServiceImpl(AppRepository appRepository) {
    this.appRepository = appRepository;
  }
}
