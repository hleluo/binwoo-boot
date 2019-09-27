import com.binwoo.poi.excel.ExcelReader;
import com.binwoo.poi.excel.ExcelReader.CellParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Excel读文件测试.
 *
 * @author admin
 * @date 2019/9/23 10:33
 */
public class ExcelReaderTest {

  /**
   * Excel读文件测试.
   *
   * @param args 参数
   */
  public static void main(String[] args) {
    try {
      ExcelReader reader = new ExcelReader("D:\\test\\write.xlsx");
      CellParser<HashMap> parser = new CellParser<HashMap>() {
        @Override
        public void parse(HashMap object, int index, String key, Cell cell) {
          ((HashMap<String, Object>) object).put(index + "", cell.getStringCellValue());
          System.out.println("56756");
        }
      };
      List<HashMap> data = reader.readByIndex(null,
          0, null, HashMap.class, null, parser);
      reader.close();
      System.out.println("success");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
