import com.binwoo.poi.word.WordHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
      params.put("test-a", "安慰我");
      params.put("testBen", "反对或法定");
      params.put("ten", "是的是的哥哥");
      List<Map<String, Object>> users = new ArrayList<>();
      users.add(buildUser());
      users.add(buildUser());
      params.put("users", users);
      WordHelper.export("D:\\ttt.docx", "D:\\123.docx", params);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Map<String, Object> buildUser(){
    Map<String, Object> user = new HashMap<>();
    user.put("name", "阿萨斯");
    user.put("sex", "男");
    user.put("nickname", "色认为");
    user.put("project", "去玩人机会签完了困扰结合起来外人看来我确认好可怜");
    return user;
  }

}
