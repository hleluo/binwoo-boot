package com.binwoo.poi.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 数据加载器.
 *
 * @author luoj
 * @date 2019/8/21 9:33
 */
public interface ExcelRowLoader {

  /**
   * 装载数据.
   *
   * @param writer writer
   * @param workbook workbook
   * @param sheet sheet
   */
  void load(ExcelWriter writer, Workbook workbook, Sheet sheet);
}
