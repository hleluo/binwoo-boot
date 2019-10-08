package com.binwoo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典注解.
 *
 * @author admin
 * @date 2019/10/8 11:02
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dictionary {

  /**
   * 字典分类对应的code.
   *
   * @return 字典分类对应的code
   */
  String type();

  /**
   * 对应的字典查询字段，如id、code等.
   *
   * @return 对应的字典查询字段
   */
  String query() default "id";

  /**
   * 分割符，多个值时的分隔符，默认为逗号.
   *
   * @return 分隔符
   */
  String separator() default ",";
}
