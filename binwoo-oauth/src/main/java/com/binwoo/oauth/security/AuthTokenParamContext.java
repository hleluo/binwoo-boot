package com.binwoo.oauth.security;

/**
 * 验证参数上下文.
 *
 * @author hleluo
 * @date 2019/9/1 18:25
 */
public class AuthTokenParamContext {

  private static ThreadLocal<AuthTokenParam> holder = new ThreadLocal<>();

  /**
   * 设置验证参数.
   *
   * @param param 验证参数
   */
  public static void set(AuthTokenParam param) {
    holder.set(param);
  }

  /**
   * 获取验证参数.
   *
   * @return 验证参数
   */
  public static AuthTokenParam get() {
    return holder.get();
  }

  /**
   * 清除验证参数.
   */
  public static void clear() {
    holder.remove();
  }

}
