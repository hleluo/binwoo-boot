import com.binwoo.poi.pdf.PdfUtils;

/**
 * PDF测试.
 *
 * @author admin
 * @date 2019/9/23 15:54
 */
public class PdfUtilsTest {

  /**
   * PDF测试.
   *
   * @param args 参数
   */
  public static void main(String[] args) {
    try {
      PdfUtils.fromWord("D:\\1234.doc", "D:\\12345.pdf");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
