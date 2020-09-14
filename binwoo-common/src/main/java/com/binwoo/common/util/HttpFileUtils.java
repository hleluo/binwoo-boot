package com.binwoo.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

/**
 * com.binwoo.society.util.
 *
 * @author luoj
 * @date 2019/8/6 15:17
 */
public class HttpFileUtils {

  private static final String EXTENSION_GIF = ".gif";
  private static final String EXTENSION_JPG = ".jpg";
  private static final String EXTENSION_PDF = ".pdf";
  private static final String EXTENSION_DOC = ".doc";
  private static final String EXTENSION_DOCX = ".docx";
  private static final String EXTENSION_XLS = ".xls";
  private static final String EXTENSION_XLSX = ".xlsx";
  private static final String EXTENSION_PPT = ".ppt";
  private static final String EXTENSION_PPTX = ".pptx";

  private static String getFileExtension(String filename) {
    if (filename == null) {
      return null;
    }
    return filename.substring(filename.lastIndexOf("."));
  }

  /**
   * 文件上传.
   *
   * @param input 文件流
   * @param originalFilename 原始文件名
   * @param root 根目录，如/a/b
   * @param bucket 桶名，如image
   * @param savedFilename 保存的文件名，如abc.png，为空时自动生成
   * @return 文件路径，如/image/abc.png
   */
  public static String upload(InputStream input, String originalFilename, String root,
      String bucket, String savedFilename) throws IOException {
    String extension = getFileExtension(originalFilename);
    String filename = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase() + extension;
    filename = savedFilename == null ? filename : savedFilename;
    String filepath = File.separator + bucket + File.separator + filename;
    File dest = new File(root + filepath);
    if (!dest.getParentFile().exists()) {
      boolean b = dest.getParentFile().mkdirs();
    }
    FileUtils.write(input, dest);
    return String.format("/%s/%s", bucket, filename);
  }

  /**
   * 文件上传.
   *
   * @param input 文件流
   * @param originalFilename 原始文件名
   * @param root 根目录，如/a/b
   * @param bucket 桶名，如image
   * @return 文件路径，如/image/abc.png
   */
  public static String upload(InputStream input, String originalFilename, String root,
      String bucket) throws IOException {
    return upload(input, originalFilename, root, bucket, null);
  }

  /**
   * 文件下载.
   *
   * @param response 相应
   * @param filepath 文件路径
   * @throws IOException 异常
   */
  private static void download(HttpServletResponse response, String filepath) throws IOException {
    try (InputStream input = new FileInputStream(filepath);
        BufferedInputStream bis = new BufferedInputStream(input);
        OutputStream out = response.getOutputStream()) {
      int length = 0;
      byte[] buffer = new byte[1024];
      while ((length = bis.read(buffer)) != -1) {
        out.write(buffer, 0, length);
      }
    }
  }

  /**
   * 文件下载.
   *
   * @param response 响应
   * @param root 根目录，如/a/b
   * @param bucket 桶名，如image
   * @param filename filename 文件名，如abc.png
   * @param downloadName 下载文件名，如test.png，可为空
   */
  public static void download(HttpServletResponse response, String root, String bucket,
      String filename, String downloadName) throws IOException {
    response.setContentType("application/force-download");
    response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    response.addHeader("Content-Disposition",
        String.format("attachment;fileName=%s", downloadName == null ? filename : downloadName));
    String filepath = root + File.separator + bucket + File.separator + filename;
    download(response, filepath);
  }

  /**
   * 在线预览.
   *
   * @param response 响应
   * @param filepath 文件名
   * @param contentType 内容类型
   * @throws IOException 异常
   */
  private static void online(HttpServletResponse response, String filepath, String contentType)
      throws IOException {
    response.setContentType(contentType);
    response.setHeader("Set-Cookie", "fileDownload=true; path=/");
    download(response, filepath);
  }

  /**
   * 在线预览文件.
   *
   * @param response 响应
   * @param root 根目录，如/a/b
   * @param bucket 桶名，如pdf
   * @param filename filename 文件名，如abc.pdf
   * @throws IOException 异常
   */
  public static void online(HttpServletResponse response, String root, String bucket,
      String filename) throws IOException {
    String extension = filename.substring(filename.lastIndexOf("."));
    String contentType = null;
    if (EXTENSION_PDF.equalsIgnoreCase(extension)) {
      contentType = "application/pdf";
    } else if (EXTENSION_DOC.equalsIgnoreCase(extension) || EXTENSION_DOCX
        .equalsIgnoreCase(extension)) {
      contentType = "application/msword";
    } else if (EXTENSION_XLS.equalsIgnoreCase(extension) || EXTENSION_XLSX
        .equalsIgnoreCase(extension)) {
      contentType = "application/msexcel";
    } else if (EXTENSION_PPT.equalsIgnoreCase(extension) || EXTENSION_PPTX
        .equalsIgnoreCase(extension)) {
      contentType = "application/ms-powerpoint";
    } else {
      contentType = "application/force-download";
    }
    String filepath = root + File.separator + bucket + File.separator + filename;
    online(response, filepath, contentType);
  }

  /**
   * 图片显示.
   *
   * @param response HTTP响应
   * @param root 根目录，如/a/b
   * @param bucket 桶名，如image
   * @param filename 文件路径，如abc.png
   * @throws IOException IO异常
   */
  public static void view(HttpServletResponse response, String root, String bucket,
      String filename)
      throws IOException {
    String extension = filename.substring(filename.lastIndexOf("."));
    String contentType = "image/png";
    if (EXTENSION_GIF.equalsIgnoreCase(extension)) {
      contentType = "image/gif";
    } else if (EXTENSION_JPG.equalsIgnoreCase(extension)) {
      contentType = "image/jpeg";
    }
    String filepath = root + File.separator + bucket + File.separator + filename;
    online(response, filepath, contentType);
  }
}
