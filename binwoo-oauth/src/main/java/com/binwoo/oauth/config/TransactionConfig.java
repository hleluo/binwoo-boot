package com.binwoo.oauth.config;

import com.binwoo.oauth.exception.SqlException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 事务配置.
 *
 * @author admin
 * @date 2019/9/19 9:47
 */
@Aspect
@Configuration
public class TransactionConfig {

  private static final String AOP_POINTCUT_EXPRESSION = "execution (* com..*.service..*.*(..))";

  private final PlatformTransactionManager manager;

  @Autowired
  public TransactionConfig(PlatformTransactionManager manager) {
    this.manager = manager;
  }

  /**
   * 事务拦截器.
   *
   * @return 事务拦截器
   */
  @Bean
  public TransactionInterceptor txAdvice() {
    //当前没有事务：只有select，非事务执行；有update，insert，delete操作，自动提交；
    //当前有事务：如果有update，insert，delete操作，支持当前事务
    RuleBasedTransactionAttribute readOnlyRule = new RuleBasedTransactionAttribute();
    readOnlyRule.setReadOnly(true);
    readOnlyRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
    /* 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
    RuleBasedTransactionAttribute requiredRule = new RuleBasedTransactionAttribute();
    // 回滚异常
    RollbackRuleAttribute attribute = new RollbackRuleAttribute(SqlException.class);
    requiredRule.setRollbackRules(Collections.singletonList(attribute));
    requiredRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    //设置事务失效时间，如果超过5秒，则回滚事务
    //requiredRule.setTimeout(5);
    Map<String, TransactionAttribute> names = new HashMap<>();
    names.put("add*", requiredRule);
    names.put("save*", requiredRule);
    names.put("insert*", requiredRule);
    names.put("update*", requiredRule);
    names.put("delete*", requiredRule);
    names.put("get*", readOnlyRule);
    names.put("query*", readOnlyRule);
    NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
    source.setNameMap(names);
    return new TransactionInterceptor(manager, source);
  }

  /**
   * 声明切点的面.
   *
   * @return 切面
   */
  @Bean
  public Advisor txAdviceAdvisor() {
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
    return new DefaultPointcutAdvisor(pointcut, txAdvice());
  }
}
