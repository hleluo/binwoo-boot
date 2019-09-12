package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 组仓库.
 *
 * @author admin
 * @date 2019/9/5 16:56
 */
@Repository
public interface RoleRepository extends BaseRepository<Role> {

  /**
   * 获取用户OAuth角色列表SQL.
   */
  String SQL_USER_ROLE = "SELECT DISTINCT tr.code FROM t_user tu "
      + "RIGHT JOIN t_user_role tur ON tu.id = tur.user_id "
      + "LEFT JOIN t_role tr ON tur.role_id = tr.id "
      + "LEFT JOIN t_domain td ON tr.domain_id = td.id "
      + "LEFT JOIN t_platform tp ON tr.platform_id = tp.id "
      + "WHERE tu.username = :username ";

  /**
   * 获取客户端OAuth角色列表SQL.
   */
  String SQL_CLIENT_ROLE = "SELECT DISTINCT tr.code FROM t_client tc "
      + "RIGHT JOIN t_client_role tcr ON tc.id = tcr.client_id "
      + "LEFT JOIN t_role tr ON tcr.role_id = tr.id "
      + "LEFT JOIN t_domain td ON tr.domain_id = td.id "
      + "LEFT JOIN t_platform tp ON tr.platform_id = tp.id "
      + "WHERE tc.code = :clientId ";

  @Query(value = SQL_USER_ROLE + "AND td.code IS NULL AND tp.code IS NULL",
      nativeQuery = true)
  List<String> selectUserRole(@Param("username") String username);

  @Query(value = SQL_USER_ROLE + "AND td.code = :domain AND tp.code = :platform",
      nativeQuery = true)
  List<String> selectUserRole(@Param("username") String username, @Param("domain") String domain,
      @Param("platform") String platform);

  @Query(value = SQL_USER_ROLE + "AND td.code = :domain AND tp.code IS NULL",
      nativeQuery = true)
  List<String> selectUserRoleByDomain(@Param("username") String username,
      @Param("domain") String domain);

  @Query(value = SQL_USER_ROLE + "AND td.code IS NULL AND tp.code = :platform",
      nativeQuery = true)
  List<String> selectUserRoleByPlatform(@Param("username") String username,
      @Param("platform") String platform);

  @Query(value = SQL_CLIENT_ROLE + "AND td.code IS NULL AND tp.code IS NULL",
      nativeQuery = true)
  List<String> selectClientRole(@Param("clientId") String clientId);

  @Query(value = SQL_CLIENT_ROLE + "AND td.code = :domain AND tp.code = :platform",
      nativeQuery = true)
  List<String> selectClientRole(@Param("clientId") String clientId, @Param("domain") String domain,
      @Param("platform") String platform);

  @Query(value = SQL_CLIENT_ROLE + "AND td.code = :domain AND tp.code IS NULL",
      nativeQuery = true)
  List<String> selectClientRoleByDomain(@Param("clientId") String clientId,
      @Param("domain") String domain);

  @Query(value = SQL_CLIENT_ROLE + "AND td.code IS NULL AND tp.code = :platform",
      nativeQuery = true)
  List<String> selectClientRoleByPlatform(@Param("clientId") String clientId,
      @Param("platform") String platform);

}
