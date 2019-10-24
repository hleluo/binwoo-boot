package com.binwoo.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.CollectionUtils;
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

  private static final String SORT_DIRECT_ASC = "ASC";
  private static final String SORT_DIRECT_DESC = "DESC";
  private static final Integer PAGE_SIZE_DEFAULT = 10;

  @ApiModelProperty(value = "页索引，从0开始，默认为0")
  private int pageOffset = 0;
  @ApiModelProperty(value = "每页大小，默认为10")
  private int pageSize = PAGE_SIZE_DEFAULT;
  @ApiModelProperty(value = "排序字段，可选，多个逗号隔开")
  private String sortField;
  @ApiModelProperty(value = "排序方向：ASC升序；DESC降序，可选，多个逗号隔开，与sortField对应")
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
    List<SortValue> values = getSortValues();
    if (!CollectionUtils.isEmpty(values)) {
      List<Sort.Order> orders = new ArrayList<>();
      for (SortValue value : values) {
        if (SORT_DIRECT_DESC.equals(value.getDirect())) {
          orders.add(new Order(Direction.DESC, value.getField(), Sort.NullHandling.NULLS_LAST));
        } else {
          orders.add(new Order(Direction.ASC, value.getField(), Sort.NullHandling.NULLS_LAST));
        }
      }
      sort = Sort.by(orders);
    }
    return sort;
  }

  /**
   * 获取排序值.
   *
   * @return 排序列表
   */
  public List<SortValue> getSortValues() {
    List<SortValue> values = new ArrayList<>();
    if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortDirect)) {
      String[] fields = sortField.trim().split(",");
      String[] directs = sortDirect.trim().split(",");
      for (int i = 0; i < fields.length; i++) {
        SortValue value = new SortValue();
        value.setField(fields[i]);
        String direct = directs.length > i ? directs[i] : SORT_DIRECT_ASC;
        if (SORT_DIRECT_DESC.equalsIgnoreCase(direct)) {
          value.setDirect(SORT_DIRECT_DESC);
        } else {
          value.setDirect(SORT_DIRECT_ASC);
        }
        values.add(value);
      }
    }
    return values;
  }

  public class SortValue {

    /**
     * 排序字段.
     */
    private String field;
    /**
     * 排序方向.
     */
    private String direct;

    public String getField() {
      return field;
    }

    public void setField(String field) {
      this.field = field;
    }

    public String getDirect() {
      return direct;
    }

    public void setDirect(String direct) {
      this.direct = direct;
    }
  }

}
