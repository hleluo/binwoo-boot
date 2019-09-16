package com.binwoo.oauth.controller;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * 保存用户信息.
   *
   * @param user 用户信息
   * @return 用户信息
   */
  @JsonSerialize
  @PostMapping
  public User save(@RequestBody User user) throws HttpException {
    log.info("save param = {}", user);
    user = userService.save(user);
    log.info("save response = {}", user);
    return user;
  }
}
