package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotEmpty;
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

  @NotEmpty(message = "Delete.ids.empty")
  @ApiModelProperty(value = "id列表", required = true)
  private List<String> ids;

}
