package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@GetMapping("/board")
	public ModelAndView openBoardList() throws Exception {
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	// 게시물 작성 화면 호출
	@GetMapping("/board/write")
	public String openBoardWrite() {
		return "/board/restBoardWrite";
	}
	
	// 게시물 작성
	@PostMapping("/board/write")
	public String insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardService.insertBoard(boardDto, multipartHttpServletRequest);
		
		return "redirect:/board";
	}
	
	// 게시물 상세 화면
	@GetMapping("/board/{boardIdx}")
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", boardDto);
		
		return mv;
	}
	
	// 게시물 수정
	@PutMapping("/board/{boardIdx}")
	public String updateBoard(BoardDto boardDto) throws Exception {
		boardService.updateBoard(boardDto);
		
		return "redirect:/board";
	}
	
	// 게시물 삭제
	@DeleteMapping("/board/{boardIdx}")
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/board";
	}
	
}
