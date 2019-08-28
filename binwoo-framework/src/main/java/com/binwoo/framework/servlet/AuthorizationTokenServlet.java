package com.binwoo.framework.servlet;

import com.binwoo.framework.oauth.AuthorizationConstant;
import com.binwoo.framework.oauth.AuthorizationServerManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.binwoo.framework.servlet.
 *
 * @author luoj
 * @date 2019/8/20 11:05
 */
public class AuthorizationTokenServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String username = req.getParameter(AuthorizationConstant.PARAMETER_USERNAME);
    String password = req.getParameter(AuthorizationConstant.PARAMETER_PASSWORD);
    boolean success = AuthorizationServerManager.getInstance().getLoginHandler()
        .check(username, password);
  }
}
