package com.binwoo.oauth.controller;

import com.binwoo.oauth.entity.User;
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
@RequestMapping("/v1/user")
public class UserController {

  @GetMapping(value = "")
  public String saveType() {
    return "r5575";
  }
}
