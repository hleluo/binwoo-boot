package com.binwoo.poi.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 * Excel单元装饰器.
 *
 * @author luoj
 * @date 2019/8/21 9:33
 */
public interface ExcelCellDecorator {

  /**
   * 样式装载.
   *
   * @param style 样式
   * @param font 字体
   */
  void decorate(CellStyle style, Font font);

}
