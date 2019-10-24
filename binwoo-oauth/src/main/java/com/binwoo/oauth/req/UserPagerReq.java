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

  @ApiModelProperty(value = "关键词，模糊查询：用户名、昵称、手机号，可选")
  private String keyword;
  @ApiModelProperty(value = "是否已激活，可选")
  private Boolean active;
  @ApiModelProperty(value = "是否被禁用，可选")
  private Boolean disable;
  @ApiModelProperty(value = "是否被删除，可选")
  private Boolean deleted;
  @ApiModelProperty(value = "类别：如交警用户、企业用户等，可选")
  private String category;
  @ApiModelProperty(value = "类型：如管理员、普通用户等，可选")
  private String type;
  @ApiModelProperty(value = "来源：如组织机构、企业等，可选")
  private String sourceId;

}
