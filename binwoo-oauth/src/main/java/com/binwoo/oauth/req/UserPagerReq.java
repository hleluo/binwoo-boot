package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询参数.
 *
 * @author admin
 * @date 2019/9/16 17:33
 */
@ApiModel(value = "用户查询参数")
@EqualsAndHashCode(callSuper = false)
@Data
public class UserPagerReq extends BasePagerReq {

  @ApiModelProperty(value = "用户名")
  private String username;

}
