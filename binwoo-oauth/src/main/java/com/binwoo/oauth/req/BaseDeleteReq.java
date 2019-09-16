package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 基础删除参数.
 *
 * @author admin
 * @date 2019/9/16 17:33
 */
@ApiModel(value = "基础删除参数")
@Data
public class BaseDeleteReq implements Serializable {

  @ApiModelProperty(value = "id列表")
  private List<String> ids;

}
