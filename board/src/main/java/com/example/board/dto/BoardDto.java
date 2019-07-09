package com.example.board.dto;

import lombok.Data;

// 아래 어노테이션만으로 알아서 getter/setter메소드와 toString, hashCode, equals등의 메소드도 생성
@Data
public class BoardDto {
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private String createdDatetime;
	private String updaterId;
	private String updatedDatetime;
	
}
