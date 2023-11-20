package com.example.demo.dto;

import lombok.Data;

@Data
public class CommentLikeDTO {
	
	
	private Integer idx;
	private String user_id;
	private Integer notice_idx;
	private Integer comment_idx;

}
