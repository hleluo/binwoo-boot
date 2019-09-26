package com.binwoo.poi.word;

import java.io.ByteArrayInputStream;
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
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * Word帮助类.
 *
 * @author admin
 * @date 2019/9/23 15:19
 */
public class WordExporter {

  private static final char CHAR_START = '{';
  private static final char CHAR_END = '}';
  private static final char CHAR_FLAG = '$';
  private static final String PARAM_FLAG = String.valueOf(CHAR_FLAG);
  private static final String PARAM_START = String.valueOf(new char[]{CHAR_FLAG, CHAR_START});
  private static final String PARAM_END = String.valueOf(CHAR_END);
  /**
   * 行复制标识.
   */
  private static final String TABLE_ROW_REPEAT = "ROW_REPEAT#";

  private XWPFDocument document;

  /**
   * 构造函数.
   *
   * @param template 模板文件路径
   * @throws IOException 异常
   */
  public WordExporter(String template) throws IOException {
    try (InputStream input = new FileInputStream(template)) {
      document = new XWPFDocument(input);
    }
  }

  /**
   * 构造函数.
   *
   * @param input 模板文件流
   * @throws IOException 异常
   */
  public WordExporter(InputStream input) throws IOException {
    document = new XWPFDocument(input);
  }

  /**
   * 导出Word.
   *
   * @param dest 目标地址
   * @param params 参数
   * @throws IOException 异常
   */
  public void export(String dest, Map<String, Object> params)
      throws IOException {
    File file = new File(dest);
    if (!file.getParentFile().exists()) {
      boolean b = file.getParentFile().mkdirs();
    }
    try (OutputStream out = new FileOutputStream(dest)) {
      export(out, params);
    }
  }

  /**
   * 导出Word.
   *
   * @param out 输出流
   * @param params 参数
   * @throws IOException 异常
   */
  public void export(OutputStream out, Map<String, Object> params)
      throws IOException {
    replaceForParagraphs(params);
    repeatForRows(params);
    replaceForTables(params);
    document.write(out);
  }


