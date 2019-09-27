import com.binwoo.poi.pdf.PdfHelper;
import com.binwoo.poi.pdf.PdfPicture;

/**
 * PDF测试.
 *
 * @author admin
 * @date 2019/9/23 15:54
 */
public class PdfHelperTest {

  /**
   * PDF测试.
   *
   * @param args 参数
   */
  public static void main(String[] args) {
    try {
      PdfHelper helper = new PdfHelper("D:\\test\\docx_pdf.pdf");
      PdfPicture picture = new PdfPicture();
      picture.setAxisX(10);
      picture.setAxisX(10);
      picture.setFilepath("D:\\test\\440300000000.png");
      picture.setOriginal(true);
      helper.addPicture(1, picture, "D:\\test\\pdf_picture.pdf");
      helper.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
