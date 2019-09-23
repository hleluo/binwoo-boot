package com.binwoo.common.http.exception;

/**
 * HTTP异常代码.
 *
 * @author hleluo
 * @date 2019/9/3 21:18
 */
public interface HttpExceptionCode {

  /**
   * 获取RET.
   *
   * @return RET
   */
  int getRet();

  /**
   * 获取Code.
   *
   * @return code
   */
  String getValue();

}
