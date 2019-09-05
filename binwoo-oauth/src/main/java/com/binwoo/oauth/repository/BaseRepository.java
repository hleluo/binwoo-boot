package com.binwoo.oauth.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 基础仓库.
 *
 * @author admin
 * @date 2019/9/5 16:57
 */
@NoRepositoryBean
public interface BaseRepository<T> extends PagingAndSortingRepository<T, String>,
    JpaSpecificationExecutor<T> {

}
