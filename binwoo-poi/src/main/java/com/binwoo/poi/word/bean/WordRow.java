package com.binwoo.poi.word.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 行数据.
 *
 * @author admin
 * @date 2019/9/23 15:21
 */
public class WordRow {

  /**
   * 行数据.
   */
  private List<Map<String, Object>> rows;

  public WordRow() {
    rows = new ArrayList<>();
  }

  public List<Map<String, Object>> getRows() {
    return rows == null ? new ArrayList<>() : rows;
  }

  public void setRows(List<Map<String, Object>> rows) {
    this.rows = rows == null ? new ArrayList<>() : rows;
  }

  /**
   * 添加行数据参数.
   *
   * @param params 参数
   */
  public void add(Map<String, Object> params) {
    rows.add(params);
  }
}
