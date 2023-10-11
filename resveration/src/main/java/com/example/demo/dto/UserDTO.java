package com.example.demo.dto;

import java.beans.JavaBean;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Component
@Data
public class UserDTO {
	
	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문, 숫자만 입력 가능합니다.")
	private String id;   	//아이디
	
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message= "최소 8자리에 숫자, 문자, 특수문자 각 1개 이상 포함해 입력해주세요.")
	private String pw;	 	//패스워드
	
	@Pattern(regexp = "^[가-힣,a-z,A-Z]+$", message = "한글, 영문만 입력 가능합니다.")
	private String name; 	//이름
	
	@Pattern(regexp = "^(0[0-9]{2})-([0-9]{4})-([0-9]{4})$", message = "양식에 맞게 입력해주세요 예)010-0000-0000")
	private String phone;	//휴대전화번호
	
	@Email(message = "올바른 형식의 이메일 주소여야 합니다. 예)abcd123@abcd.com")
	private String email;	//이메일
	
	private String role;	//권한
	
	private LocalDateTime date;	//가입일
	
}
