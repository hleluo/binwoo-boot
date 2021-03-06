package com.binwoo.oauth.repository;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.util.CollectionUtils;

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
   * 查询所有数据.
   *
   * @param sql SQL
   * @param params 参数，可为空
   * @param cls 类
   * @param <E> 泛型
   * @return 数据集
   */
  default <E> List<E> queryAllBySql(EntityManager manager, String sql, Map<String, Object> params,
      Class<E> cls) {
    Query query = manager.createNativeQuery(sql, cls);
    setParameters(query, params);
    return query.getResultList();
  }

  /**
   * 分页查询数据.
   *
   * @param sql SQL
   * @param sqlCount 统计SQL
   * @param pageOffset 页索引
   * @param pageSize 每页大小
   * @param params 参数，可为空
   * @param cls 类
   * @param <E> 泛型
   * @return 数据集
   */
  default <E> Page<E> queryPagerBySql(EntityManager manager, String sql, String sqlCount,
      int pageOffset, int pageSize, Map<String, Object> params, Class<E> cls) {
    pageOffset = pageOffset < 0 ? 0 : pageOffset;
    pageSize = pageSize < 1 ? 10 : pageSize;
    Query query = manager.createNativeQuery(sql, cls);
    query.setFirstResult(pageOffset * pageSize);
    query.setMaxResults(pageSize);
    setParameters(query, params);
    List<E> sources = query.getResultList();
    Query queryCount = manager.createNativeQuery(sqlCount);
    setParameters(queryCount, params);
    Object count = queryCount.getSingleResult();
    long total = count == null ? 0 : Long.parseLong(count.toString());
    Pageable pageable = PageRequest.of(pageOffset, pageSize);
    return new PageImpl<E>(sources, pageable, total);
  }

  /**
   * 设置参数值.
   *
   * @param query 查询
   * @param params 参数
   */
  default void setParameters(Query query, Map<String, Object> params) {
    if (CollectionUtils.isEmpty(params)) {
      return;
    }
    for (String key : params.keySet()) {
      query.setParameter(key, params.get(key));
    }
  }

  /**
   * 批量更新.
   *
   * @param sql SQL,参数用?表示
   * @param parameters 参数列表，索引从1开始
   */
  default void batch(EntityManager manager, String sql, List<Map<Integer, Object>> parameters) {
    Session session = manager.unwrap(Session.class);
    session.doWork(connection -> {
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        int i = 1;
        for (Map<Integer, Object> params : parameters) {
          for (Integer key : params.keySet()) {
            Object value = params.get(key);
            if (value instanceof Integer) {
              statement.setInt(key, (Integer) value);
            } else if (value instanceof Long) {
              statement.setLong(key, (Long) value);
            } else if (value instanceof Float) {
              statement.setFloat(key, (Float) value);
            } else if (value instanceof Double) {
              statement.setDouble(key, (Double) value);
            } else if (value instanceof Date) {
              statement.setTimestamp(key, (java.sql.Timestamp) value);
            } else if (value instanceof String) {
              statement.setString(key, (String) value);
            } else if (value instanceof Boolean) {
              statement.setBoolean(key, (Boolean) value);
            }
          }
          statement.addBatch();
          if (i % 20 == 0) {
            statement.executeBatch();
          }
          i++;
        }
        statement.executeBatch();
      }
    });
  }

  /**
   * 构建索引参数，索引从1开始.
   *
   * @param objects 值列表
   * @return 参数列表
   */
  default Map<Integer, Object> buildParams(Object... objects) {
    Map<Integer, Object> params = new HashMap<>();
    for (int i = 0; i < objects.length; i++) {
      params.put(i + 1, objects[i]);
    }
    return params;
  }

}
