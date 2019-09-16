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
   * 根据ID删除信息.
   *
   * @param id id
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query("delete from User where id = :id")
  @Override
  void deleteById(@Param("id") String id);

  /**
   * 根据ID列表删除信息.
   *
   * @param ids id列表
   */
  @Transactional(rollbackFor = SqlException.class)
  @Modifying
  @Query("delete from User where id in (:ids)")
  void deleteByIdIn(@Param("ids") List<String> ids);

}
