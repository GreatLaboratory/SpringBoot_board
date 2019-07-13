package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

// 아래 어노테이션을 사용하면 결과값을 json형식으로 만들어준다.
@RestController
@Api(description="게시판 REST API")
public class RestBoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	// get과 post,put의 주요한 차이점은 get은 요청주소에 파라미터를 같이 보내는 거고 post,put은 파라미터를 http패킷의 바디에 담아 전송한다는 점이다.
	// @requestbody 어노테이션은 메소드의 파라미터가 반드시 http패킷의 바디에 담겨있어야함을 의미한다.-> 그래서 post나 put에서 쓰인다.
	// 반면 get에선 @requestparam으로 쓰인다.
	
	@ApiOperation(value="게시물 목록 조회")
	@RequestMapping(value="/api/board", method=RequestMethod.GET)
	public List<BoardDto> openBoardList() throws Exception{
		// 기존엔 modelandview를 이용해서 목록조회결과를 담아 뷰에 보냈지만 여기선 결과를 바로 api응답결과로 사용 -> 바로 json형태로 반환
		return boardService.selectBoardList();
	}
	
	@ApiOperation(value="게시글 작성")
	@RequestMapping(value="/api/board/write", method=RequestMethod.POST)
	public void insertBoard(@RequestBody BoardDto board) throws Exception{
		boardService.insertBoard(board, null);
	}
	@ApiOperation(value="게시글 상세 내용 조회")
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.GET)
	public BoardDto openBoardDetail(@PathVariable("boardIdx") @ApiParam(value="게시물 번호") int boardIdx) throws Exception{
		
		return boardService.selectBoardDetail(boardIdx);
	}
	@ApiOperation(value="게시글 상세 내용 수정")
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(@RequestBody BoardDto board) throws Exception{
		boardService.updateBoard(board);
		return "redirect:/board";
	}
	@ApiOperation(value="게시글 삭제")
	@RequestMapping(value="/api/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") @ApiParam(value="게시물 번호") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
	}
}