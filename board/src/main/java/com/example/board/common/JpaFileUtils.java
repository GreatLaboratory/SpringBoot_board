package com.example.board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.board.entity.BoardFileEntity;

//component 어노테이션을 이용해서 이 클래스를 스프링의 빈으로 등록
@Component
public class JpaFileUtils {
	public List<BoardFileEntity> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		// jpa의 onetomany어노테이션으로 연관관계를 가지고 있어서 boardfileentity에 게시글번호를 저장할 필요가 없음. 따라서 파라미터로 받지 않는다.
		
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		
		List<BoardFileEntity> fileList = new ArrayList<>(); // 아래 코드에서 fileList에 값넣어 저장한 후 리턴시킬 예정
		
		// 파일이 업로드될 폴더 생성 코드
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/" + current.format(format);
		File file = new File(path);
		if(file.exists()==false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			for(MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty()==false) {
					contentType = multipartFile.getContentType();
					
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						} else {
							break;
						} //3번 if문
					} //2번 if문
					
					newFileName = Long.toString(System.nanoTime())+originalFileExtension;
					BoardFileEntity boardFile = new BoardFileEntity();
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path+"/"+newFileName);
					boardFile.setCreatorId("admin");
					fileList.add(boardFile);
					
					file = new File(path+"/"+newFileName);
					multipartFile.transferTo(file);
				} //1번 if문
			} //for문
		} //while문
		return fileList;
	}
}
