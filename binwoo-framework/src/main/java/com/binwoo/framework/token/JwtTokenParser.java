package com.binwoo.framework.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Token解析器.
 *
 * @author hleluo
 * @date 2019/9/8 16:52
 */
public class JwtTokenParser {

  private Claims claims;

  private JwtTokenParser() {

  }

  /**
   * 构造函数.
   *
   * @param token token字符串
   * @param secret 密码
   */
  public JwtTokenParser(String token, String secret) {
    parse(token, secret);
  }

  /**
   * 解析Token.
   *
   * @param token token字符串
   * @param secret 密码
   */
  private void parse(String token, String secret) {
    try {
      claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      claims = null;
    }
  }

  /**
   * Token是否有效.
   *
   * @return 是否有效
   */
  public boolean isValid() {
    return claims != null;
  }

  /**
   * Token是否过期.
   *
   * @return 是否过期
   */
  public boolean isExpired() {
    return claims == null || claims.getExpiration().getTime() < System.currentTimeMillis();
  }


  /**
   * 获取值.
   *
   * @return 键值对
   */
  public Map<String, Object> getValues() {
    return claims == null ? new HashMap<>() : claims;
  }

  public <T> T getValues(Class<T> cls) {
    return null;
  }
}
