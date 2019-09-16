package com.binwoo.oauth.config;

import com.binwoo.oauth.util.JacksonUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Jackson序列化配置.
 *
 * @author admin
 * @date 2019/9/16 17:10
 */
@Configuration
public class JacksonConfig {

  /**
   * Jackson序列化配置.
   *
   * @param builder 序列化构建器
   * @return 序列化
   */
  @Bean
  @Primary
  @ConditionalOnMissingBean(ObjectMapper.class)
  public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
    ObjectMapper mapper = builder.createXmlMapper(false).build();
    /*通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
    Include.Include.ALWAYS 默认
    Include.NON_DEFAULT 属性为默认值不序列化
    Include.NON_EMPTY 属性为空（""）或者为NULL都不序列化，则返回的json是没有这个字段的
    Include.NON_NULL 属性为NULL 不序列化*/
    //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    // 允许出现特殊字符和转义符
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    // 允许出现单引号
    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    // 字段保留，将null值转为""
    mapper.getSerializerProvider().setNullValueSerializer(JacksonUtils.getNullValueSerializer());
    return mapper;
  }

}
