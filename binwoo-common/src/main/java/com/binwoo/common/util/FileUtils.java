package com.binwoo.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

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
  public static void write(InputStream input, File dest) throws IOException {
    if (!dest.getParentFile().exists()) {
      boolean b = dest.getParentFile().mkdirs();
    }
    dest.deleteOnExit();
    try (OutputStream out = new FileOutputStream(dest)) {
      int length = 0;
      byte[] buffer = new byte[1024];
      while ((length = input.read(buffer)) != -1) {
        out.write(buffer, 0, length);
      }
    }
  }

  /**
   * 文件保存，文件流不会被关闭.
   *
   * @param filepath 文件路径
   * @param content 文件内容
   * @param append 是否追加
   * @throws IOException 异常
   */
  public static void write(String filepath, String content, boolean append) throws IOException {
    File file = new File(filepath);
    if (!file.exists()) {
      boolean exits = file.createNewFile();
    }
    try (FileOutputStream fos = new FileOutputStream(file, append);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
      osw.write(content);
    }
  }

  /**
   * 读取文件内容.
   *
   * @param filepath 文件路径
   * @return 文件内容
   * @throws IOException 异常
   */
  public static String read(String filepath) throws IOException {
    File file = new File(filepath);
    if (!file.exists()) {
      throw new FileNotFoundException("File Not Found");
    }
    try (FileInputStream input = new FileInputStream(file)) {
      Long length = file.length();
      byte[] bytes = new byte[length.intValue()];
      int count = input.read(bytes);
      return new String(bytes, StandardCharsets.UTF_8);
    }
  }

}
