package com.binwoo.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * com.binwoo.summer.
 *
 * @author luoj
 * @date 2019/8/16 15:10
 */
@SpringBootApplication
@ServletComponentScan("com.binwoo.common")
public class TestApplication {

  /**
   * 启动程序.
   *
   * @param args 参数
   */
  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class, args);
  }

}
