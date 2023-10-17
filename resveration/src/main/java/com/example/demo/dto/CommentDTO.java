package com.example.demo.dto;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.auth.PrincipalDetails;

import lombok.Data;

@Data
public class CommentDTO {
	
	private Integer idx; // 댓글 pk
	private Integer notice_idx; // 게시판 번호
	private String comment; // 댓글 내용
	private String writer; // 작성자 이름
	private String writerid; // 작성자 ID
	private LocalDateTime date; // 작성날짜
	private LocalDateTime modifydate; // 댓글 수정 날짜
	

}
