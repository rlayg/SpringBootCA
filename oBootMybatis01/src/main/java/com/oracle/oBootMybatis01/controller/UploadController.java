package com.oracle.oBootMybatis01.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UploadController {
//	upLoadForm 시작화면
	@RequestMapping(value = "upLoadFormStart")
	public String upLoadFormStart(Model model) {
		System.out.println("upLoadFormStart Start...");
		return "upLoadFormStart";
	}
	
	@RequestMapping(value = "uploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		System.out.println("uploadForm GET Start...");
		System.out.println();
	}
	
	@RequestMapping(value = "uploadForm", method = RequestMethod.POST)
	public String uploadForm(HttpServletRequest request, MultipartFile file1, Model model) 
		throws IOException, Exception {
//		Servlet 상속받지 못했을 때  realPath 불러 오는 방법
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		
		System.out.println("uploadForm POST Start...");
		log.info("originalName : " + file1.getOriginalFilename());
		log.info("size : " + file1.getSize());
		log.info("contentType : " + file1.getContentType());
		log.info("uploadPath : " + uploadPath);
		String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
//		Service --> DB CRUD
		
		log.info("Return savedName : " + savedName);
		model.addAttribute("savedName", savedName);
		
		return "uploadResult";
	}

	private String uploadFile(String originalName, byte[] fileData, String uploadPath) throws IOException {
		
//		universally unique identifier (UUID).
		UUID uid = UUID.randomUUID();
//		requestPath = requestPath + "/resources/image";
		System.out.println("uploadPath -> " + uploadPath);	// uploadPath 업로드할 폴더 이름
//		Directory 생성
		File fileDirectory = new File(uploadPath);
		if(!fileDirectory.exists()) {
//			신규 폴더(Directory) 생성
			fileDirectory.mkdirs();
			System.out.println("업로드용 폴더 생성 : " + uploadPath);
		}
		
		String savedName = uid.toString() + "_" + originalName;
		log.info("savedName : " + savedName);
		File target = new File(uploadPath, savedName);
//		File target = new File(requestPath, savaName);
//		File UpLoad ---> uploadPath / UUID +_+ originalName
		FileCopyUtils.copy(fileData, target);	// org.springframeword.util.FileCopyUtils	/	boot에서 이걸 쓰는걸 권장
		
		return savedName;
		
	}
	
//	이미지 삭제
	@RequestMapping(value = "uploadFileDelete", method = RequestMethod.GET)
	public String uploadFileDelete(HttpServletRequest request, Model model) {
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		String deleteFile = uploadPath + "b9aaa9d4-3959-4ec6-9cbb-88e5d7516f63_nav2.png";	// 확장자도 꼭 써줘야함 / 여기는 이미지 삽입된 경로에서 파일명 찾아서 넣기
		log.info("deleteFile : " + deleteFile);
		System.out.println("uploadFileDelete GET Start...");
		int delResult = uploadFileDelete(deleteFile);
		log.info("deleteFile result -> " + delResult);
		model.addAttribute("deleteFile", deleteFile);
		model.addAttribute("delResult", delResult);
		return "uploadDelResult";
	}

	private int uploadFileDelete(String deleteFileName) {
		int result = 0;
		log.info("upFileDelte result -> " + deleteFileName);
		File file = new File(deleteFileName);
		if(file.exists()) {
			if(file.delete()) {
				System.out.println("파일삭제 성공");
				result = 1;
			} else {
				System.out.println("파일삭제 실패");
				result = 0;
			}
		} else {
			System.out.println("파일이 존재하지 않습니다");
			result = -1;
		}
		
		return result;
	}
	
}
