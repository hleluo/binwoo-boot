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
 * 字典选项信息.
 *
 * @author admin
 * @date 2019/9/19 16:50
 */
@ApiModel(value = "字典选项信息")
@Data
@Entity
@Table(name = "t_dict_option")
public class DictOption {

  @Id
  @GenericGenerator(name = "uid", strategy = "uuid2")
  @GeneratedValue(generator = "uid")
  private String id;
  @ApiModelProperty(value = "标识，唯一")
  private String code;
  @ApiModelProperty(value = "字典值，如MALE、FEMALE等")
  private String value;
  @ApiModelProperty(value = "字典展示名称，如男、女等")
  private String name;
  @ApiModelProperty(value = "优先级，越小越在前")
  private Integer priority = 0;
  @ApiModelProperty(value = "描述")
  private String description;
  @ApiModelProperty(value = "字典类型id")
  private String typeId;
  @ApiModelProperty(value = "是否文本显示，TRUE 显示文本；FALSE 显示标签")
  private boolean textual = true;
  @ApiModelProperty(value = "标签类型")
  private String tag;
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
