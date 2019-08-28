package com.binwoo.framework.excel;

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
   * @param helper helper
   * @param workbook workbook
   * @param sheet sheet
   */
  void load(ExcelWriter helper, Workbook workbook, Sheet sheet);
}
