package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Menu;
import com.binwoo.oauth.exception.SqlException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 菜单仓库.
 *
 * @author admin
 * @date 2019/9/16 15:19
 */
@Repository
public interface MenuRepository extends BaseRepository<Menu> {

  /**
   * 根据应用id删除菜单信息.
   *
   * @param appId 应用id
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query("delete from Menu m where m.appId = :appId")
  void deleteByAppId(@Param("appId") String appId);

  /**
   * 根据应用id列表删除菜单信息.
   *
   * @param appIds 应用id列表
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query("delete from Menu m where m.appId in (:appIds)")
  void deleteByAppIdIn(@Param("appIds") List<String> appIds);

}
