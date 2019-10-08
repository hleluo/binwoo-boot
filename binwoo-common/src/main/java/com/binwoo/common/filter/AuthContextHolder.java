package com.binwoo.common.filter;

/**
 * 认证上下文.
 *
 * @author admin
 * @date 2019/10/8 15:10
 */
public class AuthContextHolder {

  /**
   * 本地线程.
   */
  private static ThreadLocal<AuthContext> holder = new ThreadLocal<>();

  /**
   * 设置认证上下文信息.
   *
   * @param context 认证上下文信息
   */
  public static void set(AuthContext context) {
    holder.set(context);
  }

  /**
   * 获取认证上下文信息.
   *
   * @return 认证上下文信息
   */
  public static AuthContext get() {
    return holder.get();
  }

  /**
   * 清除认证上下文信息.
   */
  public static void clear() {
    holder.remove();
  }

}
