package com.binwoo.oauth.integrate;

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
  /**
   * 认证方式.
   */
  private static final String PARAM_KEY_GRANT_TYPE = "grant_type";
  /**
   * 应用标识参数.
   */
  private static final String PARAM_KEY_APP_CODE = "app_code";
  /**
   * 应用类型参数.
   */
  private static final String PARAM_KEY_APP_TYPE = "app_type";

  /**
   * 权限验证方式.
   */
  private String authType;
  /**
   * 用户名.
   */
  private String username;
  /**
   * 参数列表.
   */
  private Map<String, String[]> parameters;

  /**
   * 获取指定参数的值.
   *
   * @param key 参数名
   * @return 参数值
   */
  public String getParameterValue(String key) {
    if (this.parameters == null) {
      return null;
    }
    String[] values = this.parameters.get(key);
    if (values != null && values.length > 0) {
      return values[0];
    }
    return null;
  }

  /**
   * 获取认证类型.
   *
   * @return 认证类型
   */
  public String getGrantType() {
    return getParameterValue(PARAM_KEY_GRANT_TYPE);
  }

  /**
   * 获取应用标识.
   *
   * @return 应用标识
   */
  public String getAppCode() {
    return getParameterValue(PARAM_KEY_APP_CODE);
  }

  /**
   * 获取应用类型.
   *
   * @return 应用类型
   */
  public String getAppType() {
    return getParameterValue(PARAM_KEY_APP_TYPE);
  }

}
