package com.binwoo.oauth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * 字典类型信息.
 *
 * @author admin
 * @date 2019/9/19 16:50
 */
@ApiModel(value = "字典类型信息")
@Data
@Entity
@Table(name = "t_dict_type")
public class DictType {

  @Id
  @GenericGenerator(name = "uid", strategy = "uuid2")
  @GeneratedValue(generator = "uid")
  private String id;
  @ApiModelProperty(value = "标识，唯一")
  private String code;
  @ApiModelProperty(value = "优先级，越小越在前")
  private Integer priority = 0;
  @ApiModelProperty(value = "名称")
  private String name;
  @ApiModelProperty(value = "描述")
  private String description;
  @ApiModelProperty(value = "字典类型父id，可为空")
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

}
