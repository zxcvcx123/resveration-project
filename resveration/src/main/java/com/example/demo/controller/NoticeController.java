package com.example.demo.controller;

import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.CommentPageDTO;
import com.example.demo.dto.NoticeDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NoticeController {
	
	private final NoticeService noticeService;
	
	/* ===== 게시판(공지, 자유, 문의사항 등등) ===== */
	@GetMapping("/notice")
	public String notice(PageDTO pageDto, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model)
			throws SQLException {

		// 유저 권한 확인 (admin => 작성자 이름 다보임 , guest => 작성자 이름 가려짐)
		if (principalDetails == null) {
			String role = "outSide";
			model.addAttribute("role", role);
		} else {
			model.addAttribute("userId", principalDetails.getUserid());
			model.addAttribute("role", principalDetails.getUserRole());

			System.out.println("@@@@@@@@@@@@@@접속자 ID: " + principalDetails.getUserid());
		}

		/* 페이징 처리 관련 부분 시작 */
		// 게시물 가져올 때 Limit n, m 에서 n 부분 설정
		// (현재 페이지 -1) * 몇 개로 나눠 보여줄건지
		// 계산식: (nowPage -1) * limitNoticeList
		pageDto.setLimitNoticeNowPage((pageDto.getPage() - 1) * pageDto.getLimitNoticeList());

		// 게시물 가져오기
		List<NoticeDTO> getNoticeList = noticeService.getNoticeList(pageDto);

		// 전체 게시물 수량

		System.out.println("@@@@@@@@@@@@@@@@field: " + pageDto.getField());
		System.out.println("@@@@@@@@@@@@@@@@keyword: " + pageDto.getKeyword());

		Integer totalList = noticeService.noticeTotal(pageDto);

		// 나눌 페이지 수
		Integer slicePage = pageDto.getSlicePage();

		// 총 페이지수
		// 계산식: (전체 게시물 수량(totalList) / 나눌 페이지 수(slicePage)) + 1
		Integer totalPage = (totalList / slicePage) + 1;

		// 페이지 시작점
		// n = nowPage m = slicePage
		// 계산식: (n-1)/m * m + 1
		Integer startPage = (pageDto.getPage() - 1) / slicePage * slicePage + 1;
		System.out.println("@@@@@@@@@@@현재 페이지: " + (pageDto.getPage() - 1));
		System.out.println("@@@@@@@@@@@@@나눌 페이지: " + (slicePage * slicePage));
		System.out.println("@@@@@@@@@@@@@@페이지 시작점: " + startPage);

		// 페이지 끝점
		// startPage + (m-1)
		Integer endPage = startPage + (slicePage - 1);

		// 현재 페이지
		Integer nowPage = pageDto.getPage();

		// 마지막 페이지
		// 계산식: ((totalList-1) / slicePage) + 1
		Integer lastPage = ((totalList - 1) / slicePage) + 1;

		// 마지막 페이지 번호 이후 안나오게 해야해서 min으로 처리
		endPage = Math.min(endPage, lastPage);

		model.addAttribute("list", getNoticeList); // 게시물
		model.addAttribute("totalList", totalList); // 총 게시물 수
		model.addAttribute("totalPage", totalPage); // 총 페이지 수
		model.addAttribute("startPage", startPage); // 페이지 시작점
		model.addAttribute("endPage", endPage); // 페이지 끝점
		model.addAttribute("nowPage", nowPage); // 현재 페이지
		model.addAttribute("lastPage", lastPage); // 마지막 페이지
		model.addAttribute("field", pageDto.getField()); // 게시판 타입
		model.addAttribute("keyword", pageDto.getKeyword()); // 검색어

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
			Map<String, Object> notice = noticeService.getIdxNotice(idx);
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

			noticeService.noticeWriteDo(notice);
			System.out.println("@@@@@@@@ ====== 게시글이 새로 등록 됐습니다. ====== @@@@@@@@");

		} else if (notice.getSaveMode().equals("UPDATE")) {

			noticeService.noticeUpdate(notice);
			System.out.println("@@@@@@@@ ====== 게시글이 업데이트 됐습니다. ====== @@@@@@@@");
		}

		return "redirect:/notice";
	}

	/* ===== 게시판 글 내용보기 ===== */
	@GetMapping("/notice/{idx}")
	public String noticePage(@PathVariable("idx") String idx,
							@RequestParam(value="cp", defaultValue = "1")Integer cp,
							@AuthenticationPrincipal PrincipalDetails principalDetails, 
							CommentPageDTO commentPageDTO, 
							Model model) {

		// 유저 권한 확인 (admin => 작성자 이름 다보임 , guest => 작성자 이름 가려짐)
		if (principalDetails == null) {
			String role = "outSide";
			String userId = "oS";
			
			System.out.println("@@@@@@@ 권한 확인: " + role +" / " + userId);
			model.addAttribute("userId", userId);
			model.addAttribute("role", role);
		} else {
			model.addAttribute("role", principalDetails.getUserRole());
			model.addAttribute("userId", principalDetails.getUserid());
		}

		// 게시물 내용 가져오기
		Map<String, Object> getIdxNotice = noticeService.getIdxNotice(idx);

		/* @@@ 댓글 영역 @@@ */
		// 댓글 가져올 게시판의 id
		commentPageDTO.setNotice_idx(idx);
		
		// 현재 댓글 페이지 세팅
		commentPageDTO.setPage(cp);
		
		// 댓글 가져오기
		List<Map<String, Object>> getComment = noticeService.getComment(commentPageDTO);

		// 댓글 전체 수
		Integer totalComment = noticeService.getCommentTotal(commentPageDTO);

		// 댓글의 페이지 수
		Integer totalPage = (totalComment / commentPageDTO.getSlicePage() + 1);

		// 댓글 페이지의 시작점
		Integer startPage = (commentPageDTO.getPage() - 1) / commentPageDTO.getSlicePage()
				* commentPageDTO.getSlicePage() + 1;

		// 댓글 페이지의 끝점
		Integer endPage = startPage + (commentPageDTO.getSlicePage() - 1);

		// 마지막 페이지 번호
		Integer lastPage = ((totalComment - 1) / commentPageDTO.getSlicePage()) + 1;

		// 마지막 페이지 번호 이후 안나오게 하기 위해서
		endPage = Math.min(endPage, lastPage);

		// 조회수 증가
		noticeService.viewCount(idx);
		
		model.addAttribute("idx", idx); // 현재 게시물이 몇 페이지인지
		model.addAttribute("comment", getComment); // 댓글 내용 (10개씩 보여주기)
		model.addAttribute("notice", getIdxNotice); // 댓글가져올 게시판의 id 값
		model.addAttribute("totalComment", totalComment); // 댓글 전체 개수
		model.addAttribute("totalPage", totalPage); // 댓글의 전체 페이지 수
		model.addAttribute("startPage", startPage); // 댓글 페이지 시작점
		model.addAttribute("endPage", endPage); // 댓글 페이지의 끝점
		model.addAttribute("lastPage", lastPage); // 댓글 페이지의 마지막 번호

		return "noticePage";
	}

