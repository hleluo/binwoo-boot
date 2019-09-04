package com.binwoo.oauth.security;

import com.binwoo.oauth.integrate.AuthTokenIntegrator;
import com.binwoo.oauth.integrate.AuthTokenParam;
import com.binwoo.oauth.integrate.AuthTokenParamContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * token验证拦截.
 *
 * @author hleluo
 * @date 2019/8/31 21:13
 */
@Component
public class AuthTokenEndpointFilter extends GenericFilterBean implements ApplicationContextAware {

  /**
   * 登录地址.
   */
  private static final String URI_PATH_AUTH_TOKEN = "/oauth/token";
  /**
   * 登录授权方式参数名.
   */
  private static final String PARAM_KEY_AUTH_TYPE = "auth_type";
  /**
   * 登录方式集合.
   */
  private Collection<AuthTokenIntegrator> integrators;
  /**
   * 应用上下文.
   */
  private ApplicationContext applicationContext;
  /**
   * 请求匹配器.
   */
  private RequestMatcher requestMatcher;

  /**
   * 构造函数.
   */
  public AuthTokenEndpointFilter() {
    //匹配登录接口的POST和GET请求，用户多方式登录扩展.
    this.requestMatcher = new OrRequestMatcher(
        new AntPathRequestMatcher(URI_PATH_AUTH_TOKEN, HttpMethod.GET.name()),
        new AntPathRequestMatcher(URI_PATH_AUTH_TOKEN, HttpMethod.POST.name())
    );
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    if (requestMatcher.matches(request)) {
      //获取登录信息，存入上下文.
      AuthTokenParam param = new AuthTokenParam();
      param.setAuthType(request.getParameter(PARAM_KEY_AUTH_TYPE));
      param.setParameters(request.getParameterMap());
      AuthTokenParamContext.set(param);
      try {
        //权限验证前的预处理
        this.prepare(param);
        filterChain.doFilter(request, response);
        //后置处理
        this.complete(param);
      } finally {
        //清空参数.
        AuthTokenParamContext.clear();
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  /**
   * 进行预处理.
   */
  private void prepare(AuthTokenParam param) {
    //延迟加载认证器.
    if (this.integrators == null) {
      synchronized (this) {
        //自动检测所有实现AuthTokenIntegrator接口的类，加入到登录方式列表中.
        Map<String, AuthTokenIntegrator> integratorMap = applicationContext
            .getBeansOfType(AuthTokenIntegrator.class);
        if (integratorMap != null) {
          this.integrators = integratorMap.values();
        }
      }
    }
    if (this.integrators == null) {
      this.integrators = new ArrayList<>();
    }
    //支持的登录方式才进入预处理.
    for (AuthTokenIntegrator integrator : integrators) {
      if (integrator.support(param)) {
        integrator.prepare(param);
      }
    }
  }

  /**
   * 后置处理.
   */
  private void complete(AuthTokenParam param) {
    for (AuthTokenIntegrator integrator : integrators) {
      if (integrator.support(param)) {
        integrator.complete(param);
      }
    }
  }
}
