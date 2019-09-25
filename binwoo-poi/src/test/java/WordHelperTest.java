import com.binwoo.poi.word.WordHelper;
import com.binwoo.poi.word.WordPicture;
import com.binwoo.poi.word.WordRow;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
      params.put("fileNumber_Text", buildPicture());
      params.put("test-a", "安慰我");
      params.put("testBen", "反对或法定");
      params.put("ten", "是的是的哥哥");
      WordRow wordRow = new WordRow();
      wordRow.add(buildUser());
      wordRow.add(buildUser());

      params.put("users", wordRow);
      WordHelper.export("D:\\ttt.docx", "D:\\123.docx", params);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Map<String, Object> buildUser() {
    Map<String, Object> user = new HashMap<>();
    user.put("name", "阿萨斯");
    user.put("sex", "男");
    user.put("nickname", "色认为");

    user.put("project", buildPicture());
    return user;
  }

  private static WordPicture buildPicture() {
    WordPicture picture = new WordPicture();
    picture.setWidth(200);
    picture.setHeight(200);
    picture.setExtension("png");
    try (InputStream input = new FileInputStream("D:\\440300000000.png")) {
      byte[] content = new byte[input.available()];
      int length = input.read(content);
      picture.setContent(content);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return picture;
  }
}
