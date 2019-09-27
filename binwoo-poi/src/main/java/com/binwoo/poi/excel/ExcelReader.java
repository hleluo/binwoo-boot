package com.binwoo.poi.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Excel读取.
 *
 * @author luoj
 * @date 2019/8/27 11:25
 */
public class ExcelReader {

  /**
   * 单元格解析器.
   *
   * @param <T> 泛型
   */
  public interface CellParser<T> {

    /**
     * 解析数据.
     *
     * @param object 行数据对象
     * @param index 列索引，从0开始
     * @param key 列唯一键
     * @param cell 单元格
     */
    void parse(T object, int index, String key, Cell cell);
  }

  /**
   * Workbook.
   */
  private Workbook workbook;

  /**
   * 构造函数.
   *
   * @param filepath 文件路径
   * @throws IOException 异常
   */
  public ExcelReader(String filepath) throws IOException {
    try (InputStream input = new FileInputStream(filepath)) {
      workbook = WorkbookFactory.create(input);
    }
  }

  /**
   * 构造函数.
   *
   * @param input 文件流
   * @throws IOException 异常
   */
  public ExcelReader(InputStream input) throws IOException {
    workbook = WorkbookFactory.create(input);
  }

  /**
   * 读取数据.
   *
   * @param sheetIndex Sheet索引，从0开始
   * @param startRowIndex 起始行索引，从0开始，如为NULL，则自动获取
   * @param endRowIndex 结束行索引，从0开始，如为NULL，则自动获取
   * @param cls 泛型
   * @param keyIndex 键所在行索引，为空，则随机生成唯一key
   * @param parser 数据回调
   * @param <T> 泛型
   * @return 数据集
   */
  public <T> List<T> readByIndex(Integer sheetIndex, Integer startRowIndex,
      Integer endRowIndex, Class<T> cls, Integer keyIndex, CellParser<T> parser) {
    sheetIndex = sheetIndex == null ? 0 : sheetIndex;
    Sheet sheet = workbook.getSheetAt(sheetIndex);
    return read(sheet, startRowIndex, endRowIndex, cls, keyIndex, parser);
  }

  /**
   * 读取数据.
   *
   * @param sheetName Sheet名称
   * @param startRowIndex 起始行索引，从0开始，如为NULL，则自动获取
   * @param endRowIndex 结束行索引，从0开始，如为NULL，则自动获取
   * @param cls 泛型
   * @param keyIndex 键所在行索引，为空，则随机生成唯一key
   * @param parser 数据回调
   * @param <T> 泛型
   * @return 数据集
   */
  public <T> List<T> readByName(String sheetName, Integer startRowIndex,
      Integer endRowIndex, Class<T> cls, Integer keyIndex, CellParser<T> parser) {
    Sheet sheet = workbook.getSheet(sheetName);
    return read(sheet, startRowIndex, endRowIndex, cls, keyIndex, parser);
  }

  /**
   * 读取数据.
   *
   * @param sheet Sheet
   * @param startRowIndex 起始行索引，从0开始，如为NULL，则自动获取
   * @param endRowIndex 结束行索引，从0开始，如为NULL，则自动获取
   * @param cls 泛型
   * @param keyIndex 键所在行索引，为空，则随机生成唯一key
   * @param parser 数据回调
   * @param <T> 泛型
   * @return 数据集
   */
  private <T> List<T> read(Sheet sheet, Integer startRowIndex, Integer endRowIndex, Class<T> cls,
      Integer keyIndex, CellParser<T> parser) {
    //获取最大行
    int rowCount = sheet.getLastRowNum();
    startRowIndex = startRowIndex == null ? sheet.getFirstRowNum() : startRowIndex;
    startRowIndex = startRowIndex < sheet.getFirstRowNum() ? sheet.getFirstRowNum() : startRowIndex;
    endRowIndex = endRowIndex == null || endRowIndex > rowCount ? rowCount : endRowIndex;
    //获取最大列
    int columnCount = 0;
    for (int i = startRowIndex; i <= endRowIndex; i++) {
      Row row = sheet.getRow(i);
      columnCount = columnCount < row.getLastCellNum() ? row.getLastCellNum() : columnCount;
    }
    int startColumnIndex = 0;
    int endColumnIndex = columnCount;
    //读取key列表
    List<String> keys = new ArrayList<>();
    if (keyIndex != null && sheet.getRow(keyIndex) != null) {
      Row row = sheet.getRow(keyIndex);
      for (int i = startColumnIndex; i < endColumnIndex; i++) {
        Cell cell = row.getCell(i);
        String key = cell == null ? UUID.randomUUID().toString() : cell.getStringCellValue();
        keys.add(key);
      }
    } else {
      for (int i = startColumnIndex; i < endColumnIndex; i++) {
        keys.add(UUID.randomUUID().toString());
      }
    }
    //读取数据
    List<T> targets = new ArrayList<>();
    for (int i = startRowIndex; i <= endRowIndex; i++) {
      try {
        Row row = sheet.getRow(i);
        T target = cls.newInstance();
        for (int j = startColumnIndex; j < endColumnIndex; j++) {
          Cell cell = row.getCell(j);
          if (parser != null) {
            parser.parse(target, j, keys.get(j), cell);
          }
        }
        targets.add(target);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return targets;
  }

  /**
   * 关闭资源.
   */
  public void close() {
    if (workbook != null) {
      try {
        workbook.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
