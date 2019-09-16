package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Authority;
import java.util.List;
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

  @Query(value = SQL_USER_ROLE + "AND ta.code IS NULL AND ta.type IS NULL",
      nativeQuery = true)
  List<String> selectUserRoleByApp(@Param("username") String username);

  @Query(value = SQL_USER_ROLE + "AND ta.code = :code AND ta.type = :type",
      nativeQuery = true)
  List<String> selectUserRoleByApp(@Param("username") String username, @Param("code") String code,
      @Param("type") String type);

  @Query(value = SQL_USER_ROLE + "AND ta.code = :code AND ta.type IS NULL",
      nativeQuery = true)
  List<String> selectUserRoleByAppCode(@Param("username") String username,
      @Param("code") String code);

  @Query(value = SQL_USER_ROLE + "AND ta.code IS NULL AND ta.type = :type",
      nativeQuery = true)
  List<String> selectUserRoleByAppType(@Param("username") String username,
      @Param("type") String type);

  @Query(value = SQL_CLIENT_ROLE + "AND ta.code IS NULL AND ta.type IS NULL",
      nativeQuery = true)
  List<String> selectClientRoleByApp(@Param("clientId") String clientId);

  @Query(value = SQL_CLIENT_ROLE + "AND ta.code = :code AND ta.type = :type",
      nativeQuery = true)
  List<String> selectClientRoleByApp(@Param("clientId") String clientId, @Param("code") String code,
      @Param("type") String type);

  @Query(value = SQL_CLIENT_ROLE + "AND ta.code = :code AND ta.type IS NULL",
      nativeQuery = true)
  List<String> selectClientRoleByAppCode(@Param("clientId") String clientId,
      @Param("code") String code);

  @Query(value = SQL_CLIENT_ROLE + "AND ta.code IS NULL AND ta.type = :type",
      nativeQuery = true)
  List<String> selectClientRoleByAppType(@Param("clientId") String clientId,
      @Param("type") String type);

}
