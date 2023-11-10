package com.example.demo.mapper;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.CommentPageDTO;
import com.example.demo.dto.NoticeDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.UserDTO;

@Mapper
public interface MainMapper {
	
	
	
	/* 로그인 */
	UserDTO searchId(String userId);
	
	
	

}
