package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Set;
import lombok.Data;

/**
 * 客户端权职设置参数.
 *
 * @author admin
 * @date 2019/9/16 17:33
 */
@ApiModel(value = "客户端权职设置参数")
@Data
public class ClientAuthorityReq implements Serializable {

  @ApiModelProperty(value = "权职id列表")
  private Set<String> authorityIds;

}
