package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.BoardDTO;

@Mapper
public interface ReservationMapper {
	
	/* 예약저장 */
	void requestdo(BoardDTO boarddto);
	
	/* 전체 예약된 일자 가져오기 */
	List<String> getDay();
	
	/* 전체 예약일자, 예약시간 가져오기 */
	List<String> getDayTime();
	
	/* 해당 일자의 예약 정보 가져오기 */
	List<BoardDTO> getReservation(String date);
	
	/* 내예약 현황 가져오기 */
	List<BoardDTO> getMyReservation(String userId);
	
	/* 내예약 수정할때 나오는 내 예약 데이터 가져오기*/
	List<BoardDTO> getMyReservationUPDATE(String idx);
	
	/* 예약 수정하기 기능 */
	void updatedo(BoardDTO boarddto);
	
	/* 예약 취소 기능 */
	boolean deleteMyReservation(String idx);

}
