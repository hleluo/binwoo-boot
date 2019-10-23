package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.User;
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
 * 用户仓库.
 *
 * @author admin
 * @date 2019/9/5 16:56
 */
@Repository
public interface UserRepository extends BaseRepository<User> {

  /**
   * 根据用户名查询用户信息.
   *
   * @param username 用户名
   * @return 用户信息.
   */
  User findByUsername(String username);

  /**
   * 通过id或用户名查询用户信息.
   *
   * @param id id
   * @param username 用户名
   * @return 用户信息
   */
  User findFirstByIdOrUsername(String id, String username);

  /**
   * 根据ID更新删除状态.
   *
   * @param id id
   */
  @Modifying
  @Query("update User u set u.deleted = true where id = :id")
  void updateDeletedById(@Param("id") String id);

  /**
   * 根据ID列表更新删除状态.
   *
   * @param ids id列表
   */
  @Modifying
  @Query("update User u set u.deleted = true where id in (:ids)")
  void updateDeletedByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除用户角色.
   *
   * @param id 用户id
   */

  @Modifying
  @Query(value = "delete from t_user_role tur where user_id = :id", nativeQuery = true)
  void deleteRoleById(@Param("id") String id);

  /**
   * 根据id列表删除用户角色.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_user_role tur where user_id in (:ids)", nativeQuery = true)
  void deleteRoleByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据用户id删除用户权职.
   *
   * @param id 用户id
   */
  @Modifying
  @Query(value = "delete from t_user_authority tua where user_id = :id", nativeQuery = true)
  void deleteAuthorityById(@Param("id") String id);

  /**
   * 根据用户id列表删除用户权职.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_user_authority tua where user_id in (:ids)", nativeQuery = true)
  void deleteAuthorityByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据用户id删除用户组.
   *
   * @param id 用户id
   */
  @Modifying
  @Query(value = "delete from t_user_group tug where user_id = :id", nativeQuery = true)
  void deleteGroupById(@Param("id") String id);

  /**
   * 根据用户id列表删除用户组.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_user_group tug where user_id in (:ids)", nativeQuery = true)
  void deleteGroupByIdIn(@Param("ids") List<String> ids);

  /**
   * 更新用户角色列表.
   *
   * @param manager 管理器
   * @param id 用户id
   * @param roleIds 角色id列表
   */
  default void updateRoles(EntityManager manager, String id, Set<String> roleIds) {
    deleteRoleById(id);
    if (!CollectionUtils.isEmpty(roleIds)) {
      String sql = "INSERT INTO t_user_role (user_id,role_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String roleId : roleIds) {
        parameters.add(buildParams(id, roleId));
      }
      batch(manager, sql, parameters);
    }
  }

  /**
   * 更新用户权职列表.
   *
   * @param manager 管理器
   * @param id 用户id
   * @param authorityIds 权职id列表
   */
  default void updateAuthorities(EntityManager manager, String id, Set<String> authorityIds) {
    deleteAuthorityById(id);
    if (!CollectionUtils.isEmpty(authorityIds)) {
      String sql = "INSERT INTO t_user_authority (user_id,authority_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String authorityId : authorityIds) {
        parameters.add(buildParams(id, authorityId));
      }
      batch(manager, sql, parameters);
    }
  }

  /**
   * 更新用户组列表.
   *
   * @param manager 管理器
   * @param id 用户id
   * @param groupIds 组id列表
   */
  default void updateGroups(EntityManager manager, String id, Set<String> groupIds) {
    deleteGroupById(id);
    if (!CollectionUtils.isEmpty(groupIds)) {
      String sql = "INSERT INTO t_user_group (user_id,group_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String groupId : groupIds) {
        parameters.add(buildParams(id, groupId));
      }
      batch(manager, sql, parameters);
    }
  }

}
