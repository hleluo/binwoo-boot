package com.binwoo.poi.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * @author admin
 * @date 2019/9/23 15:19
 */
public class WordHelper {

  private static final String PARAM_START = "${";
  private static final String PARAM_END = "}";
  private static final String PARAM_IMAGE = "@";

  public static void export(String template, String dest, Map<String, Object> params)
      throws IOException {
    File file = new File(dest);
    if (!file.getParentFile().exists()) {
      boolean b = file.getParentFile().mkdirs();
    }
    try (InputStream input = new FileInputStream(template);
        OutputStream out = new FileOutputStream(dest)) {
      EXWPFDocument document = new EXWPFDocument(input);
      replaceForParagraphs(document.getParagraphsIterator(), params);
      document.write(out);
    }
  }

  private static void replaceForParagraph(XWPFParagraph paragraph, Map<String, Object> params) {
    List<XWPFRun> runs = paragraph.getRuns();
    for (XWPFRun run : runs) {
      String text = run.toString();
      if (text.startsWith(PARAM_START) && text.endsWith(PARAM_END)) {
        int startIndex = text.indexOf(PARAM_START) + PARAM_START.length();
        String key = text.substring(startIndex, text.indexOf(PARAM_END));
        if (key.startsWith(PARAM_IMAGE)) {
          //图片
        } else {
          //文字
          if (params.containsKey(key)) {
            String value = (String) params.get(key);
            run.setText(value == null ? "" : value);
          }
        }
      }
    }
  }

  private static void replaceForParagraphs(Iterator<XWPFParagraph> iterator,
      Map<String, Object> params) {
    if (iterator == null) {
      return;
    }
    while (iterator.hasNext()) {
      XWPFParagraph paragraph = iterator.next();
      replaceForParagraph(paragraph, params);
    }
  }

  private static void replaceForTables(Iterator<XWPFTable> iterator, Map<String, Object> params) {
    while (iterator.hasNext()) {
      XWPFTable table = iterator.next();
      List<XWPFTableRow> rows = table.getRows();
      for (XWPFTableRow row : rows) {
        List<XWPFTableCell> cells = row.getTableCells();
        for (XWPFTableCell cell : cells) {
          List<XWPFParagraph> paragraphs = cell.getParagraphs();
          for (XWPFParagraph paragraph : paragraphs) {
            replaceForParagraph(paragraph, params);
          }
        }
      }
    }
  }

  private static int getPictureType(String type) {
    int res = EXWPFDocument.PICTURE_TYPE_PICT;
    if (type != null) {
      if (type.equalsIgnoreCase("png")) {
        res = EXWPFDocument.PICTURE_TYPE_PNG;
      } else if (type.equalsIgnoreCase("dib")) {
        res = EXWPFDocument.PICTURE_TYPE_DIB;
      } else if (type.equalsIgnoreCase("emf")) {
        res = EXWPFDocument.PICTURE_TYPE_EMF;
      } else if (type.equalsIgnoreCase("jpg") || type.equalsIgnoreCase("jpeg")) {
        res = EXWPFDocument.PICTURE_TYPE_JPEG;
      } else if (type.equalsIgnoreCase("wmf")) {
        res = EXWPFDocument.PICTURE_TYPE_WMF;
      }
    }
    return res;
  }

}
