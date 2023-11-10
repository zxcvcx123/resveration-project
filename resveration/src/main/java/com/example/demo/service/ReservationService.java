package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.dto.BoardDTO;
import com.example.demo.mapper.ReservationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
	
	private final ReservationMapper reservationMapper;
	
	
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
			reservationMapper.requestdo(boardDTO);
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
			reservationMapper.updatedo(boardDTO);
		}

	}

	/* 전체 예약한 일자 가져오기 */
	public List<String> getDay() {
		return reservationMapper.getDay();
	}
	
	/* 예약일에 어떤 시간에 예약되어있는지 */
	public List<String> getDayTime() {
		return reservationMapper.getDayTime();
	}

	/* 해당 일자에 예약정보 가져오기 */
	public List<BoardDTO> getReservation(String date) {
		return reservationMapper.getReservation(date);
	}

	/* 내 예약현황 가져오기 */
	public List<BoardDTO> getMyReservation(String userId) {
		return reservationMapper.getMyReservation(userId);
	}

	/* 내가 예약한 정보 수정하기 */
	public List<BoardDTO> getMyReservationUPDATE(String idx) {
		return reservationMapper.getMyReservationUPDATE(idx);
	}

	/* 예약 취소 기능 */
	public boolean deleteMyReservation(String idx) {
		return reservationMapper.deleteMyReservation(idx);
	}

}
