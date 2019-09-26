package com.binwoo.poi.word;

import fr.opensagres.poi.xwpf.converter.core.ImageManager;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;
import sun.misc.BASE64Encoder;

/**
 * Word工具类.
 *
 * @author admin
 * @date 2019/9/26 10:04
 */
public class WordUtils {

  /**
   * HTML内容转Word.
   *
   * @param html html内容
   * @param dest 目标地址
   * @throws IOException 异常
   */
  public static void fromHtml(String html, String dest) throws IOException {
    File file = new File(dest);
    if (!file.getParentFile().exists()) {
      boolean b = file.getParentFile().mkdirs();
    }
    try (InputStream input = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
        OutputStream out = new FileOutputStream(file);
        POIFSFileSystem fs = new POIFSFileSystem()) {
      fs.createDocument(input, "WordDocument");
      fs.writeFilesystem(out);
    }
  }

  /**
   * Word转HTML.
   *
   * @param filepath word文件路径
   * @return html内容
   * @throws IOException 异常
   * @throws TransformerException 异常
   * @throws ParserConfigurationException 异常
   */
  public static String toHtml(String filepath)
      throws IOException, TransformerException, ParserConfigurationException {
    String extension = filepath.substring(filepath.lastIndexOf(".") + 1);
    String html = null;
    if ("doc".equalsIgnoreCase(extension)) {
      html = docToHtml(filepath);
    } else {
      html = docxToHtml(filepath);
    }
    return html;
  }

  /**
   * Word转HTML.
   *
   * @param filepath word文件路径
   * @param dest html存储路径
   * @throws IOException 异常
   * @throws TransformerException 异常
   * @throws ParserConfigurationException 异常
   */
  public static void toHtml(String filepath, String dest)
      throws IOException, TransformerException, ParserConfigurationException {
    String html = toHtml(filepath);
    writeToFile(html, dest);
  }

  /**
   * docx转HTML.
   *
   * @param filepath docx文件路径
   * @return html内容
   * @throws IOException 异常
   */
  private static String docxToHtml(String filepath) throws IOException {
    File file = new File(filepath).getParentFile();
    File fileImageDir = null;
    try (InputStream input = new FileInputStream(filepath);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        XWPFDocument document = new XWPFDocument(input)) {
      XHTMLOptions options = XHTMLOptions.create();
      options.setIgnoreStylesIfUnused(false);
      options.setFragment(true);
      //图片存储路径
      String imageDir = "IMAGE_" + UUID.randomUUID().toString().replaceAll("-", "");
      ImageManager manager = new ImageManager(file, imageDir);
      options.setImageManager(manager);

      XHTMLConverter converter = (XHTMLConverter) XHTMLConverter.getInstance();
      converter.convert(document, writer, options);
      String html = new String(out.toByteArray());
      //获取文件夹下的所有图片
      fileImageDir = new File(file.getPath() + File.separator + imageDir);
      if (fileImageDir.exists()) {
        File[] fileImages = fileImageDir.listFiles();
        if (fileImages != null) {
          for (File fileImage : fileImages) {
            byte[] bytes = readImageBase64(fileImage);
            if (bytes == null) {
              continue;
            }
            String key = String.format("%s\\\\%s", imageDir, fileImage.getName());
            html = html.replaceAll(key, byteToImageBase64(fileImage.getName(), bytes));
          }
        }
      }
      return html;
    } finally {
      if (fileImageDir != null) {
        File[] fileImages = fileImageDir.listFiles();
        if (fileImages != null) {
          for (int i = fileImages.length - 1; i >= 0; i--) {
            fileImages[i].deleteOnExit();
          }
          fileImageDir.deleteOnExit();
        }
      }
    }
  }

  /**
   * doc转HTML.
   *
   * @param filepath doc文件路径
   * @return html内容
   * @throws TransformerException 异常
   * @throws IOException 异常
   * @throws ParserConfigurationException 异常
   */
  private static String docToHtml(String filepath)
      throws TransformerException, IOException, ParserConfigurationException {
    try (InputStream input = new FileInputStream(filepath);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HWPFDocument document = new HWPFDocument(input)) {
      WordToHtmlConverter converter = new WordToHtmlConverter(
          DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
      converter.setPicturesManager(new PicturesManager() {
        @Override
        public String savePicture(byte[] bytes, PictureType pictureType, String s, float v,
            float v1) {
          return byteToImageBase64(s, bytes);
        }
      });
      converter.processDocument(document);
      Document htmlDocument = converter.getDocument();
      DOMSource domSource = new DOMSource(htmlDocument);
      StreamResult streamResult = new StreamResult(out);
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer serializer = factory.newTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING, StandardCharsets.UTF_8.name());
      serializer.setOutputProperty(OutputKeys.INDENT, "yes");
      serializer.setOutputProperty(OutputKeys.METHOD, "html");
      serializer.transform(domSource, streamResult);
      return new String(out.toByteArray());
    }
  }

  /**
   * 读取文件字节.
   *
   * @param file 文件
   * @return 字节数组
   */
  private static byte[] readImageBase64(File file) {
    try (InputStream input = new FileInputStream(file)) {
      byte[] bytes = new byte[input.available()];
      int length = input.read(bytes);
      return bytes;
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * 获取图片Base64.
   *
   * @param filename 文件名
   * @param bytes 字节数组
   */
  private static String byteToImageBase64(String filename, byte[] bytes) {
    BASE64Encoder encoder = new BASE64Encoder();
    String extension = filename.substring(filename.lastIndexOf(".") + 1);
    String src = encoder.encode(bytes);
    return "data:image/" + extension + ";base64," + src;
  }

  /**
   * 内容写入文件.
   *
   * @param content 内容
   * @param filepath 文件路径
   * @throws IOException 异常
   */
  private static void writeToFile(String content, String filepath) throws IOException {
    try (FileOutputStream out = new FileOutputStream(filepath)) {
      byte[] bytes = content == null ? new byte[0] : content.getBytes(StandardCharsets.UTF_8);
      out.write(bytes);
    }
  }

}
