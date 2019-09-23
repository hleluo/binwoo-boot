package com.binwoo.oauth.exception;

import com.binwoo.common.http.response.HttpResponse;
import com.binwoo.common.http.response.HttpResponseBuilder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Map;

/**
 * 异常数据jackson返回定义.
 *
 * @author hleluo
 * @date 2019/9/1 00:05
 */
public class AuthJsonSerializer extends StdSerializer<AuthException> {

  protected AuthJsonSerializer() {
    super(AuthException.class);
  }

  @Override
  public void serialize(AuthException e, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    HttpResponse<String> response = HttpResponseBuilder.failure(e.getCode());
    Map<String, Object> map = HttpResponseBuilder.toMapWithoutNullValue(response);
    jsonGenerator.writeStartObject();
    for (String key : map.keySet()) {
      jsonGenerator.writeObjectField(key, map.get(key));
    }
    jsonGenerator.writeEndObject();
  }
}
