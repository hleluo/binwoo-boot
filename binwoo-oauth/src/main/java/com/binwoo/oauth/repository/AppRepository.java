package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.App;
import org.springframework.stereotype.Repository;

/**
 * 应用仓库.
 *
 * @author admin
 * @date 2019/9/5 16:56
 */
@Repository
public interface AppRepository extends BaseRepository<App> {

  /**
   * 根据标识和类型查询.
   *
   * @param code 标识
   * @param type 类型
   * @return 应用信息
   */
  App findByCodeAndType(String code, String type);

}
