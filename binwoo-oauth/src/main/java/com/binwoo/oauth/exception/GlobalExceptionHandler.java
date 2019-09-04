package com.binwoo.oauth.exception;

import com.binwoo.framework.http.exception.HttpException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理.
 *
 * @author admin
 * @date 2019/9/4 15:12
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

  //运行时异常
  @ExceptionHandler(HttpException.class)
  public String httpExceptionHandler(HttpException e) {
    return "";
  }

}
