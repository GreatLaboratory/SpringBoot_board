package com.example.board.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.board.dto.BoardDto;
import com.example.board.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	// 데이터베이스에 접근하는 DAO빈을 선언
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}

	@Override
	public void insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		//boardMapper.insertBoard(boardDto);
		
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)==false) {
			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
			String name;
			
			while(iterator.hasNext()) {
				name = iterator.next();
				log.debug("file tag name : " + name);
				// 이건 html파일에서 form의 파일업로드하는 input태그의 name속성값을 로그로 찍어준다.
				// 아마 파일업로드하는 태그가 두 개있어서 name도 두개였으면 while문을 두 번 돌렸겠지??
				
				List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
				// name태그의 속성값을 인자로 넣어 첨부한 일련의 이미지파일들을 multipartfile형식의 list로 읽어들인다.
				
				for(MultipartFile multipartFile : list) {
					log.debug("start file information");
					log.debug("file name : " + multipartFile.getOriginalFilename());
					log.debug("file size : " + multipartFile.getSize());
					log.debug("file content type : " + multipartFile.getContentType());
					log.debug("end file information  \n");
				}
			}
		}
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
