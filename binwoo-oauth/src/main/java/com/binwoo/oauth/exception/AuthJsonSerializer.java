package com.binwoo.oauth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

/**
 * 异常数据返回定义.
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
    jsonGenerator.writeStartObject();
    jsonGenerator.writeObjectField("status", e.getHttpErrorCode());
    jsonGenerator.writeEndObject();
  }
}
