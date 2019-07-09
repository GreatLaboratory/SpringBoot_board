package com.example.board.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

// aop를 이용하여 트랜잭션을 설정하면 새로운 클래스나 메소드가 추가될 때 따로 어노테이션을 붙이지 않아도 자동적으로 트랜잭션 처리가 된다.

@Configuration
public class TransactionAspect {
	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	private static final String AOP_TRANSACTION_EXPRESSION = "execution(* com.example.board..service.*Impl.*(..))";
	
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public TransactionInterceptor transactionAdvice() {
		MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		
		transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
		// 트랜잭션의 이름을 설정 + 트랜잭션 모니터에서 트랜젝션의 이름으로 확인가능
		
		transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		// 트랜젝션에서 롤백하는 룰설정 - 여기선 모든 exception으로 설정해서 어떤 예외가 발생하더라도 롤백이 수행된다.
		source.setTransactionAttribute(transactionAttribute);
		
		return new TransactionInterceptor(transactionManager, source);
	}
	
	@Bean
	public Advisor transactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);
		// aop의 포인트컷 설정 / 여기선 비즈니스로직이 수행되는 모든 serviceImpl클래스의 모든 메소드를 지정
		
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}
}
