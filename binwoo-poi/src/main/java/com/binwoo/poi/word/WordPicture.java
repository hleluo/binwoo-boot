package com.binwoo.poi.word;

/**
 * 图片数据.
 *
 * @author admin
 * @date 2019/9/23 15:21
 */
public class WordPicture {

  private int width;
  private int height;
  private String extension;
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

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }
}
