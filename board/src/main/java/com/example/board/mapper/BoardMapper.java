package com.example.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardFileDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> selectBoardList() throws Exception;
	
	void insertBoard(BoardDto boardDto) throws Exception;
	void insertBoardFileList(List<BoardFileDto> lsit) throws Exception;
	
	BoardDto selectBoardDetail(int boardIdx) throws Exception;
	void updateHitCount(int boardIdx) throws Exception;
	List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;
	
	void updateBoard(BoardDto boardDto) throws Exception;
	
	void deleteBoard(int boardIdx) throws Exception;
}
