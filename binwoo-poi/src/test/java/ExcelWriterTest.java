import com.binwoo.poi.excel.ExcelCell;
import com.binwoo.poi.excel.ExcelRow;
import com.binwoo.poi.excel.ExcelWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel写文件测试.
 *
 * @author admin
 * @date 2019/9/23 10:33
 */
public class ExcelWriterTest {

  /**
   * Excel写文件测试.
   *
   * @param args 参数
   */
  public static void main(String[] args) {
    ExcelWriter writer = new ExcelWriter();
    List<ExcelRow> rows = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      ExcelRow row = new ExcelRow();
      List<ExcelCell> cells = new ArrayList<>();
      for (int j = 0; j < 10; j++) {
        ExcelCell cell = new ExcelCell();
        cell.setValue(i + "-" + j);
        cells.add(cell);
      }
      row.setCells(cells);
      rows.add(row);
    }
    try {
      writer.write("Test", rows, null, "D:\\write.xlsx");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
