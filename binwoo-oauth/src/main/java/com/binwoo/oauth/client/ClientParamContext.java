package com.binwoo.oauth.client;

/**
 * 客户端参数信息管理器.
 *
 * @author hleluo
 * @date 2019/9/12 20:49
 */
public class ClientParamContext {

  /**
   * 本地线程.
   */
  private static ThreadLocal<ClientParam> holder = new ThreadLocal<>();

  /**
   * 设置客户端参数信息.
   *
   * @param param 客户端参数信息
   */
  public static void set(ClientParam param) {
    holder.set(param);
  }

  /**
   * 获取客户端参数信息.
   *
   * @return 客户端参数信息
   */
  public static ClientParam get() {
    return holder.get();
  }

  /**
   * 清除客户端参数信息.
   */
  public static void clear() {
    holder.remove();
  }

}
