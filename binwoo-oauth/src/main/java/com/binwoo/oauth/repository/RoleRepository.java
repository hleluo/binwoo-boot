package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 角色仓库.
 *
 * @author admin
 * @date 2019/9/16 15:19
 */
@Repository
public interface RoleRepository extends BaseRepository<Role> {

  /**
   * 根据id删除角色信息.
   *
   * @param id id
   */
  @Modifying
  @Query("delete from Role r where r.id = :id")
  void deleteById(@Param("id") String id);

  /**
   * 根据id列表删除角色信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query("delete from Role r where r.id in (:ids)")
  void deleteByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除用户角色信息.
   *
   * @param id id
   */
  @Modifying
  @Query(value = "delete from t_user_role tur where role_id = :id", nativeQuery = true)
  void deleteUserById(@Param("id") String id);

  /**
   * 根据id列表删除用户角色信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_user_role tur where role_id in (:ids)", nativeQuery = true)
  void deleteUserByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除角色菜单信息.
   *
   * @param id id
   */
  @Modifying
  @Query(value = "delete from t_role_menu trm where role_id = :id", nativeQuery = true)
  void deleteMenuById(@Param("id") String id);

  /**
   * 根据id列表删除角色菜单信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_role_menu trm where role_id in (:ids)", nativeQuery = true)
  void deleteMenuByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据应用id删除角色信息.
   *
   * @param appId 应用id
   */
  @Modifying
  @Query("delete from Role r where r.appId = :appId")
  void deleteByAppId(@Param("appId") String appId);

  /**
   * 根据应用id列表删除角色信息.
   *
   * @param appIds 应用id列表
   */
  @Modifying
  @Query("delete from Role r where r.appId in (:appIds)")
  void deleteByAppIdIn(@Param("appIds") List<String> appIds);

  /**
   * 根据应用id删除用户角色信息.
   *
   * @param appId 应用id
   */
  @Modifying
  @Query(value = "delete from t_user_role tur where tur.role_id in "
      + "(select id from t_role where app_id = :appId)", nativeQuery = true)
  void deleteUserByAppId(@Param("appId") String appId);

  /**
   * 根据应用id列表删除用户角色信息.
   *
   * @param appIds 应用id列表
   */
  @Modifying
  @Query(value = "delete from t_user_role tur where tur.role_id in "
      + "(select id from t_role where app_id in (:appIds))", nativeQuery = true)
  void deleteUserByAppIdIn(@Param("appIds") List<String> appIds);

  /**
   * 根据应用id列表删除角色菜单信息.
   *
   * @param appId 应用id
   */
  @Modifying
  @Query(value = "delete from t_role_menu trm where trm.role_id in "
      + "(select id from t_role where app_id = :appId)", nativeQuery = true)
  void deleteMenuByAppId(@Param("appId") String appId);

  /**
   * 根据应用id列表删除角色菜单信息.
   *
   * @param appIds 应用id列表
   */
  @Modifying
  @Query(value = "delete from t_role_menu trm where trm.role_id in "
      + "(select id from t_role where app_id in (:appIds))", nativeQuery = true)
  void deleteMenuByAppIdIn(@Param("appIds") List<String> appIds);

}
