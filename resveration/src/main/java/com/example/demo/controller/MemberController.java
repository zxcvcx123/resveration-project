package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	/* ===== 회원가입 페이지 ===== */
	@GetMapping("/member")
	public String memberPage() {
		return "member";
	}

	/* ===== 아이디 중복확인 ===== */
	@PostMapping("/checkID")
	@ResponseBody
	public Map<String, String> checkID(@RequestBody Map<String, String> map) {

		String userId = map.get("userid");
		String checkId = userId;

		Map<String, String> result = new HashMap<>();

		if (userId.equals(memberService.checkId(checkId))) {
			result.put("result", "error");
		} else if (userId != memberService.checkId(checkId)) {
			result.put("result", "success");
		}

		return result;

	}

	/* ===== 회원가입 기능 ===== */
	@PostMapping("/memberDo")
	public ResponseEntity<?> memberDo(@Validated UserDTO user, BindingResult bindingResult, Model model) {

		// bindingResult는 오류메세지를 확인할 수 있는 클래스
		if (bindingResult.hasErrors()) {

			// 에러메세지 로그 출력을 위해 StringBuilder 객체 생성해서 나중에 append 해주기
			StringBuilder sb = new StringBuilder();

			// for each문과 람다식으로 모든 메세지를 append 해서 로그 볼 수 있게 한다
			bindingResult.getAllErrors().forEach(objectError -> {

				FieldError field = (FieldError) objectError;
				String message = objectError.getDefaultMessage();

				System.out.println(field.getField() + ": " + message);

				sb.append(field.getField() + ": " + message + "\n" + "\n");

			});

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());

		}

		System.out.println("====== 가입자 정보 IN ======");
		System.out.println(user.getId());
		System.out.println(user.getPw());
		System.out.println(user.getName());
		System.out.println(user.getPhone());
		System.out.println(user.getEmail());
		System.out.println("====== 가입자 정보 END ======");
		boolean result = memberService.memberDo(user);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
