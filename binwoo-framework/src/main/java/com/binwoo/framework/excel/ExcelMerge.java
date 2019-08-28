package com.binwoo.framework.excel;

/**
 * com.binwoo.framework.excel.
 *
 * @author luoj
 * @date 2019/8/21 9:33
 */
public class ExcelMerge {

  /**
   * 起始行索引.
   */
  private int startRowIndex;
  /**
   * 结束行索引.
   */
  private int endRowIndex;
  /**
   * 起始列索引.
   */
  private int startColIndex;
  /**
   * 结束列索引.
   */
  private int endColIndex;

  public ExcelMerge() {
  }

  public ExcelMerge(int startRowIndex, int endRowIndex, int startColIndex, int endColIndex) {
    this.startRowIndex = startRowIndex;
    this.endRowIndex = endRowIndex;
    this.startColIndex = startColIndex;
    this.endColIndex = endColIndex;
  }

  public int getStartRowIndex() {
    return startRowIndex;
  }

  public void setStartRowIndex(int startRowIndex) {
    this.startRowIndex = startRowIndex;
  }

  public int getEndRowIndex() {
    return endRowIndex;
  }

  public void setEndRowIndex(int endRowIndex) {
    this.endRowIndex = endRowIndex;
  }

  public int getStartColIndex() {
    return startColIndex;
  }

  public void setStartColIndex(int startColIndex) {
    this.startColIndex = startColIndex;
  }

  public int getEndColIndex() {
    return endColIndex;
  }

  public void setEndColIndex(int endColIndex) {
    this.endColIndex = endColIndex;
  }
}
