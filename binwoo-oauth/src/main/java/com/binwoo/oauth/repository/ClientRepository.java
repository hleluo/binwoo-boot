package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

/**
 * 客户端仓库.
 *
 * @author admin
 * @date 2019/9/5 16:56
 */
@Repository
public interface ClientRepository extends BaseRepository<Client> {

  /**
   * 根据客户端id查询客户端信息.
   *
   * @param code 客户端标识
   * @return 客户端信息.
   */
  Client findByCode(String code);

  /**
   * 根据ID更新删除状态.
   *
   * @param id id
   */
  @Modifying
  @Query("update Client c set c.deleted = true where id = :id")
  void updateDeletedById(@Param("id") String id);

  /**
   * 根据ID列表更新删除状态.
   *
   * @param ids id列表
   */
  @Modifying
  @Query("update Client c set c.deleted = true where id in (:ids)")
  void updateDeletedByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除客户端权职.
   *
   * @param id id
   */
  @Modifying
  @Query(value = "delete from t_client_authority tca where client_id = :id", nativeQuery = true)
  void deleteAuthorityById(@Param("id") String id);

  /**
   * 根据id列表删除客户端权职.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_client_authority tca where client_id in (:ids)", nativeQuery = true)
  void deleteAuthorityByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除客户端组.
   *
   * @param id id
   */
  @Modifying
  @Query(value = "delete from t_client_group tcg where client_id = :id", nativeQuery = true)
  void deleteGroupById(@Param("id") String id);

  /**
   * 根据id列表删除客户端组.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_client_group tcg where client_id in (:ids)", nativeQuery = true)
  void deleteGroupByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除客户端资源.
   *
   * @param id id
   */
  @Modifying
  @Query(value = "delete from t_client_resource tcr where client_id = :id", nativeQuery = true)
  void deleteResourceById(@Param("id") String id);

  /**
   * 根据id列表删除客户端资源.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_client_resource tcr where client_id in (:ids)", nativeQuery = true)
  void deleteResourceByIdIn(@Param("ids") List<String> ids);

  /**
   * 更新客户端权职列表.
   *
   * @param manager 管理器
   * @param id 客户端id
   * @param authorityIds 权职id列表
   */
  default void updateAuthorities(EntityManager manager, String id, Set<String> authorityIds) {
    deleteAuthorityById(id);
    if (!CollectionUtils.isEmpty(authorityIds)) {
      String sql = "INSERT INTO t_client_authority (client_id,authority_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String authorityId : authorityIds) {
        parameters.add(buildParams(id, authorityId));
      }
      batch(manager, sql, parameters);
    }
  }

  /**
   * 更新客户端组列表.
   *
   * @param manager 管理器
   * @param id 客户端id
   * @param groupIds 组id列表
   */
  default void updateGroups(EntityManager manager, String id, Set<String> groupIds) {
    deleteGroupById(id);
    if (!CollectionUtils.isEmpty(groupIds)) {
      String sql = "INSERT INTO t_client_group (client_id,group_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String groupId : groupIds) {
        parameters.add(buildParams(id, groupId));
      }
      batch(manager, sql, parameters);
    }
  }

  /**
   * 更新客户端资源列表.
   *
   * @param manager 管理器
   * @param id 客户端id
   * @param resourceIds 资源id列表
   */
  default void updateResources(EntityManager manager, String id, Set<String> resourceIds) {
    deleteResourceById(id);
    if (!CollectionUtils.isEmpty(resourceIds)) {
      String sql = "INSERT INTO t_client_resource (client_id,resource_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String resourceId : resourceIds) {
        parameters.add(buildParams(id, resourceId));
      }
      batch(manager, sql, parameters);
    }
  }

}
