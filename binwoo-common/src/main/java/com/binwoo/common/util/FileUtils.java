package com.binwoo.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * com.binwoo.common.util.
 *
 * @author luoj
 * @date 2019/8/19 17:40
 */
public class FileUtils {

  /**
   * 文件保存，文件流不会被关闭.
   *
   * @param input 文件流
   * @param dest 目标文件
   * @throws IOException 异常
   */
  public static void save(InputStream input, File dest) throws IOException {
    OutputStream out = null;
    int length = 0;
    try {
      if (!dest.getParentFile().exists()) {
        boolean b = dest.getParentFile().mkdirs();
      }
      dest.deleteOnExit();
      out = new FileOutputStream(dest);
      byte[] buffer = new byte[1024];
      while ((length = input.read(buffer)) != -1) {
        out.write(buffer, 0, length);
      }
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
