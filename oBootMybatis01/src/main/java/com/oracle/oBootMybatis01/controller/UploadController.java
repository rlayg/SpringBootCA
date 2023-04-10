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
		FileCopyUtils.copy(fileData, target);	// org.springframeword.util.FileCopyUtils
		
		return savedName;
		
	}
	
}
