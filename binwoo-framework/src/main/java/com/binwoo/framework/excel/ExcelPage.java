package com.binwoo.framework.excel;

import java.util.List;

/**
 * com.binwoo.framework.excel.
 *
 * @author luoj
 * @date 2019/8/21 9:33
 */
public class ExcelPage {

  /**
   * Sheet名称.
   */
  private String sheetName;
  /**
   * 是否动态数据，false sources有效，true loader有效.
   */
  private boolean dynamic = false;
  /**
   * 数据.
   */
  private List<ExcelRow> rows;
  /**
   * 数据加载器.
   */
  private ExcelRowLoader loader;
  /**
   * 合并.
   */
  private List<ExcelMerge> merges;

  /**
   * 构造函数.
   */
  public ExcelPage() {
  }

  /**
   * 构造函数.
   *
   * @param sheetName Sheet名称
   * @param rows 行数据
   */
  public ExcelPage(String sheetName, List<ExcelRow> rows) {
    this(sheetName, rows, null);
  }

  /**
   * 构造函数.
   *
   * @param sheetName Sheet名称
   * @param rows 行数据
   * @param merges 合并数据
   */
  public ExcelPage(String sheetName, List<ExcelRow> rows,
      List<ExcelMerge> merges) {
    this.sheetName = sheetName;
    this.rows = rows;
    this.merges = merges;
    this.dynamic = false;
  }

  /**
   * 构造函数.
   *
   * @param sheetName Sheet名称
   * @param loader 数据加载器
   */
  public ExcelPage(String sheetName, ExcelRowLoader loader) {
    this(sheetName, loader, null);
  }

  /**
   * 构造函数.
   *
   * @param sheetName Sheet名称
   * @param loader 数据加载器
   * @param merges 合并数据
   */
  public ExcelPage(String sheetName, ExcelRowLoader loader,
      List<ExcelMerge> merges) {
    this.sheetName = sheetName;
    this.loader = loader;
    this.merges = merges;
    this.dynamic = true;
  }

  public String getSheetName() {
    return sheetName;
  }

  public void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }

  public boolean isDynamic() {
    return dynamic;
  }

  public void setDynamic(boolean dynamic) {
    this.dynamic = dynamic;
  }

  public List<ExcelRow> getRows() {
    return rows;
  }

  public void setRows(List<ExcelRow> rows) {
    this.rows = rows;
  }

  public ExcelRowLoader getLoader() {
    return loader;
  }

  public void setLoader(ExcelRowLoader loader) {
    this.loader = loader;
  }

  public List<ExcelMerge> getMerges() {
    return merges;
  }

  public void setMerges(List<ExcelMerge> merges) {
    this.merges = merges;
  }
}
