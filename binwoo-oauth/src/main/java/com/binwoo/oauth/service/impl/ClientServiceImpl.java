package com.binwoo.oauth.service.impl;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.Client;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.exception.SqlException;
import com.binwoo.oauth.repository.ClientRepository;
import com.binwoo.oauth.repository.SqlRepository;
import com.binwoo.oauth.req.ClientPagerReq;
import com.binwoo.oauth.service.ClientService;
import com.binwoo.oauth.util.PageListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 客户端服务实现.
 *
 * @author admin
 * @date 2019/9/18 11:41
 */
@Service
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;
  private final SqlRepository sqlRepository;

  @Autowired
  public ClientServiceImpl(ClientRepository clientRepository,
      SqlRepository sqlRepository) {
    this.clientRepository = clientRepository;
    this.sqlRepository = sqlRepository;
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public Client save(Client client) throws HttpException {
    Client source = clientRepository.findByCode(client.getCode());
    if (source != null && !source.getId().equals(client.getId())) {
      throw new HttpException(HttpAuthExceptionCode.CLIENT_EXIST);
    }
    return clientRepository.save(client);
  }

  @Override
  public PageList<Client> getByPager(ClientPagerReq req) {
    Page<Client> page = clientRepository.findAll(new Specification<Client>() {
      @Override
      public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return PageListUtils.convert(page);
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public boolean delete(String id) {
    if (id != null) {
      clientRepository.updateDeletedById(id);
      clientRepository.deleteResourceById(id);
      clientRepository.deleteGroupById(id);
      clientRepository.deleteAuthorityById(id);
    }
    return true;
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      clientRepository.updateDeletedByIdIn(ids);
      clientRepository.deleteResourceByIdIn(ids);
      clientRepository.deleteGroupByIdIn(ids);
      clientRepository.deleteAuthorityByIdIn(ids);
    }
    return true;
  }

  @Override
  public Client getById(String id) {
    return clientRepository.findById(id).orElse(null);
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public boolean updateAuthorities(String id, Set<String> authorityIds) {
    clientRepository.deleteAuthorityById(id);
    if (!CollectionUtils.isEmpty(authorityIds)) {
      String sql = "INSERT INTO t_client_authority (client_id,authority_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String authorityId : authorityIds) {
        parameters.add(sqlRepository.buildParams(id, authorityId));
      }
      sqlRepository.batch(sql, parameters);
    }
    return true;
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public boolean updateGroups(String id, Set<String> groupIds) {
    clientRepository.deleteGroupById(id);
    if (!CollectionUtils.isEmpty(groupIds)) {
      String sql = "INSERT INTO t_client_group (client_id,group_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String groupId : groupIds) {
        parameters.add(sqlRepository.buildParams(id, groupId));
      }
      sqlRepository.batch(sql, parameters);
    }
    return true;
  }

  @Transactional(rollbackOn = SqlException.class)
  @Override
  public boolean updateResources(String id, Set<String> resourceIds) {
    clientRepository.deleteResourceById(id);
    if (!CollectionUtils.isEmpty(resourceIds)) {
      String sql = "INSERT INTO t_client_resource (client_id,resource_id) VALUES (?,?)";
      List<Map<Integer, Object>> parameters = new ArrayList<>();
      for (String resourceId : resourceIds) {
        parameters.add(sqlRepository.buildParams(id, resourceId));
      }
      sqlRepository.batch(sql, parameters);
    }
    return true;
  }
}
