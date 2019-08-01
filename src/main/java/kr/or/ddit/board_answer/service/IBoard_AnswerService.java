package kr.or.ddit.board_answer.service;

import java.util.List;

import kr.or.ddit.board_answer.model.Board_AnswerVo;

public interface IBoard_AnswerService {

	/**
	 * Method 		: insertReply
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param replyVo
	 * @return
	 * Method 설명 	: 댓글 작성
	 */
	int insertReply(Board_AnswerVo replyVo);
	
	/**
	 * Method 		: deleteReply
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param replyVo
	 * @return
	 * Method 설명 	: 댓글 삭제
	 */
	int deleteReply(int comm_id);
	
	/**
	 * Method 		: updateReply
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param replyVo
	 * @return
	 * Method 설명 	: 댓글 수정
	 */
	int updateReply(Board_AnswerVo replyVo);
	
	/**
	 * Method 		: replyCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param reply_id
	 * @return
	 * Method 설명 	: 게시판별 댓글 개수
	 */
	int replyCnt(int write_id);
	
	/**
	 * Method 		: replyList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param write_id
	 * @return
	 * Method 설명 	: 게시글별 댓글리스트조회
	 */
	List<Board_AnswerVo> replyList(int write_id);
	
	/**
	 * Method 		: myReplyList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-01 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 내가 작성한 댓글 리스트
	 */
	List<Board_AnswerVo> myReplyList(String user_email);
		
}
