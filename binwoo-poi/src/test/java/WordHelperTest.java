import com.binwoo.poi.word.WordHelper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Word测试.
 *
 * @author admin
 * @date 2019/9/23 15:54
 */
public class WordHelperTest {

  /**
   * Word测试.
   *
   * @param args 参数
   */
  public static void main(String[] args) {
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("fileNumber_Text", "哈哈哈哈");
      WordHelper.export("D:\\ttt.docx", "D:\\123.docx", params);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
