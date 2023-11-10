package com.example.demo.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.CommentPageDTO;
import com.example.demo.dto.NoticeDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.MainMapper;

@Service
public class MainService {

	@Autowired
	private MainMapper mainMapper;

	/* 암호변환을 위한 의존성 추가 (Spring Sercurity) */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	

	
}
