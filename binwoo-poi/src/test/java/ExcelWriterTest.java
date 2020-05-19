import com.binwoo.poi.excel.ExcelCellDecorator;
import com.binwoo.poi.excel.ExcelWriter;
import com.binwoo.poi.excel.bean.ExcelCell;
import com.binwoo.poi.excel.bean.ExcelRow;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

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
    // 定义样式
    ExcelCellDecorator decorator = new ExcelCellDecorator() {
      @Override
      public void decorate(CellStyle style, Font font) {
        // 设置粗体
        font.setBold(true);
      }
    };
    List<ExcelRow> rows = new ArrayList<>();
    // 添加表头
    ExcelRow header = new ExcelRow(new ExcelCell("姓名").decorate(decorator),
        new ExcelCell("年龄").decorate(decorator),
        new ExcelCell("金额").decorate(decorator));
    rows.add(header);
    // 添加行数据
    for (int i = 0; i < 2; i++) {
      ExcelRow row = new ExcelRow(new ExcelCell("张三"),
          new ExcelCell(i + 20, Integer.class),
          new ExcelCell(123456, Integer.class, "#,##0.00"));
      rows.add(row);
    }
    try {
      // 写入文件
      writer.write("demo", rows, null, "D:\\test\\write.xlsx");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
