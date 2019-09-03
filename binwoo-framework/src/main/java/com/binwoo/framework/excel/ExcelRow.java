package com.binwoo.framework.excel;

import java.util.List;

/**
 * com.binwoo.framework.excel.
 *
 * @author luoj
 * @date 2019/8/21 9:33
 */
public class ExcelRow {

  /**
   * 列数.
   */
  private Integer columnCount;
  /**
   * 单元数据列表.
   */
  private List<ExcelCell> cells;

  /**
   * 构造函数.
   */
  public ExcelRow() {
  }

  public ExcelRow(List<ExcelCell> cells) {
    this.cells = cells;
  }

  public List<ExcelCell> getCells() {
    return cells;
  }

  public void setCells(List<ExcelCell> cells) {
    this.cells = cells;
  }

  public Integer getColumnCount() {
    return cells == null ? 0 : cells.size();
  }
}
