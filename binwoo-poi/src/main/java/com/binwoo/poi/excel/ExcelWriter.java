package com.binwoo.poi.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel写入.
 *
 * @author luoj
 * @date 2019/8/21 9:29
 */
public class ExcelWriter {

  /**
   * 大数据量是缓存行数.
   */
  private static final int FLUSH_SIZE = 200;
  /**
   * Workbook.
   */
  private Workbook workbook;
  /**
   * 文件类型.
   */
  private ExcelType type = ExcelType.XLSX;
  /**
   * 是否大数据量.
   */
  private boolean massive = false;

  /**
   * 构造函数.
   */
  public ExcelWriter() {
  }

  /**
   * 构造函数.
   *
   * @param type 类型
   */
  public ExcelWriter(ExcelType type) {
    this.type = type;
  }

  /**
   * 是否大数据量.
   *
   * @param massive 大数据量
   */
  public void setMassive(boolean massive) {
    this.massive = massive;
  }

  /**
   * 导出数据.
   *
   * @param pages 页
   * @param out 输出流
   * @throws IOException 异常
   */
  public void write(List<ExcelPage> pages, OutputStream out)
      throws IOException {
    buildWorkbook();
    if (pages != null && pages.size() > 0) {
      for (int i = 0; i < pages.size(); i++) {
        buildSheet(i, pages.get(i));
      }
    }
    workbook.write(out);
    close();
  }

  /**
   * 导出数据.
   *
   * @param pages 页
   * @param filepath 文件路径，如/a/b.xls
   */
  public void write(List<ExcelPage> pages, String filepath) throws IOException {
    try (OutputStream out = buildOutput(filepath)) {
      write(pages, out);
    }
  }

  /**
   * 导出数据.
   *
   * @param sheetName Sheet名称
   * @param sources 数据源
   * @param merges 合并
   * @param out 输出流
   * @throws IOException 异常
   */
  public void write(String sheetName, List<ExcelRow> sources, List<ExcelMerge> merges,
      OutputStream out) throws IOException {
    buildWorkbook();
    Sheet sheet = workbook.createSheet(sheetName);
    append(sheet, 0, sources);
    merge(sheet, merges);
    workbook.write(out);
    close();
  }

  /**
   * 导出数据.
   *
   * @param sheetName Sheet名称
   * @param sources 数据源
   * @param merges 合并
   * @param filepath 文件路径，如/a/b.xls
   * @throws IOException 异常
   */
  public void write(String sheetName, List<ExcelRow> sources, List<ExcelMerge> merges,
      String filepath) throws IOException {
    try (OutputStream out = buildOutput(filepath)) {
      write(sheetName, sources, merges, out);
    }
  }

  /**
   * 导出数据.
   *
   * @param sheetName Sheet名称
   * @param loader 数据装装载器
   * @param merges 合并
   * @param out 输出流
   * @throws IOException 异常
   */
  public void write(String sheetName, ExcelRowLoader loader, List<ExcelMerge> merges,
      OutputStream out) throws IOException {
    buildWorkbook();
    Sheet sheet = workbook.createSheet(sheetName);
    if (loader != null) {
      loader.load(this, workbook, sheet);
    }
    merge(sheet, merges);
    workbook.write(out);
    close();
  }

  /**
   * 导出数据.
   *
   * @param sheetName Sheet名称
   * @param loader 数据装装载器
   * @param merges 合并
   * @param filepath 文件路径，如/a/b.xls
   * @throws IOException 异常
   */
  public void write(String sheetName, ExcelRowLoader loader, List<ExcelMerge> merges,
      String filepath) throws IOException {
    try (OutputStream out = buildOutput(filepath)) {
      write(sheetName, loader, merges, out);
    }
  }

  /**
   * 追加数据.
   *
   * @param sheet sheet
   * @param rowIndex 行索引
   * @param source 行数据
   */
  public void append(Sheet sheet, int rowIndex, ExcelRow source) {
    rowIndex = rowIndex < 0 ? 0 : rowIndex;
    if (source != null && source.getColumnCount() > 0) {
      Row row = sheet.createRow(rowIndex);
      List<ExcelCell> items = source.getCells();
      for (int j = 0; j < items.size(); j++) {
        ExcelCell item = items.get(j);
        Cell cell = row.createCell(j);
        cell.setCellStyle(buildStyle(item));
        setCellValue(cell, item);
      }
    }
  }

  /**
   * 追加数据.
   *
   * @param sheet sheet
   * @param rowIndex 行索引
   * @param sources 数据源
   */
  public void append(Sheet sheet, int rowIndex, List<ExcelRow> sources) {
    rowIndex = rowIndex < 0 ? 0 : rowIndex;
    if (sources != null && sources.size() > 0) {
      for (int i = rowIndex; i < sources.size(); i++) {
        ExcelRow items = sources.get(i);
        append(sheet, i, items);
      }
    }
  }

