package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Resource;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 服务仓库.
 *
 * @author admin
 * @date 2019/9/5 16:56
 */
@Repository
public interface ResourceRepository extends BaseRepository<Resource> {

  /**
   * 根据客户端标识查询资源标识列表.
   *
   * @param clientId 客户端标识
   * @return 资源标识列表
   */
  @Query(value = "SELECT DISTINCT tc.code FROM t_client tc "
      + "RIGHT JOIN t_client_resource tcr ON tc.id = tcr.client_id "
      + "LEFT JOIN t_resource tr ON tcr.resource_id = tr.id "
      + "WHERE tc.code = :clientId", nativeQuery = true)
  List<String> selectClientResourceIds(@Param("clientId") String clientId);

  /**
   * 根据标识查询资源信息.
   *
   * @param code 标识
   * @return 资源信息
   */
  Resource findByCode(String code);

  /**
   * 根据id删除资源信息.
   *
   * @param id id
   */
  @Override
  @Modifying
  @Query("delete from Resource r where r.id = :id")
  void deleteById(@Param("id") String id);

  /**
   * 根据资源id列表删除资源信息.
   *
   * @param ids id列表
   */
  @Override
  @Modifying
  @Query("delete from Resource r where r.id in (:ids)")
  void deleteByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除客户端资源信息.
   *
   * @param id id
   */
  @Modifying
  @Query(value = "delete from t_client_resource tcr where resource_id = :id", nativeQuery = true)
  void deleteClientById(@Param("id") String id);

  /**
   * 根据id列表删除客户端资源信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_client_resource tcr where resource_id = (:ids)", nativeQuery = true)
  void deleteClientByIdIn(@Param("ids") List<String> ids);

}
