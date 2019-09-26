package com.binwoo.poi.pdf;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * PDF工具类.
 *
 * @author admin
 * @date 2019/9/25 16:04
 */
public class PdfUtils {

  /**
   * Word转Pdf(支持docx文件).
   *
   * @param filepath word路径
   * @param dest pdf路径
   * @throws IOException 异常
   */
  public static void fromWord(String filepath, String dest) throws IOException {
    File file = new File(dest);
    if (!file.getParentFile().exists()) {
      boolean b = file.getParentFile().mkdirs();
    }
    try (OutputStream out = new FileOutputStream(dest)) {
      fromWord(filepath, out);
    }
  }

  /**
   * Word转Pdf(支持docx文件).
   *
   * @param filepath word路径
   * @param out PDF文件流
   * @throws IOException 异常
   */
  public static void fromWord(String filepath, OutputStream out) throws IOException {
    try (InputStream input = new FileInputStream(filepath)) {
      XWPFDocument document = new XWPFDocument(input);
      PdfOptions options = PdfOptions.create();
      PdfConverter.getInstance().convert(document, out, options);
    }
  }
}
