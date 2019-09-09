package com.binwoo.oauth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 客户端信息.
 *
 * @author hleluo
 * @date 2019/9/3 21:14
 */
@Data
public class Client {

  @Id
  @GenericGenerator(name = "uid", strategy = "uuid2")
  @GeneratedValue(generator = "uid")
  private String id;
  @ApiModelProperty(value = "clientId")
  private String code;
  @ApiModelProperty(value = "秘钥")
  private String secret;
  @ApiModelProperty(value = "认证类型：authorization_code,password,"
      + "client_credentials,refresh_token，逗号隔开")
  private String grantType;
  @ApiModelProperty(value = "作用范围，逗号隔开")
  private String scope;
  @ApiModelProperty(value = "授权资源，逗号隔开")
  private String resource;
  @ApiModelProperty(value = "AccessToken过期时间，秒，默认7200s")
  private Integer accessTokenExpire = 2 * 60 * 60;
  @ApiModelProperty(value = "RefreshToken过期时间，秒，默认10800s")
  private Integer refreshTokenExpire = 3 * 60 * 60;
  @ApiModelProperty(value = "是否被禁用")
  private boolean disable = false;
  @ApiModelProperty(value = "是否被删除")
  private boolean deleted = false;

  @ApiModelProperty(value = "创建时间：yyyy-MM-dd HH:mm:ss")
  @Column(updatable = false)
  @Temporal(value = TemporalType.TIMESTAMP)
  @CreationTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;
  @ApiModelProperty(value = "更新时间：yyyy-MM-dd HH:mm:ss")
  @Temporal(value = TemporalType.TIMESTAMP)
  @UpdateTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;

}
