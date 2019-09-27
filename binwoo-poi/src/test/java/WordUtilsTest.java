import com.binwoo.poi.word.WordUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.docx4j.openpackaging.exceptions.Docx4JException;

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
  public static void main(String[] args) throws IOException, Docx4JException {
    WordUtils.docxToDoc("D:\\test\\export.docx", "D:\\test\\docx_doc.doc");
    //WordUtils.docToDocx("D:\\test\\export.doc", "D:\\test\\doc_docx.docx");

    try (InputStream input = new FileInputStream("D:\\test\\docx_html.html")) {
      //WordUtils.toHtml("D:\\test\\export.docx", "D:\\test\\docx_html.html");
      byte[] bytes = new byte[input.available()];
      int length = input.read(bytes);
      WordUtils.fromHtml(new String(bytes), "D:\\test\\html_doc.doc");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
