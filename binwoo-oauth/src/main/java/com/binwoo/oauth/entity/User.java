package com.binwoo.oauth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 用户信息.
 *
 * @author hleluo
 * @date 2019/8/29 23:20
 */
@ApiModel(value = "用户信息")
@Data
@Entity
@Table(name = "t_user")
public class User implements Serializable {

  @Id
  @GenericGenerator(name = "uid", strategy = "uuid2")
  @GeneratedValue(generator = "uid")
  private String id;
  @ApiModelProperty(value = "用户名")
  private String username;
  @ApiModelProperty(value = "昵称")
  private String nickname;
  @ApiModelProperty(value = "手机号")
  private String mobile;
  @ApiModelProperty(value = "邮箱")
  private String email;
  @ApiModelProperty(value = "密码")
  private String password;
  @ApiModelProperty(value = "是否已激活")
  private boolean active = false;
  @ApiModelProperty(value = "是否被禁用")
  private boolean disable = false;
  @ApiModelProperty(value = "是否被删除")
  private boolean deleted = false;
  @ApiModelProperty(value = "类别：如交警用户、企业用户等")
  private String category;
  @ApiModelProperty(value = "类型：如管理员、普通用户等")
  private String type;
  @ApiModelProperty(value = "来源：如组织机构、企业等")
  private String sourceId;
  @ApiModelProperty(value = "主题")
  private String theme;
  @ApiModelProperty(value = "头像")
  private String thumbnail;
  @ApiModelProperty(value = "过期时间：yyyy-MM-dd HH:mm:ss，为NULL时永不过期")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime expireTime;
  @ApiModelProperty(value = "额外数据，JSON")
  private String extra;
  @ApiModelProperty(value = "创建时间：yyyy-MM-dd HH:mm:ss")
  @Column(updatable = false)
  @CreationTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  @ApiModelProperty(value = "更新时间：yyyy-MM-dd HH:mm:ss")
  @UpdateTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonProperty
  public void setPassword(String password) {
    this.password = password;
  }
}
