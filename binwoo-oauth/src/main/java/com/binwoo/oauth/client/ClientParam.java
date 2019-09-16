package com.binwoo.oauth.client;

import com.binwoo.oauth.entity.Client;
import java.util.List;
import lombok.Data;

/**
 * 客户端参数信息.
 *
 * @author hleluo
 * @date 2019/9/12 21:45
 */
@Data
public class ClientParam {

  private String clientId;
  private Client client;
  private List<String> resourceIds;
  private List<String> authorities;

}
