package com.binwoo.oauth.service;

import com.binwoo.framework.http.exception.HttpException;
import com.binwoo.framework.http.response.PageList;
import com.binwoo.oauth.entity.User;
import com.binwoo.oauth.req.UserQueryReq;

/**
 * 用户服务接口.
 *
 * @author admin
 * @date 2019/9/4 17:00
 */
public interface UserService {

  User save(User user) throws HttpException;

  PageList<User> query(UserQueryReq req);

}
