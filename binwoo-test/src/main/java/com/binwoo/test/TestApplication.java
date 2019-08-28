package com.binwoo.test;

import com.binwoo.framework.response.HttpRetProperty;
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
@ServletComponentScan("com.binwoo.framework")
public class TestApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class, args);
    HttpRetProperty
        .setResource(TestApplication.class.getResource("/ret_message.properties").getPath());
    String value = HttpRetProperty.getValue(10000);
  }

}