  /**
   * 根据文件路径获取文件类型.
   *
   * @param filepath 文件路径
   * @return 输出流
   */
  private OutputStream buildOutput(String filepath) throws FileNotFoundException {
    File file = new File(filepath);
    if (!file.getParentFile().exists()) {
      boolean b = file.getParentFile().mkdirs();
    }
    this.type = ExcelType.XLS;
    final String dot = ".";
    if (filepath.contains(dot) && !filepath.endsWith(dot)) {
      String extension = filepath.substring(filepath.lastIndexOf(dot) + 1);
      if (ExcelType.XLSX.toString().equalsIgnoreCase(extension.trim())) {
        this.type = ExcelType.XLSX;
      }
    }
    return new FileOutputStream(file);
  }

  /**
   * 构建Workbook.
   */
  private void buildWorkbook() {
    if (massive) {
      workbook = new SXSSFWorkbook(FLUSH_SIZE);
    } else {
      workbook = type == ExcelType.XLS ? new HSSFWorkbook() : new XSSFWorkbook();
    }
  }

  /**
   * 构建Sheet.
   *
   * @param index 页索引
   * @param page 页信息
   */
  private void buildSheet(int index, ExcelPage page) {
    String sheetName = page.getSheetName() == null ? "sheet" + (index + 1) : page.getSheetName();
    Sheet sheet = workbook.createSheet(sheetName);
    if (page.isDynamic()) {
      if (page.getLoader() != null) {
        page.getLoader().load(this, workbook, sheet);
      }
    } else {
      append(sheet, 0, page.getRows());
    }
    merge(sheet, page.getMerges());
  }

  /**
   * 单元格合并.
   *
   * @param sheet Sheet
   * @param merges 合并项
   */
  private void merge(Sheet sheet, List<ExcelMerge> merges) {
    if (merges != null && merges.size() > 0) {
      for (ExcelMerge merge : merges) {
        CellRangeAddress region = new CellRangeAddress(merge.getStartRowIndex(),
            merge.getEndRowIndex(), merge.getStartColIndex(), merge.getEndColIndex());
        sheet.addMergedRegion(region);
      }
    }
  }

  /**
   * 关闭.
   */
  private void close() {
    if (massive && workbook != null) {
      ((SXSSFWorkbook) workbook).dispose();
    }
    if (workbook != null) {
      try {
        workbook.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 单元格值.
   *
   * @param cell 单元格
   * @param item 数据项
   */
  private void setCellValue(Cell cell, ExcelCell item) {
    if (item.getValue() == null) {
      return;
    }
    if (item.getType() == Short.class) {
      cell.setCellValue((Short) item.getValue());
    } else if (item.getType() == Integer.class) {
      cell.setCellValue((Integer) item.getValue());
    } else if (item.getType() == Long.class) {
      cell.setCellValue((Long) item.getValue());
    } else if (item.getType() == Float.class) {
      cell.setCellValue((Float) item.getValue());
    } else if (item.getType() == Double.class) {
      cell.setCellValue((Double) item.getValue());
    } else if (item.getType() == Date.class) {
      cell.setCellValue((Date) item.getValue());
    } else if (item.getType() == Boolean.class) {
      cell.setCellValue((Boolean) item.getValue());
    } else if (item.getType() == String.class) {
      cell.setCellValue((String) item.getValue());
    } else {
      cell.setCellValue((String) item.getValue());
    }
  }

  /**
   * 默认样式.
   *
   * @param item 数据项
   * @return 样式
   */
  private CellStyle buildStyle(ExcelCell item) {
    CellStyle style = workbook.createCellStyle();
    if (item.getFormat() != null) {
      style.setDataFormat(HSSFDataFormat.getBuiltinFormat(item.getFormat()));
    }
    Font font = workbook.createFont();
    if (item.getDecorator() != null) {
      item.getDecorator().decorate(style, font);
    }
    style.setAlignment(HorizontalAlignment.CENTER);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setBorderTop(BorderStyle.THIN);
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);
    style.setTopBorderColor(HSSFColorPredefined.BLACK.getIndex());
    style.setBottomBorderColor(HSSFColorPredefined.BLACK.getIndex());
    style.setLeftBorderColor(HSSFColorPredefined.BLACK.getIndex());
    style.setRightBorderColor(HSSFColorPredefined.BLACK.getIndex());
    style.setWrapText(false);
    font.setFontName("仿宋");
    style.setFont(font);
    return style;
  }

}
