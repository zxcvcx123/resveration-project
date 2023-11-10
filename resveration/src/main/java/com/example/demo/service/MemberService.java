package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	/* 암호변환을 위한 의존성 추가 (Spring Sercurity) */
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final MemberMapper memberMapper;
	
	
	/*회원가입 */
	public boolean memberDo(UserDTO user) {

		/* 시큐리티 암호변환 기능을 위해 암호 입력된 값을 가져오고 */
		String pw = user.getPw();

		/* 그걸 BCryptPasswordEncoder.encode(가입 패스워드); 를 넣어 암호화 변환 해준다 */
		String encPassword = bCryptPasswordEncoder.encode(pw);

		/* 변환된 암호를 set 해서 넣어준다 */
		user.setPw(encPassword);

		/* 기본적으로 게스트 사용자 */
		user.setRole("guest");

		/* 가입일 */
		user.setDate(LocalDateTime.now());

		System.out.println("===== 가입 완료 =====");

		memberMapper.memberDo(user);

		return true;
	};

	/*  아이디 중복확인 */
	public String checkId(String checkId) {
		System.out.println("@@@@ 들어가는중: " + checkId);
		System.out.println("@@@@ 나오는중: " + memberMapper.checkId(checkId));
		return memberMapper.checkId(checkId);
	}

}
