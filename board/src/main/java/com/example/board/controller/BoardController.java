package com.example.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;

@Controller
public class BoardController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(path="/board/openBoardList.do")
	// /board/openBoardList.do 이건 클라이언트가 호출하는 주소인데 이것과 수행할 아래의 메소드를 연결시킨다.
	public ModelAndView openBoardList()throws Exception {
		log.debug("openBoardList");
		// debug 레벨의 로그를 출력
		
		ModelAndView mv = new ModelAndView("/board/boardList");
		//호출된 요청의 결과를 보여줄 뷰를 위와 같이 /board/boardList로 지정한다. -> templates폴더 아래에 있는 board/boardList.html을 의미
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		// 실행된 비즈니스 로직의 결과값을 뷰에 list라는 이름으로 저장, 뷰에서 list라는 이름으로 조회결과를 사용가능
		
		return mv;
	}
	
	@RequestMapping(path = "/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception{
		return "/board/boardWrite";
	}
	
	@RequestMapping(path = "/board/insertBoard.do")
	public String insertBoard(BoardDto boardDto) throws Exception {
		boardService.insertBoard(boardDto);
		return "redirect:/board/openBoardList.do";
		// 게시물이 등록된 후 보여줘야하니까 /board/boardList가 아닌 위의 주소로 가줘야함.
	}
	
	@RequestMapping(path="/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		
		BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", boardDto);
		return mv;
	}
	
	@RequestMapping(path="board/updateBoard.do")
	public String updateBoard(BoardDto boardDto) throws Exception {
		boardService.updateBoard(boardDto);
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping(path = "board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
		return "redirect:/board/openBoardList.do";
	}
}
