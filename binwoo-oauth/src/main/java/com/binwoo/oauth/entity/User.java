package com.binwoo.oauth.entity;

import java.util.Set;
import lombok.Data;

/**
 * 用户信息.
 *
 * @author hleluo
 * @date 2019/8/29 23:20
 */
@Data
public class User {

  private String username;
  private String mobile;
  private String email;
  private String password;
  private boolean disable = false;
  private boolean deleted = false;
  private Set<String> authorities;

}
