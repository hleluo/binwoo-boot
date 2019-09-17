package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Set;
import lombok.Data;

/**
 * 用户角色设置参数.
 *
 * @author admin
 * @date 2019/9/16 17:33
 */
@ApiModel(value = "用户角色设置参数")
@Data
public class UserRoleReq implements Serializable {

  @ApiModelProperty(value = "角色id列表")
  private Set<String> roleIds;

}
