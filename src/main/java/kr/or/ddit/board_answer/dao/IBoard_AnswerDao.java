package kr.or.ddit.board_answer.dao;

import java.util.List;

import kr.or.ddit.board_answer.model.Board_AnswerVo;

public interface IBoard_AnswerDao {
	
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
	int deleteReply(Board_AnswerVo replyVo);
	
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
	
	
	
	
	
}
