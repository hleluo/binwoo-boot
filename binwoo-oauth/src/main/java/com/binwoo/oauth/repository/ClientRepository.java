package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Client;
import com.binwoo.oauth.exception.SqlException;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query("update Client c set c.deleted = true where id = :id")
  void updateDeletedById(@Param("id") String id);

  /**
   * 根据ID列表更新删除状态.
   *
   * @param ids id列表
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query("update Client c set c.deleted = true where id in (:ids)")
  void updateDeletedByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除客户端权职.
   *
   * @param id id
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_client_authority tca where client_id = :id", nativeQuery = true)
  void deleteAuthorityById(@Param("id") String id);

  /**
   * 根据id列表删除客户端权职.
   *
   * @param ids id列表
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_client_authority tca where client_id in (:ids)", nativeQuery = true)
  void deleteAuthorityByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除客户端组.
   *
   * @param id id
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_client_group tcg where client_id = :id", nativeQuery = true)
  void deleteGroupById(@Param("id") String id);

  /**
   * 根据id列表删除客户端组.
   *
   * @param ids id列表
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_client_group tcg where client_id in (:ids)", nativeQuery = true)
  void deleteGroupByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除客户端资源.
   *
   * @param id id
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_client_resource tcr where client_id = :id", nativeQuery = true)
  void deleteResourceById(@Param("id") String id);

  /**
   * 根据id列表删除客户端资源.
   *
   * @param ids id列表
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_client_resource tcr where client_id in (:ids)", nativeQuery = true)
  void deleteResourceByIdIn(@Param("ids") List<String> ids);

}
