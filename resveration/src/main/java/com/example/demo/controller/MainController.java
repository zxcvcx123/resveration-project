package com.example.demo.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.CommentPageDTO;
import com.example.demo.dto.NoticeDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.MainService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor // final 필드를 아규먼트로 받는 생성자 만들어주는 lombok 어노테이션
public class MainController {

	// 필드 주입방법
	// @Autowired
	// private MainService mainService;

	// final과 lombok의 @RequiredArgsConstructor 활용한 불변한 생성자 주입 방법
	private final MainService mainService;

	/* ===== login 페이지 ===== */
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	

	/* ===== 로그인 후 메인 페이지 ===== */
	// @AuthenticationPrincipal 어노테이션을 사용하여 인증된 사용자 정보를 직접 전달할 수 있습니다.
	@GetMapping("/home")
	public String main(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

		// 시큐리티에 위임된 세션 정보를 가져와서 변수에 담는다
		// 이걸가지고 ~님 방문을 환영합니다 같은 기능을 구현할 수 있다.
		String userId = principalDetails.getUserid();
		String userName = principalDetails.getUsername();

		Collection<? extends GrantedAuthority> auth = principalDetails.getAuthorities();

		List<String> roles = new ArrayList<>();

		for (GrantedAuthority authority : auth) {
			String role = authority.getAuthority();
			roles.add(role);
		}

		// String userRoles = roles.get(0).toString();

		System.out.println("========= 접속자 정보 START ==========");
		System.out.println("@@@@@@@@@@Id: " + userId);
		System.out.println("@@@@@@@@Nmae: " + userName);
		System.out.println("========= 접속자 정보 END ==========");

		model.addAttribute("userid" + userId);
		model.addAttribute("username" + userName);

		return "home";
	}

	

	/* ===== 오시는길 ===== */
	@GetMapping("/welcome")
	public String welcomePage() {
		return "welcome";
	}

	

}
