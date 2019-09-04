package com.binwoo.oauth.controller;

import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller.
 *
 * @author hleluo
 * @date 2019/8/29 21:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/save")
  public String save() {
    User user = userService.save();
    return "r5575";
  }
}
