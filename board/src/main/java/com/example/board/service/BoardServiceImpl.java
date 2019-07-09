package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDto;
import com.example.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	// 데이터베이스에 접근하는 DAO빈을 선언
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}

	@Override
	public void insertBoard(BoardDto boardDto) throws Exception {
		boardMapper.insertBoard(boardDto);
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		
		// 선택된 게시물보여주기와 선택된 게시물의 조회수 올려주기 이렇게 두 가지 기능을 수행하기 위함
		boardMapper.updateHitCount(boardIdx);
		// 트랜젝션 성능검사 고의 오류
		//int i = 10 / 0;
		return boardMapper.selectBoardDetail(boardIdx);
	}

	@Override
	public void updateBoard(BoardDto boardDto) throws Exception {
		boardMapper.updateBoard(boardDto);
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardMapper.deleteBoard(boardIdx);
	}
	
	
}