//	/* ===== 댓글 페이징 / 현재 페이지 마다 목록 새로 가져오기 ===== */
//	@PostMapping("/notice/{idx}")
//	public ResponseEntity<List<Map<String, Object>>> commentPagingViewContent(
//													@PathVariable("idx") String idx,
//													@RequestBody Map<String, Object> map,
//													CommentPageDTO commentPageDTO, 
//													Model model) {
//		System.out.println("@@@@@@@@@@@@@@@데이터확인: " + idx);
//		/* @@@ 댓글 영역 @@@ */
//		// 댓글 가져올 게시판의 id
//		commentPageDTO.setNotice_idx(idx);
//		
//		// 댓글 현재 페이지
//		commentPageDTO.setPage(Integer.valueOf((String) map.get("cnum")));
//
//		// 댓글 가져오기
//		List<Map<String, Object>> getComment = noticeService.getComment(commentPageDTO);
//
//		model.addAttribute("endPage", 6); // 댓글 페이지의 끝점
//		return new ResponseEntity<List<Map<String,Object>>>(getComment, HttpStatus.OK);
//	}

	/* ===== 게시판 글 삭제하기 ===== */
	@GetMapping("/notice/deleteNotice/{idx}")
	public String noticeDelete(@PathVariable("idx") String idx) {

		noticeService.noticeDelete(idx);
		System.out.println("====== 삭제된 게시글 정보 시작 =======");
		System.out.println("====== " + idx + "번 게시글 삭제 =======");
		System.out.println("====== 삭제된 게시글 정보 끝 =======");

		return "redirect:/notice";
	}

	/* ===== 댓글 작성하기 ===== */
	@PostMapping("/notice/{idx}/comment")
	public ResponseEntity<?> commentWrite(@PathVariable("idx") Integer idx,
			@AuthenticationPrincipal PrincipalDetails principalDetails, CommentPageDTO commentPageDTO,
			@RequestBody CommentDTO commentDto) {

		String writerid = principalDetails.getUserid();
		String writer = principalDetails.getUsername();

		noticeService.commentWrite(commentDto, idx, writerid, writer);

		commentPageDTO.setNotice_idx(String.valueOf(idx));
		List<Map<String, Object>> result = noticeService.getComment(commentPageDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		

		return new ResponseEntity<>(result, headers, HttpStatus.OK);

	}
	
	/* ===== 댓글 좋아요 ===== */
	@PostMapping("/api/comment/likeup")
	public ResponseEntity<?> commentLikeUp(@RequestBody Map<String, Object> map) {
		System.out.println("@@@@@ " + map.get("idx")+"번 댓글 좋아요 눌렀습니다. @@@@@");
		
		String likeCount = noticeService.getCommentLikeCount(map.get("id"));
		
		return ResponseEntity.ok().build();
	}
}
