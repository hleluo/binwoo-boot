package com.binwoo.poi.pdf;

/**
 * PDF图片.
 *
 * @author admin
 * @date 2019/9/25 16:23
 */
public class PdfPicture {

  /**
   * 图片路径.
   */
  private String filepath;
  /**
   * 坐标，左上角为原点.
   */
  private float left;
  /**
   * 坐标，左上角为原点.
   */
  private float top;
  /**
   * 是否使用原始大小，为TRUE时，destWidth和destHeight不生效.
   */
  private boolean original = false;
  /**
   * 目标宽度.
   */
  private float destWidth;
  /**
   * 目标高度.
   */
  private float destHeight;

  public String getFilepath() {
    return filepath;
  }

  /**
   * 图片路径.
   *
   * @param filepath 图片路径
   */
  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public float getLeft() {
    return left;
  }

  /**
   * 设置坐标X，左上角为原点.
   *
   * @param left 坐标X
   */
  public void setLeft(float left) {
    this.left = left;
  }

  public float getTop() {
    return top;
  }

  /**
   * 设置坐标Y，左上角为原点.
   *
   * @param top 坐标Y
   */
  public void setTop(float top) {
    this.top = top;
  }

  public boolean isOriginal() {
    return original;
  }

  /**
   * 是否使用原始大小，为TRUE时，destWidth和destHeight不生效.
   *
   * @param original 是否使用原始大小，默认为false
   */
  public void setOriginal(boolean original) {
    this.original = original;
  }

  public float getDestWidth() {
    return destWidth;
  }

  public void setDestWidth(float destWidth) {
    this.destWidth = destWidth;
  }

  public float getDestHeight() {
    return destHeight;
  }

  public void setDestHeight(float destHeight) {
    this.destHeight = destHeight;
  }
}
