package com.example.board.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

// 아래 어노테이션만으로 알아서 getter/setter메소드와 toString, hashCode, equals등의 메소드도 생성
@Data
@ApiModel(value="BoardDto : 게시글 내용", description="게시글 내용")
public class BoardDto {
	@ApiModelProperty(value="게시글 번호")
	private int boardIdx;
	
	@ApiModelProperty(value="게시글 제목")
	private String title;
	
	@ApiModelProperty(value="게시글 내용")
	private String contents;
	
	@ApiModelProperty(value="게시글 조회수")
	private int hitCnt;
	
	@ApiModelProperty(value="게시글 작성자")
	private String creatorId;
	
	@ApiModelProperty(value="게시글 작성시간")
	private String createdDatetime;
	
	@ApiModelProperty(value="게시글 수정자")
	private String updaterId;
	
	@ApiModelProperty(value="게시글 수정시간")
	private String updatedDatetime;
	
	@ApiModelProperty(value="게시글 첨부파일목록")
	private List<BoardFileDto> fileList;
}
