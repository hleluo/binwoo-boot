package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Api;
import java.util.List;
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
   * 根据id删除接口信息.
   *
   * @param id id
   */
  @Modifying
  @Query("delete from Api a where a.id = :id")
  void deleteById(@Param("id") String id);

  /**
   * 根据id列表删除接口信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query("delete from Api a where a.id in (:ids)")
  void deleteByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除组接口信息.
   *
   * @param id id
   */
  @Modifying
  @Query(value = "delete from t_group_api tga where api_id = :id", nativeQuery = true)
  void deleteGroupById(@Param("id") String id);

  /**
   * 根据资源id列表删除接口信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_group_api tga where api_id in (:ids)", nativeQuery = true)
  void deleteGroupByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据资源id删除接口信息.
   *
   * @param resourceId 资源id
   */
  @Modifying
  @Query(value = "delete from Api a where a.resourceId = :resourceId")
  void deleteByResourceId(@Param("resourceId") String resourceId);

  /**
   * 根据资源id列表删除接口信息.
   *
   * @param resourceIds 资源id列表
   */
  @Modifying
  @Query(value = "delete from Api a where a.resourceId in (:resourceIds)")
  void deleteByResourceIdIn(@Param("resourceIds") List<String> resourceIds);

}
