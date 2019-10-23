package com.binwoo.oauth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * API信息.
 *
 * @author hleluo
 * @date 2019/9/7 21:06
 */
@ApiModel(value = "API信息")
@Data
@Entity
@Table(name = "t_api")
public class Api implements Serializable {

  @Id
  @GenericGenerator(name = "uid", strategy = "uuid2")
  @GeneratedValue(generator = "uid")
  private String id;
  @ApiModelProperty(value = "资源id")
  private String resourceId;
  @ApiModelProperty(value = "地址")
  private String url;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "请求方式")
  private String method;
  @ApiModelProperty(value = "描述")
  private String description;
  @ApiModelProperty(value = "父节点")
  private String parentId;
  @ApiModelProperty(value = "创建时间：yyyy-MM-dd HH:mm:ss")
  @Column(updatable = false)
  @CreationTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  @ApiModelProperty(value = "更新时间：yyyy-MM-dd HH:mm:ss")
  @UpdateTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;

  @ApiModelProperty(value = "子节点，可为空")
  @Transient
  private Set<Api> children;

}
