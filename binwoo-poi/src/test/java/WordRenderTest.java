import com.binwoo.poi.word.bean.WordPicture;
import com.binwoo.poi.word.WordRender;
import com.binwoo.poi.word.bean.WordRow;
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
public class WordRenderTest {

  /**
   * Word测试.
   *
   * @param args 参数
   */
  public static void main(String[] args) {
    try {
      Map<String, Object> params = new HashMap<>();
      params.put("fileNumber_Text", buildPicture());
      params.put("test-a", "测试1");
      params.put("testBen", "测试2");
      params.put("ten", "测试测试");
      WordRow wordRow = new WordRow();
      wordRow.add(buildUser());
      wordRow.add(buildUser());
      WordRender render = new WordRender("D:\\test\\template.docx");
      params.put("users", wordRow);
      render.build("D:\\test\\export.docx", params);
      render.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Map<String, Object> buildUser() {
    Map<String, Object> user = new HashMap<>();
    user.put("name", "张三");
    user.put("sex", "男");
    user.put("nickname", "图图");
    user.put("project", buildPicture());
    return user;
  }

  private static WordPicture buildPicture() {
    WordPicture picture = new WordPicture();
    picture.setWidth(200);
    picture.setHeight(200);
    picture.setFilename("440300000000.png");
    try (InputStream input = new FileInputStream("D:\\test\\440300000000.png")) {
      byte[] content = new byte[input.available()];
      int length = input.read(content);
      picture.setContent(content);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return picture;
  }
}
