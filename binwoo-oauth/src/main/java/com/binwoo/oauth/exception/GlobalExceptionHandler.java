package com.binwoo.oauth.exception;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.HttpResponse;
import com.binwoo.common.http.response.HttpResponseBuilder;
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

  /**
   * SqlException处理器.
   *
   * @param e SqlException
   * @return 返回结果
   */
  @ExceptionHandler(SqlException.class)
  public HttpResponse<String> sqlExceptionHandler(SqlException e) {
    log.info("HttpException >> ", e);
    return HttpResponseBuilder.failure(HttpAuthExceptionCode.INTERNAL_SERVER_ERROR);
  }

}
