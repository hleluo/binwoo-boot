package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.StringUtils;


/**
 * 分页查询参数.
 *
 * @author admin
 * @date 2019/9/16 17:33
 */
@ApiModel(value = "分页查询参数")
@Data
public class BasePagerReq implements Serializable {

  private static final String SORT_DIRECT_DESC = "DESC";
  private static final Integer PAGE_SIZE_DEFAULT = 10;

  @ApiModelProperty(value = "页索引，从0开始，默认为0")
  private int pageOffset = 0;
  @ApiModelProperty(value = "每页大小，默认为10")
  private int pageSize = PAGE_SIZE_DEFAULT;
  @ApiModelProperty(value = "排序字段，可选")
  private String sortField;
  @ApiModelProperty(value = "排序方向：ASC升序；DESC降序，可选")
  private String sortDirect;

  /**
   * 分页请求.
   *
   * @return 分页信息
   */
  public Pageable getPageRequest() {
    pageOffset = pageOffset < 0 ? 0 : pageOffset;
    pageSize = pageSize <= 0 ? PAGE_SIZE_DEFAULT : pageSize;
    Sort sort = getSort();
    return sort == null ? PageRequest.of(pageOffset, pageSize)
        : PageRequest.of(pageOffset, pageSize, sort);
  }

  /**
   * 获取排序条件.
   *
   * @return 排序条件
   */
  public Sort getSort() {
    Sort sort = null;
    if (!StringUtils.isEmpty(sortField)) {
      if (SORT_DIRECT_DESC.equals(sortDirect)) {
        sort = Sort.by(new Order(Sort.Direction.DESC, sortField, Sort.NullHandling.NULLS_LAST));
      } else {
        sort = Sort.by(new Order(Sort.Direction.ASC, sortField, Sort.NullHandling.NULLS_LAST));
      }
    }
    return sort;
  }

}
