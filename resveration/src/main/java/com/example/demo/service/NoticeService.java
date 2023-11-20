package com.example.demo.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.CommentLikeDTO;
import com.example.demo.dto.CommentPageDTO;
import com.example.demo.dto.NoticeDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.mapper.NoticeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {
	
	private final NoticeMapper noticeMapper;
	
	
	/* 게시판 글쓰기 기능 */
	public void noticeWriteDo(NoticeDTO notice) {
		LocalDateTime today = LocalDateTime.now();

		notice.setDate(today.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));

		System.out.println("@@@@@게시글 작성 시간: " + today.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
		noticeMapper.noticeWriteDo(notice);

	}

	/* 특정(idx기준) 게시글 내용 가져오기 */
	public Map<String, Object> getIdxNotice(String idx) {
		return noticeMapper.getIdxNotice(idx);
	}

	/* 게시판 글 업데이트 */
	public void noticeUpdate(NoticeDTO notice) {

		LocalDateTime today = LocalDateTime.now();
		notice.setModifydate(today.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")));
		noticeMapper.noticeUpdate(notice);
	}

	/* 게시판 글 삭제  */
	public void noticeDelete(String idx) {
		noticeMapper.noticeDelete(idx);
	}
	
	/* 조회수 증가 */
	public void viewCount(String idx) {
		noticeMapper.viewCount(idx);
	}

	/* ===== 페이징 처리 관련 ===== */
	/* 게시판 글 목록 가져오기 */
	public List<NoticeDTO> getNoticeList(PageDTO pageDto) throws SQLException {
		
		return noticeMapper.getNoticeList(pageDto);
	}

	/* 게시판 글 Total 개수 */
	public Integer noticeTotal(PageDTO pageDto) {
		
		return noticeMapper.noticeTotal(pageDto);
	}
	
	/* ===== 댓글 관련 ===== */
	/* 댓글 쓰기 */
	public void commentWrite(CommentDTO comment, Integer idx, String writerid, String writer) {
		
		comment.setNotice_idx(idx);
		comment.setWriter(writer);
		comment.setWriterid(writerid);
		
		
		noticeMapper.commentWrite(comment);
	}
	
	/* 댓글 가져오기 */
	public List<Map<String, Object>> getComment(CommentPageDTO commentPageDTO){
		commentPageDTO.setLimitNoticeNowPage((commentPageDTO.getPage()-1) * commentPageDTO.getLimitNoticeList());
		return noticeMapper.getComment(commentPageDTO);
	}
	
	/* 해당 페이지의 댓글 전체 수 가져오기 */
	public Integer getCommentTotal(CommentPageDTO commentPageDTO) {
		return noticeMapper.getCommentTotal(commentPageDTO);
	}
	
	/* 댓글 좋아요 */
	public boolean commentLike(CommentLikeDTO like, PrincipalDetails principalDetails) {
		
		like.setUser_id(principalDetails.getUserid());
		
		
		System.out.println("@@@@@ "+ like.getUser_id() +"님이 " + like.getNotice_idx()+"번 게시물의 " + like.getComment_idx() + "번 댓글에 좋아요 눌렀습니다. @@@@@");
		
		// 좋아요 눌렀을 때 삭제 할 데이터 가 없으면 좋아요 된거
		if(noticeMapper.commentLikeDelete(like) == 0) {
			return noticeMapper.commentLike(like) == 1;
		} else {
			// 눌렀을 때 삭제 되면 좋아요 취소 된거
			return noticeMapper.commentLikeDelete(like) == 0;
		}
		
		
	}
	

}
