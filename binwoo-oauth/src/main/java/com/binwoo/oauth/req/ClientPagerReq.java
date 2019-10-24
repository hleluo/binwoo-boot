package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端查询参数.
 *
 * @author admin
 * @date 2019/9/16 17:33
 */
@ApiModel(value = "客户端查询参数")
@EqualsAndHashCode(callSuper = false)
@Data
public class ClientPagerReq extends BasePagerReq {

  @ApiModelProperty(value = "模糊搜索：名称、标识")
  private String keyword;
  @ApiModelProperty(value = "是否被禁用，可选")
  private Boolean disable;
  @ApiModelProperty(value = "是否被删除，可选")
  private Boolean deleted;

}
