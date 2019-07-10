package com.example.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggerAspect {
//	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Around("execution(* com.example.board..controller.*Controller.*(..)) or execution(* com.example.board..service.*Impl.*(..)) or execution(* com.example.board..mapper.*Mapper.*(..))")
	// 어드바이스는 조인포인트(실행 메소드)에 삽입되어 동작하는 것을 의미
	// @Around어드바이스는 대상 메소드의 호출 전후, 예외발생 등 모든 시점에서 적용할 수 있음 - 가장 많이 쓰임.
	// 포인트컷이란? 어드바이스를 적용한 조인포인트를 선별하는 과정이나 그 기능을 정의한 것 - 위에 execution문법
	// 위의 표현은 com.example.board로 시작해서 하위패키지가 controller인 곳에 Controller라고 끝나는 클래스의 인자가 0개 이상인 모든 조인포인트(메소드)를 의미
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		// name = com.example.board.service.BoardServiceImpl
		
		if(name.indexOf("Controller") > -1) {
			type = "Controller  \t:  ";
		} else if(name.indexOf("Service") > -1) {
			type = "ServiceImpl  \t:  ";
		} else if(name.indexOf("Mapper") > -1) {
			type = "Mapper  \t\t:  ";
		}
		
		log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		
		return joinPoint.proceed();
	}
}
