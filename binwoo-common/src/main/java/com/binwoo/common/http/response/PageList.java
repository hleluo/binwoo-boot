package com.binwoo.common.http.response;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据.
 *
 * @author admin
 * @date 2019/9/16 17:27
 */
public class PageList<T> implements Serializable {

  private int pageOffset;
  private int pageSize;
  private int totalPage;
  private long totalSize;
  private List<T> content;

  /**
   * 构造函数.
   */
  public PageList() {
  }

  /**
   * 构造函数.
   *
   * @param pageOffset 页偏移,从0开始
   * @param pageSize 页大小
   * @param totalPage 总页数
   * @param totalSize 总大小
   * @param content 数据列表
   */
  public PageList(int pageOffset, int pageSize, int totalPage, long totalSize, List<T> content) {
    this.pageOffset = pageOffset;
    this.pageSize = pageSize;
    this.totalPage = totalPage;
    this.totalSize = totalSize;
    this.content = content;
  }

  public int getPageOffset() {
    return pageOffset;
  }

  public void setPageOffset(int pageOffset) {
    this.pageOffset = pageOffset;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public long getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(long totalSize) {
    this.totalSize = totalSize;
  }

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }
}
