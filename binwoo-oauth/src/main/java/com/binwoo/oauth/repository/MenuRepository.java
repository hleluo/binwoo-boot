package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 菜单仓库.
 *
 * @author admin
 * @date 2019/9/16 15:19
 */
@Repository
public interface MenuRepository extends BaseRepository<Menu> {

  /**
   * 根据id删除菜单信息.
   *
   * @param id id
   */
  @Override
  @Modifying
  @Query("delete from Menu m where m.id = :id")
  void deleteById(@Param("id") String id);

  /**
   * 根据id列表删除菜单信息.
   *
   * @param ids id列表
   */
  @Override
  @Modifying
  @Query("delete from Menu m where m.id in (:ids)")
  void deleteByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据id删除角色菜单信息.
   *
   * @param id id
   */
  @Modifying
  @Query(value = "delete from t_role_menu trm where menu_id = :id", nativeQuery = true)
  void deleteRoleById(@Param("id") String id);

  /**
   * 根据id列表删除角色菜单信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query(value = "delete from t_role_menu trm where menu_id in (:ids)", nativeQuery = true)
  void deleteRoleByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据应用id删除菜单信息.
   *
   * @param appId 应用id
   */
  @Modifying
  @Query("delete from Menu m where m.appId = :appId")
  void deleteByAppId(@Param("appId") String appId);

  /**
   * 根据应用id列表删除菜单信息.
   *
   * @param appIds 应用id列表
   */
  @Modifying
  @Query("delete from Menu m where m.appId in (:appIds)")
  void deleteByAppIdIn(@Param("appIds") List<String> appIds);

}
