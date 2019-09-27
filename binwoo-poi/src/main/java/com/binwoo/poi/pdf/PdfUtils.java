package com.binwoo.poi.pdf;

import com.binwoo.poi.word.WordUtils;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.openpackaging.exceptions.Docx4JException;

/**
 * PDF工具类.
 *
 * @author admin
 * @date 2019/9/25 16:04
 */
public class PdfUtils {

  /**
   * Word转Pdf.
   *
   * @param filepath word路径
   * @param dest pdf路径
   * @throws IOException 异常
   * @throws Docx4JException 异常
   */
  public static void fromWord(String filepath, String dest) throws IOException, Docx4JException {
    File file = new File(dest);
    if (!file.getParentFile().exists()) {
      boolean b = file.getParentFile().mkdirs();
    }
    try (OutputStream out = new FileOutputStream(dest)) {
      fromWord(filepath, out);
    }
  }

  /**
   * Word转Pdf.
   *
   * @param filepath word路径
   * @param out PDF文件流
   * @throws IOException 异常
   * @throws Docx4JException 异常
   */
  public static void fromWord(String filepath, OutputStream out)
      throws IOException, Docx4JException {
    String extension = filepath.substring(filepath.lastIndexOf(".") + 1);
    if ("doc".equalsIgnoreCase(extension)) {
      fromDoc(filepath, out);
    } else {
      fromDocx(filepath, out);
    }
  }

  /**
   * docx转Pdf.
   *
   * @param filepath word路径
   * @param out PDF文件流
   * @throws IOException 异常
   */
  private static void fromDocx(String filepath, OutputStream out) throws IOException {
    try (InputStream input = new FileInputStream(filepath);
        XWPFDocument document = new XWPFDocument(input)) {
      PdfOptions options = PdfOptions.create();
      PdfConverter.getInstance().convert(document, out, options);
    }
  }

  /**
   * doc转Pdf.
   *
   * @param filepath word路径
   * @param out PDF文件流
   * @throws IOException 异常
   * @throws Docx4JException 异常
   */
  private static void fromDoc(String filepath, OutputStream out)
      throws IOException, Docx4JException {
    File file = new File(filepath).getParentFile();
    String docxPath = file.getPath() + File.separator + UUID.randomUUID().toString() + ".docx";
    File fileDocx = new File(docxPath);
    try {
      WordUtils.docToDocx(filepath, docxPath);
      fromDocx(docxPath, out);
    } finally {
      fileDocx.deleteOnExit();
    }

  }
}
