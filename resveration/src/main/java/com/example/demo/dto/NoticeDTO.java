package com.example.demo.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import lombok.Data;


@Data
public class NoticeDTO {
	
	private String idx;
	private String type;
	private String title;
	private String content;
	private String writer;
	private String writerid;
	private String date;
	private String modifydate;
	
	private String saveMode;

}
