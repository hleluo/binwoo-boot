package com.binwoo.oauth.converter;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;

/**
 * JAP LocalDate转换Date.
 *
 * @author admin
 * @date 2019/10/23 14:55
 */
//@Converter(autoApply = true)
public class JpaLocalDateConverter implements AttributeConverter<LocalDate, Date> {

  @Override
  public Date convertToDatabaseColumn(LocalDate localDate) {
    return (localDate == null ? null : Date.valueOf(localDate));
  }

  @Override
  public LocalDate convertToEntityAttribute(Date date) {
    return (date == null ? null : date.toLocalDate());
  }
}
