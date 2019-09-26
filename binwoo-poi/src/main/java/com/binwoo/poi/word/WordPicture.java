package com.binwoo.poi.word;

/**
 * 图片数据.
 *
 * @author admin
 * @date 2019/9/23 15:21
 */
public class WordPicture {

  /**
   * 图片宽度.
   */
  private int width;
  /**
   * 图片高度.
   */
  private int height;
  /**
   * 图片文件名.
   */
  private String filename;
  /**
   * 图片字节.
   */
  private byte[] content;

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }
}
