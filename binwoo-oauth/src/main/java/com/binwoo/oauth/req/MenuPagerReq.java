package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单查询参数.
 *
 * @author admin
 * @date 2019/9/16 17:33
 */
@ApiModel(value = "菜单查询参数")
@EqualsAndHashCode(callSuper = false)
@Data
public class MenuPagerReq extends BasePagerReq {

}
