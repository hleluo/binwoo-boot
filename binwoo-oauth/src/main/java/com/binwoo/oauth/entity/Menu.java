package com.binwoo.oauth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 菜单信息.
 *
 * @author hleluo
 * @date 2019/9/7 21:06
 */
@ApiModel(value = "菜单信息")
@Data
@Entity
@Table(name = "t_menu")
public class Menu implements Serializable {

  @Id
  @GenericGenerator(name = "uid", strategy = "uuid2")
  @GeneratedValue(generator = "uid")
  private String id;
  @ApiModelProperty(value = "应用id")
  private String appId;
  @ApiModelProperty(value = "优先级，越小越在前")
  private Integer priority = 0;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "图标")
  private String icon;
  @ApiModelProperty(value = "地址")
  private String url;
  @ApiModelProperty(value = "组件名称")
  private String component;
  @ApiModelProperty(value = "组件路径")
  private String path;
  @ApiModelProperty(value = "是否外部菜单")
  private boolean external = false;
  @ApiModelProperty(value = "是否为元素")
  private boolean elemental = false;
  @ApiModelProperty(value = "域，如id、class等，可为空")
  private String scope;
  @ApiModelProperty(value = "父节点")
  private String parentId;
  @ApiModelProperty(value = "描述")
  private String description;
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

  @ApiModelProperty(value = "子节点，可为空")
  @Transient
  private Set<Menu> children;

}
