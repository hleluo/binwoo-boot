package com.binwoo.oauth.util;

import com.binwoo.framework.http.response.PageList;
import org.springframework.data.domain.Page;

/**
 * 分页转换工具类.
 *
 * @author admin
 * @date 2019/9/18 15:55
 */
public class PageListUtils {

  /**
   * 分页数据转换.
   *
   * @param page Page分页
   * @param <T> 泛型
   * @return 分页
   */
  public static <T> PageList<T> convert(Page<T> page) {
    return new PageList<>(page.getNumber(), page.getSize(), page.getTotalPages(),
        page.getTotalElements(), page.getContent());
  }

}
