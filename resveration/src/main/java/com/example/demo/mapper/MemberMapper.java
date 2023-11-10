package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.UserDTO;

@Mapper
public interface MemberMapper {
	
	/* 회원가입 */
	boolean memberDo(UserDTO user);
	
	/* 아이디 중복확인 */
	String checkId(String checkId);

}
