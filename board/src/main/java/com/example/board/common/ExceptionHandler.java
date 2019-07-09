package com.example.board.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
// 위 어노테이션은 이 클래스가 예외처리 클래스임을 알려준다.
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	// 어떤 예외가 발생될 때 아래 메소드가 구현되는지에 대한 어노테이션인데 여기선 모든 예외인 Exception클래스로 설정
	// 보통은 이 어노테이션의 메소드를 가장 아래에 구현해놓고 위에다가 좀 더 구체적인 exception을 올려놓는게 일반적이다.
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		
		// 예외발생 시 보여줄 화변(뷰) 설정
		ModelAndView mv = new ModelAndView("/error/error_default");
		mv.addObject("exception", exception);
		
		// 에러 로그 출력
		log.error("exception", exception);
		
		return mv;
	}
}
