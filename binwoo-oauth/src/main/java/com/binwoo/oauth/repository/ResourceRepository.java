package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Resource;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 资源仓库.
 *
 * @author admin
 * @date 2019/9/5 16:56
 */
@Repository
public interface ResourceRepository extends BaseRepository<Resource> {

  @Query(value = "SELECT DISTINCT tr.code FROM t_client tc "
      + "RIGHT JOIN t_client_resource tcr ON tc.id = tcr.client_id "
      + "LEFT JOIN t_resource tr ON tcr.resource_id = tr.id "
      + "WHERE tc.code = :clientId", nativeQuery = true)
  List<String> selectClientResourceIds(@Param("clientId") String clientId);

}
