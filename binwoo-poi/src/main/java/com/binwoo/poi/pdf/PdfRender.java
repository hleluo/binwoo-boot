package com.binwoo.poi.pdf;

import com.binwoo.poi.pdf.bean.PdfPicture;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

/**
 * PDF帮助类.
 *
 * @author admin
 * @date 2019/9/25 16:04
 */
public class PdfRender {

  /**
   * PDF阅读器.
   */
  private PdfReader reader;

  private PdfRender() {

  }

  /**
   * 构造函数.
   *
   * @param filepath 文件路径
   * @throws IOException 异常
   */
  public PdfRender(String filepath) throws IOException {
    try (InputStream input = new FileInputStream(filepath)) {
      reader = new PdfReader(input);
    }
  }

  /**
   * 构造函数.
   *
   * @param input 文件流
   * @throws IOException 异常
   */
  public PdfRender(InputStream input) throws IOException {
    reader = new PdfReader(input);
  }

  /**
   * 获取总页数.
   *
   * @return 总页数
   */
  public int getPageSize() {
    return reader.getNumberOfPages();
  }

  /**
   * 获取某一页的页面宽度.
   *
   * @param page 页号，从1开始
   * @return 宽度
   */
  public float getPageWidth(int page) {
    Rectangle rectangle = reader.getPageSize(page);
    return rectangle.getWidth();
  }

  /**
   * 获取某一页的页面高度.
   *
   * @param page 页号，从1开始
   * @return 高度
   */
  public float getPageHeight(int page) {
    Rectangle rectangle = reader.getPageSize(page);
    return rectangle.getHeight();
  }

  /**
   * 插入图片.
   *
   * @param page 页号，从1开始
   * @param pictures 图片列表
   * @param target 存储文件路径
   * @throws IOException IO异常
   * @throws DocumentException 文档异常
   */
  public void addPicture(int page, List<PdfPicture> pictures, String target)
      throws IOException, DocumentException {
    if (page < 0 || page > getPageSize()) {
      throw new IndexOutOfBoundsException("page is out of bounds");
    }
    File file = new File(target);
    if (!file.getParentFile().exists()) {
      boolean b = file.getParentFile().mkdirs();
    }
    PdfStamper stamper = null;
    OutputStream out = null;
    try {
      out = new FileOutputStream(target);
      stamper = new PdfStamper(reader, out);
      if (pictures != null && pictures.size() > 0) {
        // 获取操作的页面
        PdfContentByte under = stamper.getOverContent(page);
        for (PdfPicture picture : pictures) {
          // 根据域的大小缩放图片
          Image image = Image.getInstance(picture.getFilepath());
          float width = picture.isOriginal() ? image.getWidth() : picture.getDestWidth();
          float height = picture.isOriginal() ? image.getHeight() : picture.getDestHeight();
          image.scaleAbsolute(width, height);
          // 页面大小
          Rectangle rectangle = reader.getPageSize(page);
          // 添加图片
          float x = picture.getLeft();
          float y = rectangle.getHeight() - picture.getTop() - height;
          image.setAbsolutePosition(x, y);
          under.addImage(image);
        }
      }
    } finally {
      if (stamper != null) {
        try {
          stamper.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * 插入图片.
   *
   * @param page 页号，从1开始
   * @param picture 图片
   * @param target 存储文件路径
   * @throws IOException IO异常
   * @throws DocumentException 文档异常
   */
  public void addPicture(int page, PdfPicture picture, String target)
      throws IOException, DocumentException {
    addPicture(page, Collections.singletonList(picture), target);
  }

  /**
   * 关闭阅读器.
   */
  public void close() {
    if (reader != null) {
      try {
        reader.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
