package com.binwoo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解.
 *
 * @author admin
 * @date 2019/10/8 11:02
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

  /**
   * 名称.
   *
   * @return 名称
   */
  String value();
}
