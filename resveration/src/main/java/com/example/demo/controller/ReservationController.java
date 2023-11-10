package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.dto.BoardDTO;
import com.example.demo.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservationController {
	
	private final ReservationService reservationService;
	
	/* ===== 예약화면 ===== */
	// @AuthenticationPrincipal 어노테이션을 사용하여 인증된 사용자 정보를 직접 전달할 수 있습니다.
	@GetMapping("/reservation")
	public String reservationPage(BoardDTO boardDTO, @AuthenticationPrincipal PrincipalDetails principalDetails,
			Model model) {

		// 시큐리티에 위임된 세션 정보를 가져와서 변수에 담는다
		// 이걸가지고 ~님 방문을 환영합니다 같은 기능을 구현할 수 있다.
		String userId = principalDetails.getUserid();
		String userName = principalDetails.getUsername();

		List<String> getDay = reservationService.getDay();

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
			List<BoardDTO> myData = reservationService.getMyReservationUPDATE(idx);
			model.addAttribute("myData", myData);
		}

		List<String> getDayTime = reservationService.getDayTime();

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

		reservationService.requestdo(boardDTO, principalDetails);

		return "redirect:/reservation";
	}

	/* ===== 일자별 예약 현황 화면 보기 ===== */
	@GetMapping("/reservation/{date}")
	public String reservation_status(@PathVariable("date") String date,
			@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

		// 유저 권한 확인 (admin => 작성자 이름 다보임 , guest => 작성자 이름 가려짐)
		if (principalDetails == null) {
			String role = "outSide";
			model.addAttribute("role", role);
		} else {
			model.addAttribute("userId", principalDetails.getUserid());
			model.addAttribute("role", principalDetails.getUserRole());
			System.out.println("@@@@@@@@@@@@@권한: " + principalDetails.getUserRole());
		}

		List<BoardDTO> getReserv = reservationService.getReservation(date);
		System.out.println("@@@@@@@@@@@@@@@@@@@예약 id: " + getReserv.get(0).getBoardid());
		model.addAttribute("getReserv", getReserv);

		return "reservation_status";
	}

	/* ===== 내 예약화면 ===== */
	@GetMapping("/reservation/myreservation")
	public String myreservation(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

		String userId = principalDetails.getUserid();

		List<BoardDTO> myData = reservationService.getMyReservation(userId);

		model.addAttribute("myData", myData);

		return "myreservation";
	}

	/* ===== 예약 취소 기능 ===== */
	@PostMapping("/reservation/myreservation/delete")
	public String delete(@RequestBody Map<String, String> map) {

		String idx = map.get("idx");

		reservationService.deleteMyReservation(idx);

		return "/myreservation";
	}
	
}
