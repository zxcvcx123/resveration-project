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

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.NoticeDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.MainService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MainController {

	@Autowired
	private MainService mainService;

	/* ===== login 페이지 ===== */
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

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

		if (userId.equals(mainService.checkId(checkId))) {
			result.put("result", "error");
		} else if (userId != mainService.checkId(checkId)) {
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
		boolean result = mainService.memberDo(user);

		return new ResponseEntity<>(result, HttpStatus.OK);
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

	/* ===== 예약화면 ===== */
	// @AuthenticationPrincipal 어노테이션을 사용하여 인증된 사용자 정보를 직접 전달할 수 있습니다.
	@GetMapping("/reservation")
	public String reservationPage(BoardDTO boardDTO, @AuthenticationPrincipal PrincipalDetails principalDetails,
			Model model) {

		// 시큐리티에 위임된 세션 정보를 가져와서 변수에 담는다
		// 이걸가지고 ~님 방문을 환영합니다 같은 기능을 구현할 수 있다.
		String userId = principalDetails.getUserid();
		String userName = principalDetails.getUsername();

		List<String> getDay = mainService.getDay();

		model.addAttribute("getDay", getDay);
		model.addAttribute("userid" + userId);
		model.addAttribute("username" + userName);

		return "reservation";
	}

	/* ===== 예약등록 페이지 / 수정도 가능 ===== */
	@GetMapping("/request")
	public String requestPage(@RequestParam("saveMode") String saveMode,
			@RequestParam(value = "idx", required = false) String idx, Model model) {

		if (saveMode.equals("UPDATE")) {
			List<BoardDTO> myData = mainService.getMyReservationUPDATE(idx);
			model.addAttribute("myData", myData);
		}

		List<String> getDayTime = mainService.getDayTime();

		model.addAttribute("getDayTime", getDayTime);
		model.addAttribute("saveMode", saveMode);

		return "request";
	}

	/* ===== 예약등록기능 ===== */
	// @AuthenticationPrincipal 어노테이션을 사용하여 인증된 사용자 정보를 직접 전달할 수 있습니다.
	@PostMapping("/request/requestdo")
	public String requestdo(BoardDTO boardDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		System.out.println("================예약 데이터 시작================");
		System.out.println("@@@@@@@@@@@@@@@@@@@@: " + boardDTO.getSaveMode());
		System.out.println("@@@@@@@@@@@@@@이름: " + boardDTO.getName());
		System.out.println("@@@@@@@@@@@@@@연락처: " + boardDTO.getPhone());
		System.out.println("@@@@@@@@@@@@@@인원수: " + boardDTO.getCount());
		System.out.println("@@@@@@@@@@@@@@예약일: " + boardDTO.getDay());
		System.out.println("@@@@@@@@@@@@@@예약시간: " + boardDTO.getTime());
		System.out.println("@@@@@@@@@@@@@@촬영타입: " + boardDTO.getType());
		System.out.println("================예약 데이터 끝================");

		mainService.requestdo(boardDTO, principalDetails);

		return "redirect:/reservation";
	}

	/* ===== 일자별 예약 현황 화면 보기 ===== */
	@GetMapping("/reservation/{date}")
	public String reservation_status(@PathVariable("date") String date, Model model) {

		List<BoardDTO> getReserv = mainService.getReservation(date);

		model.addAttribute("getReserv", getReserv);

		return "reservation_status";
	}

	/* ===== 내 예약화면 ===== */
	@GetMapping("/reservation/myreservation")
	public String myreservation(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

		String userId = principalDetails.getUserid();

		List<BoardDTO> myData = mainService.getMyReservation(userId);

		model.addAttribute("myData", myData);

		return "myreservation";
	}

	/* ===== 예약 취소 기능 ===== */
	@PostMapping("/reservation/myreservation/delete")
	public String delete(@RequestBody Map<String, String> map) {

		String idx = map.get("idx");

		mainService.deleteMyReservation(idx);

		return "/myreservation";
	}

	/* ===== 오시는길 ===== */
	@GetMapping("/welcome")
	public String welcomePage() {
		return "welcome";
	}

	/* ===== 게시판(공지, 자유, 문의사항 등등) ===== */
	@GetMapping("/notice")
	public String notice(PageDTO pageDto, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) throws SQLException {

		// 유저 권한 확인 (admin => 작성자 이름 다보임 , guest => 작성자 이름 가려짐)
		if (principalDetails == null) {
			String role = "outSide";
			model.addAttribute("role", role);
		} else {
			model.addAttribute("role", principalDetails.getUserRole());
		}

		/* 페이징 처리 관련 부분 시작 */
		// 게시물 가져올 때 Limit n, m 에서 n 부분 설정
		// (현재 페이지 -1) * 몇 개로 나눠 보여줄건지
		// 계산식: (nowPage -1) * limitNoticeList
		pageDto.setLimitNoticeNowPage((pageDto.getPage() -1) * pageDto.getLimitNoticeList());
		
		// 게시물 가져오기
		List<NoticeDTO> getNoticeList = mainService.getNoticeList(pageDto);
		
		// 전체 게시물 수량
		String field = pageDto.getField();
		Integer totalList = mainService.noticeTotal(field);

		// 나눌 페이지 수
		Integer slicePage = pageDto.getSlicePage();

		// 총 페이지수
		// 계산식: (전체 게시물 수량(totalList) / 나눌 페이지 수(slicePage)) + 1
		Integer totalPage = (totalList / slicePage) + 1;
		
		// 페이지 시작점
		// n = nowPage m = slicePage
		// 계산식: (n-1)/m * m + 1
		Integer startPage = (pageDto.getPage() - 1) / slicePage * slicePage + 1;

		// 페이지 끝점
		// startPage + (m-1)
		Integer endPage = startPage + (slicePage - 1);
		
		// 현재 페이지
		Integer nowPage = pageDto.getPage();
		
		// 마지막 페이지
		// 계산식: ((totalList-1) / slicePage) + 1 
		Integer lastPage = ((totalList-1) / slicePage) + 1; 
		
		// 마지막 페이지 번호 이후 안나오게 해야해서 min으로 처리
		endPage = Math.min(endPage, lastPage);
		

		System.out.println("@@@@@@@@@@@@@@@@field: " + pageDto.getField());
		System.out.println("@@@@@@@@@@@@@@@@keyword: " + pageDto.getKeyword());
		
		model.addAttribute("list", getNoticeList); // 게시물
		model.addAttribute("totalList", totalList); // 총 게시물 수
		model.addAttribute("totalPage", totalPage); // 총 페이지 수
		model.addAttribute("startPage", startPage); // 페이지 시작점
		model.addAttribute("endPage", endPage); // 페이지 끝점
		model.addAttribute("nowPage", nowPage); // 현재 페이지
		model.addAttribute("lastPage", lastPage); // 마지막 페이지

		return "notice";
	}

	/* ===== 게시판 글 작성 페이지 ===== */
	@GetMapping("/noticeWrite")
	public String noticeWrite(@RequestParam("saveMode") String saveMode,
			@RequestParam(value = "idx", required = false) String idx, NoticeDTO noticeDTO,
			@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

		// 유저 권한 확인 (글 작성시 admin => 공지사항 등록 가능 guest => 공지사항 등록 불가)
		String getUserRole = principalDetails.getUserRole();

		if (saveMode.equals("UPDATE")) {
			Map<String, Object> notice = mainService.getIdxNotice(idx);
			model.addAttribute("notice", notice);
		} else {
			model.addAttribute("notice", noticeDTO);
		}

		model.addAttribute("role", getUserRole);
		model.addAttribute("idx", idx);
		model.addAttribute("saveMode", saveMode);

		return "noticeWrite";
	}

	/* ===== 게시판 글 작성 기능 (작성, 수정 포함) ===== */
	@PostMapping("/noticeWriteDo")
	public String noticeWriteDo(NoticeDTO notice, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		String getUserId = principalDetails.getUserid();
		String getUserName = principalDetails.getUsername();

		notice.setWriter(getUserName);
		notice.setWriterid(getUserId);

		System.out.println("@@@@@@@@@@@@@@@@WriteDo UserId: " + getUserId);
		System.out.println("@@@@@@@@@@@@@@@@WriteDo UserId: " + getUserName);

		if (notice.getSaveMode().equals("INSERT")) {

			mainService.noticeWriteDo(notice);
			System.out.println("@@@@@@@@ ====== 게시글이 새로 등록 됐습니다. ====== @@@@@@@@");

		} else if (notice.getSaveMode().equals("UPDATE")) {

			mainService.noticeUpdate(notice);
			System.out.println("@@@@@@@@ ====== 게시글이 업데이트 됐습니다. ====== @@@@@@@@");
		}

		return "redirect:/notice";
	}

	/* ===== 게시판 글 내용보기 ===== */
	@GetMapping("/notice/{idx}")
	public String noticePage(@PathVariable("idx") String idx, Model model) {
		
		Map<String, Object> getIdxNotice = mainService.getIdxNotice(idx);
		model.addAttribute("notice", getIdxNotice);
		
		return "noticePage";
	}

	/* ===== 게시판 글 삭제하기 ===== */
	@GetMapping("/notice/deleteNotice/{idx}")
	public String noticeDelete(@PathVariable("idx") String idx) {

		mainService.noticeDelete(idx);
		System.out.println("====== 삭제된 게시글 정보 시작 =======");
		System.out.println("====== " + idx + "번 게시글 삭제 =======");
		System.out.println("====== 삭제된 게시글 정보 끝 =======");

		return "redirect:/notice";
	}

}
