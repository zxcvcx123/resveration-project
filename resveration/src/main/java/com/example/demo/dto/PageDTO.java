package com.example.demo.dto;

import lombok.Data;

@Data
public class PageDTO {
	
	private int limitNoticeList = 10; // 가져올 게시판의 글을 몇 개로 제한시켜 보여줄건지 (10개 씩)
	
	private int limitNoticeNowPage; // 가져올 게시판의 현재 페이지 
	
	private int page = 1; // 현재 페이지
	
	private int slicePage = 10; // 몇 페이지로 구성할건지 (10페이지 씩)
	
	private String keyword; // 검색어
	
	private String field; // 검색 타입
	
}
