package com.binwoo.oauth.repository;

import com.binwoo.oauth.entity.Server;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 服务仓库.
 *
 * @author admin
 * @date 2019/9/5 16:56
 */
@Repository
public interface ServerRepository extends BaseRepository<Server> {

  @Query(value = "SELECT DISTINCT tc.code FROM t_client tc "
      + "RIGHT JOIN t_client_server tcs ON tc.id = tcs.client_id "
      + "LEFT JOIN t_server ts ON tcs.server_id = ts.id "
      + "WHERE tc.code = :clientId", nativeQuery = true)
  List<String> selectClientResourceIds(@Param("clientId") String clientId);

}
