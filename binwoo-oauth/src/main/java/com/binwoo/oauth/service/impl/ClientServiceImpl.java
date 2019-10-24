package com.binwoo.oauth.service.impl;

import com.binwoo.common.http.exception.HttpException;
import com.binwoo.common.http.response.PageList;
import com.binwoo.oauth.entity.Client;
import com.binwoo.oauth.exception.HttpAuthExceptionCode;
import com.binwoo.oauth.repository.ClientRepository;
import com.binwoo.oauth.req.ClientPagerReq;
import com.binwoo.oauth.service.ClientService;
import com.binwoo.oauth.util.PageListUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 客户端服务实现.
 *
 * @author admin
 * @date 2019/9/18 11:41
 */
@Service
public class ClientServiceImpl implements ClientService {

  private final ClientRepository clientRepository;
  private final PasswordEncoder passwordEncoder;
  private final EntityManager manager;

  /**
   * 构造函数.
   *
   * @param clientRepository 客户端仓库
   * @param passwordEncoder 加密方式
   * @param manager 管理器
   */
  @Autowired
  public ClientServiceImpl(ClientRepository clientRepository,
      PasswordEncoder passwordEncoder, EntityManager manager) {
    this.clientRepository = clientRepository;
    this.passwordEncoder = passwordEncoder;
    this.manager = manager;
  }

  @Override
  public Client save(Client client) throws HttpException {
    Client source = clientRepository.findByCode(client.getCode());
    if (source != null && !source.getId().equals(client.getId())) {
      throw new HttpException(HttpAuthExceptionCode.CLIENT_EXIST);
    }
    if (StringUtils.isEmpty(client.getId())) {
      client.setSecret(passwordEncoder.encode(client.getSecret()));
    } else {
      //修改客户端信息，检测秘钥是否有修改.
      if (StringUtils.isEmpty(client.getSecret())) {
        if (source == null) {
          throw new HttpException(HttpAuthExceptionCode.CLIENT_SECRET_NULL);
        }
        client.setSecret(source.getSecret());
      } else {
        client.setSecret(passwordEncoder.encode(client.getSecret()));
      }
    }
    return clientRepository.save(client);
  }

  @Override
  public PageList<Client> getByPager(ClientPagerReq req) {
    Page<Client> page = clientRepository.findAll(new Specification<Client>() {
      @Override
      public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(req.getKeyword())) {
          String keyword = String.format("%%%s%%", req.getKeyword());
          predicates.add(criteriaBuilder.or(
              criteriaBuilder.like(root.get("name").as(String.class), keyword),
              criteriaBuilder.like(root.get("code").as(String.class), keyword)));
        }
        if (req.getDisable() != null) {
          predicates.add(criteriaBuilder.equal(root.get("disable").as(Boolean.class),
              req.getDisable()));
        }
        if (req.getDeleted() != null) {
          predicates.add(criteriaBuilder.equal(root.get("deleted").as(Boolean.class),
              req.getDeleted()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        return criteriaQuery.getRestriction();
      }
    }, req.getPageRequest());
    return PageListUtils.convert(page);
  }

  @Override
  public boolean delete(String id) {
    if (!StringUtils.isEmpty(id)) {
      clientRepository.deleteResourceById(id);
      clientRepository.deleteGroupById(id);
      clientRepository.deleteAuthorityById(id);
      clientRepository.updateDeletedById(id);
    }
    return true;
  }

  @Override
  public boolean delete(List<String> ids) {
    if (!CollectionUtils.isEmpty(ids)) {
      clientRepository.deleteResourceByIdIn(ids);
      clientRepository.deleteGroupByIdIn(ids);
      clientRepository.deleteAuthorityByIdIn(ids);
      clientRepository.updateDeletedByIdIn(ids);
    }
    return true;
  }

  @Override
  public Client getById(String id) {
    return clientRepository.findById(id).orElse(null);
  }

  @Override
  public boolean updateAuthorities(String id, Set<String> authorityIds) {
    clientRepository.updateAuthorities(manager, id, authorityIds);
    return true;
  }

  @Override
  public boolean updateGroups(String id, Set<String> groupIds) {
    clientRepository.updateGroups(manager, id, groupIds);
    return true;
  }

  @Override
  public boolean updateResources(String id, Set<String> resourceIds) {
    clientRepository.updateResources(manager, id, resourceIds);
    return true;
  }
}
