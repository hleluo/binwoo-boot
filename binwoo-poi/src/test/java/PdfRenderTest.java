import com.binwoo.poi.pdf.PdfRender;
import com.binwoo.poi.pdf.bean.PdfPicture;

/**
 * PDF测试.
 *
 * @author admin
 * @date 2019/9/23 15:54
 */
public class PdfRenderTest {

  /**
   * PDF测试.
   *
   * @param args 参数
   */
  public static void main(String[] args) {
    try {
      PdfRender render = new PdfRender("D:\\test\\docx_pdf.pdf");
      PdfPicture picture = new PdfPicture();
      picture.setLeft(10);
      picture.setTop(10);
      picture.setFilepath("D:\\test\\440300000000.png");
      picture.setOriginal(true);
      render.addPicture(1, picture, "D:\\test\\pdf_picture.pdf");
      render.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
