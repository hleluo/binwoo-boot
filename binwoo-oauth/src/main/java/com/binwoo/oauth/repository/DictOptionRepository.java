package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.DictOption;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 字典选项仓库.
 *
 * @author admin
 * @date 2019/9/16 15:19
 */
@Repository
public interface DictOptionRepository extends BaseRepository<DictOption> {

  /**
   * 根据标识查询字典选项信息.
   *
   * @param code 标识
   * @return 字典信息
   */
  DictOption findByCode(String code);

  /**
   * 根据id删除字典选项信息.
   *
   * @param id id
   */
  @Modifying
  @Query("delete from DictOption d where d.id = :id")
  void deleteById(@Param("id") String id);

  /**
   * 根据id列表删除字典选项信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query("delete from DictOption d where d.id in (:ids)")
  void deleteByIdIn(@Param("ids") List<String> ids);

  /**
   * 根据类型id删除字典选项信息.
   *
   * @param typeId 类型id
   */
  @Modifying
  @Query("delete from DictOption d where d.typeId = :typeId")
  void deleteByTypeId(@Param("typeId") String typeId);

  /**
   * 根据类型id列表删除字典选项信息.
   *
   * @param typeIds 类型id列表
   */
  @Modifying
  @Query("delete from DictOption d where d.typeId in (:typeIds)")
  void deleteByTypeIdIn(@Param("typeIds") List<String> typeIds);

}
