package com.example.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.board.common.JpaFileUtils;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import com.example.board.repository.JpaBoardRepository;

@Service
public class JpaBoardServiceImpl implements JpaBoardService{
	
	@Autowired
	private JpaBoardRepository jpaBoardRepository;
	
	@Autowired
	private JpaFileUtils jpaFillUtils;
	
	
	@Override
	public List<BoardEntity> selectBoardList() throws Exception {
		return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
	}

	@Override
	public void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		board.setCreatorId("admin");
		List<BoardFileEntity> list = jpaFillUtils.parseFileInfo(multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list)==false) {
			board.setFileList(list);
		}
		jpaBoardRepository.save(board);
	}

	@Override
	public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
		Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
		if(optional.isPresent()) {
			BoardEntity board = optional.get();
			board.setHitCnt(board.getHitCnt()+1);
			jpaBoardRepository.save(board);
			
			return board;
		} else {
			throw new NullPointerException();
		}
		
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		jpaBoardRepository.deleteById(boardIdx);
	}
	
}
