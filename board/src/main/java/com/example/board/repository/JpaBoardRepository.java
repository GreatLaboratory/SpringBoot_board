package com.example.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;

public interface JpaBoardRepository extends CrudRepository<BoardEntity, Integer>{
	
	List<BoardEntity> findAllByOrderByBoardIdxDesc();
	// 게시글 번호로 정렬해서 전체 게시글을 조회한다. 규칙에 맞도록 리포지터리에 메소드를 추가하면 실행 시 메소드의 이름에 따라 쿼리가 생성되어 실행된다!!!
	
	@Query("SELECT file FROM BoardFileEntity file WHERE board_idx = :boardIdx AND idx = :idx")
	BoardFileEntity findBoardFile(@Param("boardIdx") int boardIdx, @Param("idx") int idx);
	
	
}
