import com.binwoo.poi.word.WordUtils;

/**
 * Word测试.
 *
 * @author admin
 * @date 2019/9/23 15:54
 */
public class WordUtilsTest {

  /**
   * Word测试.
   *
   * @param args 参数
   */
  public static void main(String[] args) {
    try {
      WordUtils.toHtml("D:\\123.docx", "D:\\123.html");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
