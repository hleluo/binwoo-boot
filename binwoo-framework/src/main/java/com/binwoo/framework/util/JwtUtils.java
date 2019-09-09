package com.binwoo.framework.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;

/**
 * JWT Token解析器.
 *
 * @author hleluo
 * @date 2019/9/8 16:52
 */
public class JwtUtils {

  /**
   * 解析Token.
   *
   * @param token token字符串
   * @param secret 密码
   */
  public static Result parse(String token, String secret) {
    Result result = new Result();
    try {
      Claims claims = Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
          .parseClaimsJws(token).getBody();
      result.setValid(true);
      result.setExpired(false);
      result.setClaims(claims);
    } catch (Exception e) {
      if (e instanceof ExpiredJwtException) {
        result.setValid(true);
        result.setExpired(true);
      } else {
        result.setValid(false);
      }
    }
    return result;
  }

  /**
   * 结果.
   */
  public static class Result {

    private boolean valid = false;
    private boolean expired = true;
    private Claims claims;

    public boolean isValid() {
      return valid;
    }

    public void setValid(boolean valid) {
      this.valid = valid;
    }

    public boolean isExpired() {
      return expired;
    }

    public void setExpired(boolean expired) {
      this.expired = expired;
    }

    public Claims getClaims() {
      return claims;
    }

    public void setClaims(Claims claims) {
      this.claims = claims;
    }
  }
}
