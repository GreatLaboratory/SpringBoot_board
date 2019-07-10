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

import com.example.board.dto.BoardFileDto;

// component 어노테이션을 이용해서 이 클래스를 스프링의 빈으로 등록
@Component
public class FileUtils {
	public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		
		List<BoardFileDto> fileList = new ArrayList<>(); // 아래 코드에서 fileList에 값넣어 저장한 후 리턴시킬 예정
		
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
					BoardFileDto boardFileDto = new BoardFileDto();
					boardFileDto.setBoardIdx(boardIdx);
					boardFileDto.setFileSize(multipartFile.getSize());
					boardFileDto.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFileDto.setStoredFilePath(path+"/"+newFileName);
					fileList.add(boardFileDto);
					
					file = new File(path+"/"+newFileName);
					multipartFile.transferTo(file);
				} //1번 if문
			} //for문
		} //while문
		return fileList;
	}
}
