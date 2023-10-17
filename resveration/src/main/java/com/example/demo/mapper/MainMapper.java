package com.example.demo.mapper;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.NoticeDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.UserDTO;

@Mapper
public interface MainMapper {
	
	/* 회원가입 */
	boolean memberDo(UserDTO user);
	
	/* 로그인 */
	UserDTO searchId(String userId);
	
	/* 아이디 중복확인 */
	String checkId(String checkId);
	
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
	
	/* 게시판 글 쓰기 기능 */
	void noticeWriteDo(NoticeDTO notice);
	
	/* 게시판 글 업데이트 */
	void noticeUpdate(NoticeDTO notice);
	
	/* 특정(idx기준) 게시판 글 내용 가져오기 */
	Map<String, Object> getIdxNotice(String idx);
	
	/* 게시판 글 삭제 */
	void noticeDelete(String idx);
	
	
	
	/* === 페이징 처리 관련 === */
	
	/* 게시판 글 가져오기 */
	List<NoticeDTO> getNoticeList(PageDTO pageDto) throws SQLException;
	
	/* total 게시글 수 */
	Integer noticeTotal(PageDTO pageDto);
	
	/* === 댓글 관련 === */
	
	/* 댓글 작성 */
	void commentWrite(CommentDTO comment);
	
	/* 댓글 가져오기 */
	List<Map<String, Object>>getComment(String idx);
	
}
