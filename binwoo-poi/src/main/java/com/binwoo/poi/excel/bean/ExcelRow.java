package com.binwoo.poi.excel.bean;

import java.util.Arrays;
import java.util.List;

/**
 * Excel行数据.
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

  /**
   * 构建数据行.
   *
   * @param cells 单元格列表.
   */
  public ExcelRow(List<ExcelCell> cells) {
    this.cells = cells;
  }

  /**
   * 构建数据行.
   *
   * @param cells 单元格列表.
   */
  public ExcelRow(ExcelCell... cells) {
    this.cells = Arrays.asList(cells);
  }

  public List<ExcelCell> getCells() {
    return cells;
  }

  public void setCells(List<ExcelCell> cells) {
    this.cells = cells;
  }

  /**
   * 获取行列数.
   *
   * @return 列数
   */
  public Integer getColumnCount() {
    return cells == null ? 0 : cells.size();
  }
}
