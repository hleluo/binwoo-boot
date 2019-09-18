package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端查询参数.
 *
 * @author admin
 * @date 2019/9/16 17:33
 */
@ApiModel(value = "客户端查询参数")
@EqualsAndHashCode(callSuper = false)
@Data
public class ClientPagerReq extends BasePagerReq {

}
