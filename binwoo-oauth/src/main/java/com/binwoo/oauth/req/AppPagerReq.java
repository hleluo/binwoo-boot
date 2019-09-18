package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 应用查询参数.
 *
 * @author admin
 * @date 2019/9/16 17:33
 */
@ApiModel(value = "应用查询参数")
@EqualsAndHashCode(callSuper = false)
@Data
public class AppPagerReq extends BasePagerReq {

}
