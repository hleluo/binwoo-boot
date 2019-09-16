package com.binwoo.oauth.exception;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.HttpResponse;
import com.binwoo.framework.http.response.HttpResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理.
 *
 * @author admin
 * @date 2019/9/4 15:12
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

  /**
   * HttpException处理器.
   *
   * @param e HttpException
   * @return 返回结果
   */
  @ExceptionHandler(HttpException.class)
  public HttpResponse<String> httpExceptionHandler(HttpException e) {
    log.info("HttpException code = {}", e.getCode());
    return HttpResponseBuilder.failure(e.getCode());
  }

}