  /**
   * 替换段落信息.
   *
   * @param paragraph 段落
   * @param params 参数
   */
  private void replaceForParagraph(XWPFParagraph paragraph, Map<String, Object> params) {
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
        i = i - items.size() + 1;
        if (!"".equals(prefix)) {
          XWPFRun runPrefix = paragraph.insertNewRun(i++);
          copyRunStyle(runPrefix, first);
          runPrefix.setText(prefix);
        }
        //文本
        XWPFRun runValue = paragraph.insertNewRun(i++);
        copyRunStyle(runValue, first);
        //设置值
        buildValue(runValue, params.get(key), null);
        //后缀文本
        XWPFRun last = items.get(items.size() - 1);
        String suffix = last.toString()
            .substring(last.toString().indexOf(PARAM_END) + PARAM_END.length());
        if (!"".equals(suffix)) {
          XWPFRun runSuffix = paragraph.insertNewRun(i++);
          copyRunStyle(runSuffix, last);
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

  /**
   * 复制小段样式.
   *
   * @param source 源
   * @param target 目标
   */
  private void copyRunStyle(XWPFRun source, XWPFRun target) {
    target.setBold(source.isBold());
    target.setColor(source.getColor());
    target.setFontFamily(source.getFontFamily());
    target.setFontSize(source.getFontSize());
    target.getCTR().setRPr(source.getCTR().getRPr());
  }

  /**
   * 替换段落列表信息.
   *
   * @param params 参数
   */
  private void replaceForParagraphs(Map<String, Object> params) {
    Iterator<XWPFParagraph> iterator = document.getParagraphsIterator();
    if (iterator == null) {
      return;
    }
    while (iterator.hasNext()) {
      XWPFParagraph paragraph = iterator.next();
      replaceForParagraph(paragraph, params);
    }
  }

  /**
   * 替换表单列表信息.
   *
   * @param params 参数
   */
  private void replaceForTables(Map<String, Object> params) {
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

  /**
   * 替换多行数据.
   *
   * @param params 参数
   */
  private void repeatForRows(Map<String, Object> params) {
    Iterator<XWPFTable> iterator = document.getTablesIterator();
    while (iterator.hasNext()) {
      XWPFTable table = iterator.next();
      List<XWPFTableRow> rows = table.getRows();
      boolean found = false;
      String key = null;
      //遍历所有行
      for (int i = 0; i < rows.size(); i++) {
        XWPFTableRow row = rows.get(i);
        if (found && params.get(key) instanceof WordRow) {
          //删除行标识所在行
          table.removeRow(i - 1);
          i--;
          //追加数据
          WordRow wordRow = (WordRow) params.get(key);
          for (Map<String, Object> values : wordRow.getRows()) {
            XWPFTableRow target = table.insertNewTableRow(i + 1);
            appendRow(row, target, values);
          }
          //删除模板行
          table.removeRow(i);
          i--;
          i += wordRow.getRows().size();
        }
        List<XWPFTableCell> cells = row.getTableCells();
        String text = cells.size() == 1 ? cells.get(0).getText() : null;
        if (text != null && text.trim().startsWith(TABLE_ROW_REPEAT)) {
          //匹配到复制行数据的标识
          key = text.trim().substring(TABLE_ROW_REPEAT.length());
          found = params.containsKey(key);
        } else {
          found = false;
        }
      }
    }
  }

  /**
   * 拷贝样式.
   *
   * @param source 源样式
   * @param target 目标样式
   * @param params 参数
   */
  private void appendRow(XWPFTableRow source, XWPFTableRow target, Map<String, Object> params) {
    target.getCtRow().setTrPr(source.getCtRow().getTrPr());
    List<XWPFTableCell> sourceCells = source.getTableCells();
    if (null == sourceCells) {
      return;
    }
    for (XWPFTableCell sourceCell : sourceCells) {
      XWPFTableCell targetCell = target.addNewTableCell();
      targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
      String defaultText = sourceCell.getText();
      Object value = null;
      //通过标识{XXX}获取KEY对应的值
      if (defaultText.startsWith(String.valueOf(CHAR_START)) && defaultText
          .endsWith(String.valueOf(CHAR_END))) {
        int startIndex = defaultText.indexOf(CHAR_START) + String.valueOf(CHAR_START).length();
        int endIndex = defaultText.lastIndexOf(CHAR_END);
        String key = defaultText.substring(startIndex, endIndex);
        value = params.getOrDefault(key, null);
      }
      //获取段落
      if (targetCell.getParagraphs() == null || targetCell.getParagraphs().size() == 0) {
        targetCell.addParagraph();
      }
      XWPFParagraph paragraph = targetCell.getParagraphs().get(0);
      if (paragraph.getRuns() == null || paragraph.getRuns().size() == 0) {
        paragraph.createRun();
      }
      //获取小段
      XWPFRun run = paragraph.getRuns().get(0);
      if (sourceCell.getParagraphs() != null && sourceCell.getParagraphs().size() > 0) {
        paragraph.getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());
        if (sourceCell.getParagraphs().get(0).getRuns() != null
            && sourceCell.getParagraphs().get(0).getRuns().size() > 0) {
          XWPFRun template = sourceCell.getParagraphs().get(0).getRuns().get(0);
          copyRunStyle(run, template);
        }
      }
      //设置值
      buildValue(run, value, defaultText);
    }
  }

  /**
   * 构建值.
   *
   * @param run 小段
   * @param value 值
   * @param defaultText 默认值
   */
  private void buildValue(XWPFRun run, Object value, String defaultText) {
    defaultText = defaultText == null ? "" : defaultText;
    if (value == null) {
      run.setText(defaultText);
    } else {
      if (value instanceof WordPicture) {
        WordPicture picture = (WordPicture) value;
        try (ByteArrayInputStream input = new ByteArrayInputStream(picture.getContent())) {
          String filename = picture.getFilename();
          String extension = filename.substring(filename.lastIndexOf(".") + 1);
          run.addPicture(input, getPictureType(extension), filename,
              Units.toEMU(picture.getWidth()), Units.toEMU(picture.getHeight()));
        } catch (Exception e) {
          run.setText(defaultText);
        }
      } else {
        run.setText((String) value);
      }
    }
  }

  private static final String PIC_PNG = "png";
  private static final String PIC_DIB = "dib";
  private static final String PIC_EMF = "emf";
  private static final String PIC_JPG = "jpg";
  private static final String PIC_JPEG = "jpeg";
  private static final String PIC_WMF = "wmf";

  /**
   * 获取图片类型.
   *
   * @param extension 后缀
   * @return 类型
   */
  private int getPictureType(String extension) {
    int res = XWPFDocument.PICTURE_TYPE_PICT;
    if (extension == null) {
      return res;
    }
    if (PIC_PNG.equalsIgnoreCase(extension)) {
      res = XWPFDocument.PICTURE_TYPE_PNG;
    } else if (PIC_DIB.equalsIgnoreCase(extension)) {
      res = XWPFDocument.PICTURE_TYPE_DIB;
    } else if (PIC_EMF.equalsIgnoreCase(extension)) {
      res = XWPFDocument.PICTURE_TYPE_EMF;
    } else if (PIC_JPG.equalsIgnoreCase(extension) || PIC_JPEG.equalsIgnoreCase(extension)) {
      res = XWPFDocument.PICTURE_TYPE_JPEG;
    } else if (PIC_WMF.equalsIgnoreCase(extension)) {
      res = XWPFDocument.PICTURE_TYPE_WMF;
    }
    return res;
  }

}
