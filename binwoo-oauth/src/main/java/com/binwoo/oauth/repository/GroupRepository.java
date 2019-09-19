package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Group;
import com.binwoo.oauth.exception.SqlException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 组仓库.
 *
 * @author admin
 * @date 2019/9/16 15:19
 */
@Repository
public interface GroupRepository extends BaseRepository<Group> {

  /**
   * 根据资源id删除组信息.
   *
   * @param resourceId 资源id
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query(value = "delete from Group g where g.resourceId = :resourceId")
  void deleteByResourceId(@Param("resourceId") String resourceId);

  /**
   * 根据资源id列表删除组信息.
   *
   * @param resourceIds 资源id列表
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query(value = "delete from Group g where g.resourceId in (:resourceIds)")
  void deleteByResourceIdIn(@Param("resourceIds") List<String> resourceIds);

  /**
   * 根据资源id删除组接口信息.
   *
   * @param resourceId 资源id
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query(value = "delete from t_group_api tga where tga.group_id in "
      + "(select id from t_group where resource_id = :resourceId)", nativeQuery = true)
  void deleteApiByResourceId(@Param("resourceId") String resourceId);

  /**
   * 根据资源id列表删除组接口信息.
   *
   * @param resourceIds 资源id列表
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query(value = "delete from t_group_api tga where tga.group_id in "
      + "(select id from t_group where resource_id in (:resourceIds))", nativeQuery = true)
  void deleteApiByResourceIdIn(@Param("resourceIds") List<String> resourceIds);

  /**
   * 根据资源id删除用户组信息.
   *
   * @param resourceId 资源id
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query(value = "delete from t_user_group tug where tug.group_id in "
      + "(select id from t_group where resource_id = :resourceId)", nativeQuery = true)
  void deleteUserByResourceId(@Param("resourceId") String resourceId);

  /**
   * 根据资源id列表删除用户组信息.
   *
   * @param resourceIds 资源id列表
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query(value = "delete from t_user_group tug where tug.group_id in "
      + "(select id from t_group where resource_id in (:resourceIds))", nativeQuery = true)
  void deleteUserByResourceIdIn(@Param("resourceIds") List<String> resourceIds);

  /**
   * 根据资源id删除客户端组信息.
   *
   * @param resourceId 资源id
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query(value = "delete from t_client_group tcg where tcg.group_id in "
      + "(select id from t_group where resource_id = :resourceId)", nativeQuery = true)
  void deleteClientByResourceId(@Param("resourceId") String resourceId);

  /**
   * 根据资源id列表删除客户端组信息.
   *
   * @param resourceIds 资源id列表
   */
  @Transactional(rollbackOn = SqlException.class)
  @Modifying
  @Query(value = "delete from t_client_group tcg where tcg.group_id in "
      + "(select id from t_group where resource_id in (:resourceIds))", nativeQuery = true)
  void deleteClientByResourceIdIn(@Param("resourceIds") List<String> resourceIds);

}
