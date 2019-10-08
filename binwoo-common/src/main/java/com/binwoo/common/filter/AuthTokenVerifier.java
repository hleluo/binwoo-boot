package com.binwoo.common.filter;

import com.binwoo.common.util.JwtUtils;
import io.jsonwebtoken.Claims;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 * token验证器.
 *
 * @author admin
 * @date 2019/10/8 16:55
 */
public class AuthTokenVerifier {

  private static final String HEADER_AUTHORIZATION = "Authorization";

  private HttpServletRequest request;
  private String username;
  private String clientId;
  private boolean expired = false;

  private AuthTokenVerifier() {
  }

  public AuthTokenVerifier(HttpServletRequest request) {
    this.request = request;
  }

  public boolean verify(String[] excludes, String secret) {
    String url = request.getServletPath();
    if (excludes != null) {
      for (String exclude : excludes) {
        String pattern = exclude.replaceAll("\\*\\*", "\\.\\*");
        if (Pattern.matches(pattern, url)) {
          return true;
        }
      }
    }
    String authorization = request.getHeader(HEADER_AUTHORIZATION);
    if (authorization == null || "".equals(authorization.trim())) {
      return false;
    }
    authorization = authorization.trim();
    if (authorization.indexOf(' ') == -1) {
      return false;
    }
    String token = authorization.substring(authorization.indexOf(' ')).trim();
    JwtUtils.Result result = JwtUtils.parse(token, secret);
    if (!result.isValid()) {
      return false;
    }
    this.expired = result.isExpired();
    Claims claims = result.getClaims();
    return true;
  }

  public String getUsername() {
    return username;
  }

  public String getClientId() {
    return clientId;
  }

  public boolean isExpired() {
    return expired;
  }
}
