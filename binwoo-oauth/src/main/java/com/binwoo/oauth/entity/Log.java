package com.binwoo.oauth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

/**
 * 日志信息.
 *
 * @author admin
 * @date 2019/9/19 16:50
 */
@ApiModel(value = "日志信息")
@Data
@Entity
@Table(name = "t_log")
public class Log {

  @Id
  @GenericGenerator(name = "uid", strategy = "uuid2")
  @GeneratedValue(generator = "uid")
  private String id;
  @ApiModelProperty(value = "IP地址")
  private String ip;
  @ApiModelProperty(value = "请求路径")
  private String url;
  @ApiModelProperty(value = "资源标识")
  private String resource;
  @ApiModelProperty(value = "模块")
  private String module;
  @ApiModelProperty(value = "方法")
  private String method;
  @ApiModelProperty(value = "方法全路径")
  private String path;
  @ApiModelProperty(value = "参数")
  private String parameter;
  @ApiModelProperty(value = "相应结果")
  private String result;
  @ApiModelProperty(value = "执行时间，单位毫秒")
  private Long period;
  @ApiModelProperty(value = "是否异常的，默认为false")
  private boolean abnormal = false;
  @ApiModelProperty(value = "用户名")
  private String username;
  @ApiModelProperty(value = "创建时间：yyyy-MM-dd HH:mm:ss")
  @Column(updatable = false)
  @Temporal(value = TemporalType.TIMESTAMP)
  @CreationTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

}
