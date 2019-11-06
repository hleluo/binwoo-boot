package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.DictType;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 字典类型仓库.
 *
 * @author admin
 * @date 2019/9/16 15:19
 */
@Repository
public interface DictTypeRepository extends BaseRepository<DictType> {

  /**
   * 根据id列表获取字典类型列表.
   *
   * @param ids id列表
   * @return 字典类型列表
   */
  List<DictType> findByIdIn(List<String> ids);

  /**
   * 根据标识查询字典类型信息.
   *
   * @param code 标识
   * @return 字典信息
   */
  DictType findByCode(String code);

  /**
   * 根据父节点获取子节点列表.
   *
   * @param parentId 父节点id
   * @return 子节点列表
   */
  List<DictType> findByParentId(String parentId);

  /**
   * 根据父节点获取子节点列表.
   *
   * @param parentIds 父节点id列表
   * @return 子节点列表
   */
  List<DictType> findByParentIdIn(List<String> parentIds);

  /**
   * 根据id删除字典类型信息.
   *
   * @param id id
   */
  @Override
  @Modifying
  @Query("delete from DictType d where d.id = :id")
  void deleteById(@Param("id") String id);

  /**
   * 根据id列表删除字典类型信息.
   *
   * @param ids id列表
   */
  @Modifying
  @Query("delete from DictType d where d.id in (:ids)")
  void deleteByIdIn(@Param("ids") List<String> ids);

}
