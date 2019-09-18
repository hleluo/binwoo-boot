package com.binwoo.oauth.service.impl;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.App;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.repository.AppRepository;
import com.binwoo.oauth.req.AppPagerReq;
import com.binwoo.oauth.service.AppService;
import java.util.List;
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

  @Override
  public App save(App app) throws HttpException {
    App source = appRepository.findByCodeAndType(app.getCode(), app.getType());
    if (source != null && !source.getId().equals(app.getId())) {
      throw new HttpException(HttpAuthExceptionCode.USERNAME_EXIST);
    }
    return appRepository.save(app);
  }

  @Override
  public PageList<App> getByPager(AppPagerReq req) {
    return null;
  }

  @Override
  public boolean delete(String id) {
    return false;
  }

  @Override
  public boolean delete(List<String> ids) {
    return false;
  }
}
