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

		mainMapper.memberDo(user);

		return true;
	};

	/*  아이디 중복확인 */
	public String checkId(String checkId) {
		System.out.println("@@@@ 들어가는중: " + checkId);
		System.out.println("@@@@ 나오는중: " + mainMapper.checkId(checkId));
		return mainMapper.checkId(checkId);
	}

	/* 예약저장*/
	public void requestdo(BoardDTO boardDTO, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		/* 작성자 ID */
		boardDTO.setBoardid(principalDetails.getUserid());

		/* boarddate / 등록시간 set 해주기 */
		boardDTO.setBoarddate(LocalDateTime.now());

		/* 예약시간 시간별로 타입 넣어주기 */
		if (boardDTO.getTime().equals("09:00~09:30")) {
			boardDTO.setTimetype("1");
		} else if (boardDTO.getTime().equals("09:30~10:00")) {
			boardDTO.setTimetype("2");
		} else if (boardDTO.getTime().equals("10:00~10:30")) {
			boardDTO.setTimetype("3");
		} else if (boardDTO.getTime().equals("10:30~11:00")) {
			boardDTO.setTimetype("4");
		} else if (boardDTO.getTime().equals("11:00~11:30")) {
			boardDTO.setTimetype("5");
		} else if (boardDTO.getTime().equals("11:30~12:00")) {
			boardDTO.setTimetype("6");
		} else if (boardDTO.getTime().equals("13:00~13:30")) {
			boardDTO.setTimetype("7");
		} else if (boardDTO.getTime().equals("13:30~14:00")) {
			boardDTO.setTimetype("8");
		} else if (boardDTO.getTime().equals("14:00~14:30")) {
			boardDTO.setTimetype("9");
		} else if (boardDTO.getTime().equals("14:30~15:00")) {
			boardDTO.setTimetype("10");
		} else if (boardDTO.getTime().equals("15:00~15:30")) {
			boardDTO.setTimetype("11");
		} else if (boardDTO.getTime().equals("15:30~16:00")) {
			boardDTO.setTimetype("12");
		} else if (boardDTO.getTime().equals("16:00~16:30")) {
			boardDTO.setTimetype("13");
		} else if (boardDTO.getTime().equals("16:30~17:00")) {
			boardDTO.setTimetype("14");
		} else if (boardDTO.getTime().equals("17:30~18:00")) {
			boardDTO.setTimetype("15");
		}

		if (boardDTO.getIdx() == null) {
			mainMapper.requestdo(boardDTO);
		} else if (boardDTO.getIdx() != null) {

			/* 예약시간 시간별로 타입 넣어주기 */
			if (boardDTO.getTime().equals("09:00~09:30")) {
				boardDTO.setTimetype("1");
			} else if (boardDTO.getTime().equals("09:30~10:00")) {
				boardDTO.setTimetype("2");
			} else if (boardDTO.getTime().equals("10:00~10:30")) {
				boardDTO.setTimetype("3");
			} else if (boardDTO.getTime().equals("10:30~11:00")) {
				boardDTO.setTimetype("4");
			} else if (boardDTO.getTime().equals("11:00~11:30")) {
				boardDTO.setTimetype("5");
			} else if (boardDTO.getTime().equals("11:30~12:00")) {
				boardDTO.setTimetype("6");
			} else if (boardDTO.getTime().equals("13:00~13:30")) {
				boardDTO.setTimetype("7");
			} else if (boardDTO.getTime().equals("13:30~14:00")) {
				boardDTO.setTimetype("8");
			} else if (boardDTO.getTime().equals("14:00~14:30")) {
				boardDTO.setTimetype("9");
			} else if (boardDTO.getTime().equals("14:30~15:00")) {
				boardDTO.setTimetype("10");
			} else if (boardDTO.getTime().equals("15:00~15:30")) {
				boardDTO.setTimetype("11");
			} else if (boardDTO.getTime().equals("15:30~16:00")) {
				boardDTO.setTimetype("12");
			} else if (boardDTO.getTime().equals("16:00~16:30")) {
				boardDTO.setTimetype("13");
			} else if (boardDTO.getTime().equals("16:30~17:00")) {
				boardDTO.setTimetype("14");
			} else if (boardDTO.getTime().equals("17:30~18:00")) {
				boardDTO.setTimetype("15");
			}
			mainMapper.updatedo(boardDTO);
		}

	}

	/* 전체 예약한 일자 가져오기 */
	public List<String> getDay() {
		return mainMapper.getDay();
	}
	
	/* 예약일에 어떤 시간에 예약되어있는지 */
	public List<String> getDayTime() {
		return mainMapper.getDayTime();
	}

	/* 해당 일자에 예약정보 가져오기 */
	public List<BoardDTO> getReservation(String date) {
		return mainMapper.getReservation(date);
	}

	/* 내 예약현황 가져오기 */
	public List<BoardDTO> getMyReservation(String userId) {
		return mainMapper.getMyReservation(userId);
	}

	/* 내가 예약한 정보 수정하기 */
	public List<BoardDTO> getMyReservationUPDATE(String idx) {
		return mainMapper.getMyReservationUPDATE(idx);
	}

	/* 예약 취소 기능 */
	public boolean deleteMyReservation(String idx) {
		return mainMapper.deleteMyReservation(idx);
	}

	/* 게시판 글쓰기 기능 */
	public void noticeWriteDo(NoticeDTO notice) {
		LocalDateTime today = LocalDateTime.now();

		notice.setDate(today.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));

		System.out.println("@@@@@게시글 작성 시간: " + today.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
		mainMapper.noticeWriteDo(notice);

	}

	/* 특정(idx기준) 게시글 내용 가져오기 */
	public Map<String, Object> getIdxNotice(String idx) {
		return mainMapper.getIdxNotice(idx);
	}

	/* 게시판 글 업데이트 */
	public void noticeUpdate(NoticeDTO notice) {

		LocalDateTime today = LocalDateTime.now();
		notice.setModifydate(today.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
		mainMapper.noticeUpdate(notice);
	}

	/* 게시판 글 삭제  */
	public void noticeDelete(String idx) {
		mainMapper.noticeDelete(idx);
	}
	
	/* 조회수 증가 */
	public void viewCount(String idx) {
		mainMapper.viewCount(idx);
	}

	/* ===== 페이징 처리 관련 ===== */
	/* 게시판 글 목록 가져오기 */
	public List<NoticeDTO> getNoticeList(PageDTO pageDto) throws SQLException {
		
		return mainMapper.getNoticeList(pageDto);
	}

	/* 게시판 글 Total 개수 */
	public Integer noticeTotal(PageDTO pageDto) {
		
		return mainMapper.noticeTotal(pageDto);
	}
	
	/* ===== 댓글 관련 ===== */
	/* 댓글 쓰기 */
	public void commentWrite(CommentDTO comment, Integer idx, String writerid, String writer) {
		
		comment.setNotice_idx(idx);
		comment.setWriter(writer);
		comment.setWriterid(writerid);
		
		
		mainMapper.commentWrite(comment);
	}
	
	/* 댓글 가져오기 */
	public List<Map<String, Object>> getComment(String idx){
		return mainMapper.getComment(idx);
	}
}
