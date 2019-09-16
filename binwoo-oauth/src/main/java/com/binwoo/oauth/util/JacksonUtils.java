package com.binwoo.oauth.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Jackson工具类.
 *
 * @author admin
 * @date 2019/9/4 16:44
 */
public class JacksonUtils {

  /**
   * 对象转JSON字符串.
   *
   * @param obj 对象
   * @return JSON字符串
   */
  public static String toJsonString(Object obj) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.getSerializerProvider().setNullValueSerializer(getNullValueSerializer());
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  /**
   * 空值序列化处理.
   *
   * @return 序列化
   */
  public static JsonSerializer<Object> getNullValueSerializer() {
    return new JsonSerializer<Object>() {
      @Override
      public void serialize(Object o, JsonGenerator jsonGenerator,
          SerializerProvider serializerProvider) throws IOException {
        if (o instanceof List || o instanceof Set) {
          jsonGenerator.writeString("[]");
        } else if (o instanceof Optional) {
          jsonGenerator.writeString("{}");
        } else {
          jsonGenerator.writeString("");
        }
      }
    };
  }

  /**
   * JSON字符串转对象.
   *
   * @param json json字符串
   * @param cls 对象
   * @param <T> 泛型
   * @return 对象
   */
  public static <T> T toObject(String json, Class<T> cls) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(json, cls);
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * JSON字符串转对象列表.
   *
   * @param json json字符串
   * @param <T> 泛型
   * @return 对象列表
   */
  public static <T> T toList(String json) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(json, new TypeReference<List<T>>() {
      });
    } catch (IOException e) {
      return null;
    }
  }
}
