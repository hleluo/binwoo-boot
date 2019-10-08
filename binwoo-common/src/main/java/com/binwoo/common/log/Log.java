package com.binwoo.common.log;

import java.util.Date;

/**
 * 日志信息.
 *
 * @author admin
 * @date 2019/9/19 16:50
 */
public class Log {

  private String resource;
  private String module;
  private String method;
  private String path;
  private String parameter;
  private String result;
  private boolean abnormal = false;
  private String username;
  private Date createTime;

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public boolean isAbnormal() {
    return abnormal;
  }

  public void setAbnormal(boolean abnormal) {
    this.abnormal = abnormal;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
