package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户注册参数.
 *
 * @author admin
 * @date 2019/9/16 17:33
 */
@ApiModel(value = "用户注册参数")
@Data
public class UserRegisterReq implements Serializable {

  @ApiModelProperty(value = "用户名", required = true)
  private String username;
  @ApiModelProperty(value = "昵称，可选，为空时为用户名")
  private String nickname;
  @ApiModelProperty(value = "手机号，可选")
  private String mobile;
  @ApiModelProperty(value = "邮箱，可选")
  private String email;
  @ApiModelProperty(value = "密码", required = true)
  private String password;
  @ApiModelProperty(value = "类别：如交警用户、企业用户等，可选")
  private String category;
  @ApiModelProperty(value = "类型：如管理员、普通用户等，可选")
  private String type;
  @ApiModelProperty(value = "来源：如组织机构、企业等，可选")
  private String sourceId;
  @ApiModelProperty(value = "是否已激活，可选，为空时为未激活")
  private Boolean active = false;
  @ApiModelProperty(value = "头像，可选")
  private String thumbnail;
}
