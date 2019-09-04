package com.binwoo.oauth.security;

import com.binwoo.framework.http.response.HttpResponse;
import com.binwoo.framework.http.response.HttpResponseBuilder;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.util.JacksonUtils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 资源访问拒绝异常处理.
 *
 * @author admin
 * @date 2019/9/4 12:15
 */
@Component
public class ResourceAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AccessDeniedException e) throws IOException, ServletException {
    HttpResponse<String> response = HttpResponseBuilder
        .failure(HttpAuthExceptionCode.ACCESS_DENIED);
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.setContentType("application/json; charset=utf-8");
    PrintWriter printWriter = httpServletResponse.getWriter();
    String result = JacksonUtils.toJsonString(response);
    printWriter.write(result == null ? "{}" : result);
    printWriter.flush();
  }
}
