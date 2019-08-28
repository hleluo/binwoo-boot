package com.binwoo.framework.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * com.binwoo.framework.excel.
 *
 * @author luoj
 * @date 2019/8/26 17:33
 */
public class ExcelHelperTest {

  public static void main(String[] args) {
    ExcelWriter writer = new ExcelWriter();
    writer.setMassive(true);
    try {
      writer.write("测试", new ExcelRowLoader() {

        private final String[] HEADER_NAMES = {"序号", "姓名", "住址"};
        private ExcelCellDecorator decorator = new ExcelCellDecorator() {
          public void decorate(CellStyle style, Font font) {
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
          }
        };

        private ExcelRow buildHeader() {
          List<ExcelCell> items = new ArrayList<ExcelCell>();
          for (int i = 0; i < HEADER_NAMES.length; i++) {
            ExcelCell item = new ExcelCell();
            item.setValue(HEADER_NAMES[i]);
            item.setType(String.class);
            item.setDecorator(decorator);
            items.add(item);
          }
          return new ExcelRow(items);
        }

        private ExcelRow buildRow(int sequence, String name, String address) {
          List<ExcelCell> items = new ArrayList<ExcelCell>();
          items.add(new ExcelCell(sequence, Integer.class));
          items.add(new ExcelCell(name, String.class));
          items.add(new ExcelCell(address, String.class));
          return new ExcelRow(items);
        }

        public void load(ExcelWriter helper, Workbook workbook, Sheet sheet) {
          List<ExcelRow> sources = new ArrayList<ExcelRow>();
          sources.add(buildHeader());
          int index = 0;
          helper.append(sheet, index++, sources);
          while (index < 200) {
            helper.append(sheet, index, buildRow(index, "张三" + index, "生态科技园" + index));
            index++;
          }
        }
      }, null, "G:\\t1.xlsx");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
