package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Api;
import com.binwoo.oauth.exception.SqlException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * API仓库.
 *
 * @author admin
 * @date 2019/9/16 15:19
 */
@Repository
public interface ApiRepository extends BaseRepository<Api> {

  /**
   * 根据资源id删除接口信息.
   *
   * @param resourceId 资源id
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query(value = "delete from Api a where a.resourceId = :resourceId")
  void deleteByResourceId(@Param("resourceId") String resourceId);

  /**
   * 根据资源id列表删除接口信息.
   *
   * @param resourceIds 资源id列表
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query(value = "delete from Api a where a.resourceId in (:resourceIds)")
  void deleteByResourceIdIn(@Param("resourceIds") List<String> resourceIds);

}
