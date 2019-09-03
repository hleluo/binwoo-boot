package com.binwoo.poi.excel;

/**
 * Excel单元格.
 *
 * @author luoj
 * @date 2019/8/21 9:33
 */
public class ExcelCell {

  /**
   * 数据值.
   */
  private Object value;
  /**
   * 类型.
   */
  private Class<?> type;
  /**
   * 转换格式化.
   */
  private String format;
  /**
   * 样式装饰器.
   */
  private ExcelCellDecorator decorator;

  /**
   * 构造函数.
   */
  public ExcelCell() {
  }

  /**
   * 构造函数.
   *
   * @param value 值
   */
  public ExcelCell(Object value) {
    this.value = value;
  }

  /**
   * 构造函数.
   *
   * @param value 值
   * @param type 类型
   */
  public ExcelCell(Object value, Class<?> type) {
    this.value = value;
    this.type = type;
  }

  /**
   * 构造函数.
   *
   * @param value 值
   * @param type 类型
   * @param format 格式化
   */
  public ExcelCell(Object value, Class<?> type, String format) {
    this.value = value;
    this.type = type;
    this.format = format;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public Class<?> getType() {
    return type;
  }

  public void setType(Class<?> type) {
    this.type = type;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public ExcelCellDecorator getDecorator() {
    return decorator;
  }

  public void setDecorator(ExcelCellDecorator decorator) {
    this.decorator = decorator;
  }
}
