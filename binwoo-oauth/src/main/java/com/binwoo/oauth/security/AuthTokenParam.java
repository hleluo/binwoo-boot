package com.binwoo.oauth.security;

import java.util.Map;
import lombok.Data;

/**
 * 权限验证参数.
 *
 * @author hleluo
 * @date 2019/9/1 18:25
 */
@Data
public class AuthTokenParam {

  /**
   * 权限验证方式：用户名密码.
   */
  public static final String AUTH_TYPE_PASSWORD = "password";

  private String authType;
  private String username;
  private Map<String, String[]> parameters;

  /**
   * 获取指定参数的值.
   *
   * @param key 参数名
   * @return 参数值
   */
  public String getParameterValue(String key) {
    String[] values = this.parameters.get(key);
    if (values != null && values.length > 0) {
      return values[0];
    }
    return null;
  }

}
