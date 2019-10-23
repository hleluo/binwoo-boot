package com.binwoo.oauth.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;

/**
 * JAP LocalDateTime转换Timestamp.
 *
 * @author admin
 * @date 2019/10/23 15:03
 */
//@Converter(autoApply = true)
public class JpaLocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

  @Override
  public Timestamp convertToDatabaseColumn(LocalDateTime time) {
    return (time == null ? null : Timestamp.valueOf(time));
  }

  @Override
  public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
    return (timestamp == null ? null : timestamp.toLocalDateTime());
  }
}
