package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.App;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

  /**
   * 根据id删除应用信息.
   *
   * @param id id
   */
  @Override
  @Modifying
  @Query("delete from App a where a.id = :id")
  void deleteById(@Param("id") String id);

  /**
   * 根据id列表删除应用信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query("delete from App a where a.id in (:ids)")
  void deleteByIdIn(@Param("ids") List<String> ids);

}
