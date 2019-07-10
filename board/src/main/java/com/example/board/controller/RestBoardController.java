package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;

@Controller
public class RestBoardController {
	@Autowired
	private BoardService boardService;
	
	// 게시물 목록 조회
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception {
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	// 게시물 작성 화면 호출
	@RequestMapping(value="/board/write", method=RequestMethod.GET)
	public String openBoardWrite() {
		return "/board/restBoardWrite";
	}
	
	// 게시물 작성
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardService.insertBoard(boardDto, multipartHttpServletRequest);
		
		return "redirect:/board";
	}
	
	// 게시물 상세 화면
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", boardDto);
		
		return mv;
	}
	
	// 게시물 수정
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(BoardDto boardDto) throws Exception {
		boardService.updateBoard(boardDto);
		
		return "redirect:/board";
	}
	
	// 게시물 삭제
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.DELETE)	
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/board";
	}
	
}
