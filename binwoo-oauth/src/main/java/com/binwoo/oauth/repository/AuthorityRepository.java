package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Authority;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 职权仓库.
 *
 * @author admin
 * @date 2019/9/5 16:56
 */
@Repository
public interface AuthorityRepository extends BaseRepository<Authority> {

  /**
   * 获取用户OAuth职权列表SQL.
   */
  String SQL_USER_ROLE = "SELECT DISTINCT tau.code FROM t_user tu "
      + "RIGHT JOIN t_user_authority tua ON tu.id = tua.user_id "
      + "LEFT JOIN t_authority tau ON tua.authority_id = tau.id "
      + "LEFT JOIN t_app ta ON tau.app_id = ta.id "
      + "WHERE tu.username = :username ";

  /**
   * 获取客户端OAuth职权列表SQL.
   */
  String SQL_CLIENT_ROLE = "SELECT DISTINCT tau.code FROM t_client tc "
      + "RIGHT JOIN t_client_authority tca ON tc.id = tca.client_id "
      + "LEFT JOIN t_authority tau ON tca.authority_id = tau.id "
      + "LEFT JOIN t_app ta ON tau.app_id = ta.id "
      + "WHERE tc.code = :clientId ";

  /**
   * 查询用户权职.
   *
   * @param username 用户名
   * @return 权职标识列表
   */
  @Query(value = SQL_USER_ROLE + "AND ta.code IS NULL AND ta.type IS NULL",
      nativeQuery = true)
  List<String> selectUserRoleByApp(@Param("username") String username);

  /**
   * 查询用户权职.
   *
   * @param username 用户名
   * @param code APP标识
   * @param type APP类型
   * @return 权职标识列表
   */
  @Query(value = SQL_USER_ROLE + "AND ta.code = :code AND ta.type = :type",
      nativeQuery = true)
  List<String> selectUserRoleByApp(@Param("username") String username, @Param("code") String code,
      @Param("type") String type);

  /**
   * 查询用户权职.
   *
   * @param username 用户名
   * @param code APP标识
   * @return 权职标识列表
   */
  @Query(value = SQL_USER_ROLE + "AND ta.code = :code AND ta.type IS NULL",
      nativeQuery = true)
  List<String> selectUserRoleByAppCode(@Param("username") String username,
      @Param("code") String code);

  /**
   * 查询用户权职.
   *
   * @param username 用户名
   * @param type APP类型
   * @return 权职标识列表
   */
  @Query(value = SQL_USER_ROLE + "AND ta.code IS NULL AND ta.type = :type",
      nativeQuery = true)
  List<String> selectUserRoleByAppType(@Param("username") String username,
      @Param("type") String type);

  /**
   * 查询客户端权职.
   *
   * @param clientId 客户端标识
   * @return 权职标识列表
   */
  @Query(value = SQL_CLIENT_ROLE + "AND ta.code IS NULL AND ta.type IS NULL",
      nativeQuery = true)
  List<String> selectClientRoleByApp(@Param("clientId") String clientId);

  /**
   * 查询用户权职.
   *
   * @param clientId 客户端标识
   * @param code APP标识
   * @param type APP类型
   * @return 权职标识列表
   */
  @Query(value = SQL_CLIENT_ROLE + "AND ta.code = :code AND ta.type = :type",
      nativeQuery = true)
  List<String> selectClientRoleByApp(@Param("clientId") String clientId, @Param("code") String code,
      @Param("type") String type);

  /**
   * 查询用户权职.
   *
   * @param clientId 客户端标识
   * @param code APP标识
   * @return 权职标识列表
   */
  @Query(value = SQL_CLIENT_ROLE + "AND ta.code = :code AND ta.type IS NULL",
      nativeQuery = true)
  List<String> selectClientRoleByAppCode(@Param("clientId") String clientId,
      @Param("code") String code);

  /**
   * 查询用户权职.
   *
   * @param clientId 客户端标识
   * @param type APP类型
   * @return 权职标识列表
   */
  @Query(value = SQL_CLIENT_ROLE + "AND ta.code IS NULL AND ta.type = :type",
      nativeQuery = true)
  List<String> selectClientRoleByAppType(@Param("clientId") String clientId,
      @Param("type") String type);

  /**
   * 根据应用(NULL)和标识查询权职信息.
   *
   * @param code 标识
   * @return 权职信息
   */
  Authority findByAppIdIsNullAndCode(String code);

  /**
   * 根据应用和标识查询权职信息.
   *
   * @param appId 应用id
   * @param code 标识
   * @return 权职信息
   */
  Authority findByAppIdAndCode(String appId, String code);

  /**
   * 根据id删除权职信息.
   *
   * @param id id
   */
  @Override
  @Modifying
  @Query("delete from Authority a where a.id = :id")
  void deleteById(@Param("id") String id);

  /**
   * 根据id列表删除权职信息.
   *
   * @param ids id列表
   */
  @Override
  @Modifying
  @Query("delete from Authority a where a.id in (:ids)")
  void deleteByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除用户权职信息.
   *
   * @param id id
   */
  @Modifying
  @Query(value = "delete from t_user_authority where authority_id = :id", nativeQuery = true)
  void deleteUserById(@Param("id") String id);

  /**
   * 根据id列表删除用户权职信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_user_authority where authority_id in (:ids)", nativeQuery = true)
  void deleteUserByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除客户端权职信息.
   *
   * @param id id
   */
  @Modifying
  @Query(value = "delete from t_client_authority where authority_id = :id", nativeQuery = true)
  void deleteClientById(@Param("id") String id);

  /**
   * 根据id列表删除客户端权职信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_client_authority where authority_id in (:ids)", nativeQuery = true)
  void deleteClientByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据应用id删除权职信息.
   *
   * @param appId 应用id
   */
  @Modifying
  @Query("delete from Authority a where a.appId = :appId")
  void deleteByAppId(@Param("appId") String appId);

  /**
   * 根据应用id列表删除权职信息.
   *
   * @param appIds 应用id列表
   */
  @Modifying
  @Query("delete from Authority a where a.appId in (:appIds)")
  void deleteByAppIdIn(@Param("appIds") List<String> appIds);

  /**
   * 根据应用id删除用户权职信息.
   *
   * @param appId 应用id
   */
  @Modifying
  @Query(value = "delete from t_user_authority tua where tua.authority_id in "
      + "(select id from t_authority where app_id = :appId)", nativeQuery = true)
  void deleteUserByAppId(@Param("appId") String appId);

  /**
   * 根据应用id列表删除用户权职信息.
   *
   * @param appIds 应用id列表
   */
  @Modifying
  @Query(value = "delete from t_user_authority tua where tua.authority_id in "
      + "(select id from t_authority where app_id in (:appIds))", nativeQuery = true)
  void deleteUserByAppIdIn(@Param("appIds") List<String> appIds);

  /**
   * 根据应用id删除客户端权职信息.
   *
   * @param appId 应用id
   */
  @Modifying
  @Query(value = "delete from t_client_authority tca where tca.authority_id in "
      + "(select id from t_authority where app_id = :appId)", nativeQuery = true)
  void deleteClientByAppId(@Param("appId") String appId);

  /**
   * 根据应用id列表删除客户端权职信息.
   *
   * @param appIds 应用id列表
   */
  @Modifying
  @Query(value = "delete from t_client_authority tca where tca.authority_id in "
      + "(select id from t_authority where app_id in (:appIds))", nativeQuery = true)
  void deleteClientByAppIdIn(@Param("appIds") List<String> appIds);

}
