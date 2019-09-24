package com.binwoo.poi.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;

/**
 * @author admin
 * @date 2019/9/23 15:19
 */
public class WordHelper {

  private static final char CHAR_START = '{';
  private static final char CHAR_END = '}';
  private static final char CHAR_FLAG = '$';
  private static final String PARAM_FLAG = String.valueOf(CHAR_FLAG);
  private static final String PARAM_START = String.valueOf(new char[]{CHAR_FLAG, CHAR_START});
  private static final String PARAM_END = String.valueOf(CHAR_END);
  private static final String TABLE_ROW_REPEAT = "ROW_REPEAT#";

  public static void export(String template, String dest, Map<String, Object> params)
      throws IOException {
    File file = new File(dest);
    if (!file.getParentFile().exists()) {
      boolean b = file.getParentFile().mkdirs();
    }
    try (InputStream input = new FileInputStream(template);
        OutputStream out = new FileOutputStream(dest)) {
      EXWPFDocument document = new EXWPFDocument(input);
      replaceForParagraphs(document, params);
      replaceForRows(document, params);
      replaceForTables(document, params);
      document.write(out);
    }
  }

  private static void replaceForParagraph(XWPFParagraph paragraph, Map<String, Object> params) {
    List<XWPFRun> runs = paragraph.getRuns();
    List<XWPFRun> items = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    boolean found = false;
    String key = null;
    for (int i = 0; i < runs.size(); i++) {
      XWPFRun run = runs.get(i);
      String text = run.toString();
      for (int j = 0; j < text.length(); j++) {
        char character = text.charAt(j);
        if (character == CHAR_FLAG) {
          //标识
          items.clear();
          sb.delete(0, sb.length());
          items.add(run);
          sb.append(CHAR_FLAG);
        } else if (character == CHAR_START) {
          if (PARAM_FLAG.equals(sb.toString())) {
            //开始
            if (!items.contains(run)) {
              items.add(run);
            }
            sb.append(CHAR_START);
          } else {
            items.clear();
            sb.delete(0, sb.length());
          }
        } else {
          if (sb.toString().startsWith(PARAM_START)) {
            //KEY
            if (!items.contains(run)) {
              items.add(run);
            }
            sb.append(character);
          } else {
            items.clear();
            sb.delete(0, sb.length());
          }
          if (sb.toString().startsWith(PARAM_START) && character == CHAR_END) {
            //结束
            int startIndex = sb.indexOf(PARAM_START) + PARAM_START.length();
            int endIndex = sb.indexOf(PARAM_END);
            key = sb.substring(startIndex, endIndex);
            if (params.containsKey(key)) {
              found = true;
              break;
            } else {
              items.clear();
              sb.delete(0, sb.length());
            }
          }
        }
      }
      if (found) {
        //前缀文本
        XWPFRun first = items.get(0);
        String prefix = first.toString().substring(0, first.toString().lastIndexOf(PARAM_START));
        CTRPr style = first.getCTR().getRPr();
        i = i - items.size() + 1;
        if (!"".equals(prefix)) {
          XWPFRun runPrefix = paragraph.insertNewRun(i++);
          runPrefix.getCTR().setRPr(style);
          runPrefix.setText(prefix);
        }
        //文本
        XWPFRun runValue = paragraph.insertNewRun(i++);
        runValue.getCTR().setRPr(style);
        runValue.setText((String) params.get(key));
        //后缀文本
        XWPFRun last = items.get(items.size() - 1);
        String suffix = last.toString()
            .substring(last.toString().indexOf(PARAM_END) + PARAM_END.length());
        if (!"".equals(suffix)) {
          XWPFRun runSuffix = paragraph.insertNewRun(i++);
          runSuffix.getCTR().setRPr(style);
          runSuffix.setText(suffix);
        }
        //删除多余项
        for (int j = i + items.size() - 1; j >= i; j--) {
          paragraph.removeRun(j);
        }
        i--;
        if (!"".equals(suffix)) {
          i--;
        }
        found = false;
        items.clear();
        sb.delete(0, sb.length());
        key = null;
      }
    }
  }

  private static void replaceForParagraphs(EXWPFDocument document, Map<String, Object> params) {
    Iterator<XWPFParagraph> iterator = document.getParagraphsIterator();
    if (iterator == null) {
      return;
    }
    while (iterator.hasNext()) {
      XWPFParagraph paragraph = iterator.next();
      replaceForParagraph(paragraph, params);
    }
  }

  private static void replaceForTables(EXWPFDocument document, Map<String, Object> params) {
    Iterator<XWPFTable> iterator = document.getTablesIterator();
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

  private static void replaceForRows(EXWPFDocument document, Map<String, Object> params) {
    Iterator<XWPFTable> iterator = document.getTablesIterator();
    while (iterator.hasNext()) {
      XWPFTable table = iterator.next();
      List<XWPFTableRow> rows = table.getRows();
      boolean found = false;
      String key = null;
      for (int i = 0; i < rows.size(); i++) {
        XWPFTableRow row = rows.get(i);
        if (found) {
          List<Map<String, Object>> sources = (List<Map<String, Object>>) params.get(key);
          for (Map<String, Object> source : sources) {
            XWPFTableRow target = table.createRow();
            copyRowStyle(row, target);
          }
        }
        List<XWPFTableCell> cells = row.getTableCells();
        String text = cells.size() == 1 ? cells.get(0).getText() : null;
        if (text != null && text.trim().startsWith(TABLE_ROW_REPEAT)) {
          key = text.trim().substring(TABLE_ROW_REPEAT.length());
          found = params.containsKey(key);
        } else {
          found = false;
        }
      }
    }
  }

  private static void copyRowStyle(XWPFTableRow source, XWPFTableRow target) {
    target.getCtRow().setTrPr(source.getCtRow().getTrPr());
    List<XWPFTableCell> cells = source.getTableCells();
    if (null == cells) {
      return;
    }
    XWPFTableCell targetCell = null;
    for (XWPFTableCell cell : cells) {
      targetCell = target.addNewTableCell();
      targetCell.setText("5476");
      targetCell.getCTTc().setTcPr(cell.getCTTc().getTcPr());
      targetCell.getParagraphs().get(0).getCTP()
          .setPPr(cell.getParagraphs().get(0).getCTP().getPPr());
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
