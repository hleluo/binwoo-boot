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
 * 组信息.
 *
 * @author hleluo
 * @date 2019/9/7 21:06
 */
@Data
public class Group {

  /**
   * 组类型.
   */
  public static enum Type {
    /**
     * 角色.
     */
    ROLE,
    /**
     * 菜单.
     */
    MENU,
    /**
     * 接口.
     */
    API
  }

  @Id
  @GenericGenerator(name = "uid", strategy = "uuid2")
  @GeneratedValue(generator = "uid")
  private String id;
  @ApiModelProperty(value = "类型：角色组、菜单组、权限组")
  private Type type = Type.ROLE;
  @ApiModelProperty(value = "领域，如A系统，B系统")
  private String domain;
  @ApiModelProperty(value = "设备类型：如WEB端、手机端等")
  private String deviceType;
  @ApiModelProperty(value = "名称")
  private String name;
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
