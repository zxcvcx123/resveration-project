package com.example.demo.dto;

import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;
import lombok.Data;


@Data
public class BoardDTO {
	
	private String idx;			//pk 값
	private String boardid;		//예약자 ID
	private String name; 		//예약자
	private String phone; 		//연락처
	private String count;		//인원수
	private String day;			//예약날짜
	private String time;		//예약시간
	private String timetype;    //예약시간의 타입
	private String type;		//촬영타입
	private LocalDateTime boarddate;	//등록일
	
	private String saveMode;
	
}
