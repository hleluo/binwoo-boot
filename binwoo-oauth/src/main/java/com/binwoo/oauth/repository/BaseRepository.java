package com.binwoo.oauth.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * 基础仓库.
 *
 * @author admin
 * @date 2019/9/5 16:57
 */
@NoRepositoryBean
public interface BaseRepository<T> extends PagingAndSortingRepository<T, String>,
    JpaSpecificationExecutor<T> {

  /**
   * 根据id删除数据.
   *
   * @param id id
   */
  @Override
  @Modifying
  @Query("delete from T t where t.id = :id")
  void deleteById(@Param("id") String id);

  /**
   * 根据id列表删除数据.
   *
   * @param ids id列表
   */
  @Modifying
  @Query("delete from T t where t.id in (ids)")
  void deleteByIdIn(@Param("ids") List<String> ids);

}
