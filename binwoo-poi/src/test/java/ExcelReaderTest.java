import com.binwoo.poi.excel.ExcelReader;
import com.binwoo.poi.excel.ExcelReader.CellParser;
import java.io.IOException;
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
      CellParser<User> parser = new CellParser<User>() {
        @Override
        public void parse(User object, int index, String key, Object value, Cell cell) {
          // 根据索引获取值
          switch (index) {
            case 0:
              object.setName((String) value);
              break;
            case 1:
              object.setAge(Integer.parseInt((String) value));
              break;
            case 2:
              object.setMoney(Double.parseDouble((String) value));
              break;
          }
          // 根据key获取值，对应的需要指定keyIndex所在的行
          if ("tag".equals(key)) {
            object.setTag((String) value);
          }
        }
      };
      List<User> users = reader.readByIndex(null, 1, null, User.class, 0, parser);
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
