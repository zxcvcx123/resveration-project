package com.example.demo.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.CommentPageDTO;
import com.example.demo.dto.NoticeDTO;
import com.example.demo.dto.PageDTO;

@Mapper
public interface NoticeMapper {
	
	/* === 게시판 관련 === */
	
	/* 게시판 글 쓰기 기능 */
	void noticeWriteDo(NoticeDTO notice);
	
	/* 게시판 글 업데이트 */
	void noticeUpdate(NoticeDTO notice);
	
	/* 특정(idx기준) 게시판 글 내용 가져오기 */
	Map<String, Object> getIdxNotice(String idx);
	
	/* 게시판 글 삭제 */
	void noticeDelete(String idx);
	
	/* 조회수 증가 */
	void viewCount(String idx);
	
	
	/* === 페이징 처리 관련 === */
	
	/* 게시판 글 가져오기 */
	List<NoticeDTO> getNoticeList(PageDTO pageDto) throws SQLException;
	
	/* total 게시글 수 */
	Integer noticeTotal(PageDTO pageDto);
	
	/* === 댓글 관련 === */
	
	/* 댓글 작성 */
	void commentWrite(CommentDTO comment);
	
	/* 댓글 가져오기 */
	List<Map<String, Object>>getComment(CommentPageDTO commentPageDTO);
	
	/* 해당 페이지의 댓글 전체 개수 가져오기 */
	Integer getCommentTotal(CommentPageDTO commentPageDTO);
	

}
