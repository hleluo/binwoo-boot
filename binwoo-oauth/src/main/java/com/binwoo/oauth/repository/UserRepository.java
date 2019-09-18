package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.exception.SqlException;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query("update User u set u.deleted = true where id = :id")
  void updateDeletedById(@Param("id") String id);

  /**
   * 根据ID列表更新删除状态.
   *
   * @param ids id列表
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query("update User u set u.deleted = true where id in (:ids)")
  void updateDeletedByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除用户角色.
   *
   * @param id 用户id
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_user_role tur where user_id = :id", nativeQuery = true)
  void deleteRoleById(@Param("id") String id);

  /**
   * 根据id列表删除用户角色.
   *
   * @param ids id列表
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_user_role tur where user_id in (:ids)", nativeQuery = true)
  void deleteRoleByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据用户id删除用户权职.
   *
   * @param id 用户id
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_user_authority tua where user_id = :id", nativeQuery = true)
  void deleteAuthorityById(@Param("id") String id);

  /**
   * 根据用户id列表删除用户权职.
   *
   * @param ids id列表
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_user_authority tua where user_id in (:ids)", nativeQuery = true)
  void deleteAuthorityByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据用户id删除用户组.
   *
   * @param id 用户id
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_user_group tug where user_id = :id", nativeQuery = true)
  void deleteGroupById(@Param("id") String id);

  /**
   * 根据用户id列表删除用户组.
   *
   * @param ids id列表
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query(value = "delete from t_user_group tug where user_id in (:ids)", nativeQuery = true)
  void deleteGroupByIdIn(@Param("ids") List<String> ids);

}
