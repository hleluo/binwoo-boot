package com.binwoo.oauth.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Config.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
      new HashSet<>(Collections.singletonList("application/json"));

  /**
   * Config swagger docket.
   *
   * @return docket
   */
  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build()
        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
        .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
  }
}
