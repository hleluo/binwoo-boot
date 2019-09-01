package com.binwoo.oauth.security;

import org.springframework.security.core.userdetails.User;

/**
 * 用户验证集成器.
 *
 * @author hleluo
 * @date 2019/9/1 18:34
 */
public interface AuthTokenIntegrator {

  /**
   * 处理集成认证.
   *
   * @param param 登录参数
   */
  User authenticate(AuthTokenParam param);


  /**
   * 进行预处理.
   *
   * @param param 登录参数
   */
  void prepare(AuthTokenParam param);

  /**
   * 判断是否支持集成认证类型.
   *
   * @param param 登录参数.
   * @return 是否支持.
   */
  boolean support(AuthTokenParam param);

  /**
   * 认证结束后执行.
   *
   * @param param 登录参数.
   */
  void complete(AuthTokenParam param);

}
