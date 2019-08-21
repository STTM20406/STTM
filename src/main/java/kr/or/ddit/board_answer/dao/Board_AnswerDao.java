package kr.or.ddit.board_answer.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.board_answer.model.Board_AnswerVo;

@Repository
public class Board_AnswerDao implements IBoard_AnswerDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	
	/**
	 * Method 		: insertReply
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param replyVo
	 * @return
	 * Method 설명 	: 댓글 작성
	 */
	@Override
	public int insertReply(Board_AnswerVo replyVo) {
		return sqlSession.insert("board.insertReply",replyVo);
	}

	/**
	 * Method 		: deleteReply
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param replyVo
	 * @return
	 * Method 설명 	: 댓글 삭제
	 */
	@Override
	public int deleteReply(int comm_id) {
		return sqlSession.delete("board.deleteReply",comm_id);
	}

	/**
	 * Method 		: updateReply
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param replyVo
	 * @return
	 * Method 설명 	: 댓글 수정
	 */
	@Override
	public int updateReply(Board_AnswerVo replyVo) {
		return sqlSession.update("board.updateReply",replyVo);
	}

	/**
	 * Method 		: replyCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param reply_id
	 * @return
	 * Method 설명 	: 게시판별 댓글 개수
	 */
	@Override
	public int replyCnt(int write_id) {
		return sqlSession.selectOne("board.replyCnt",write_id);
	}

	/**
	 * Method 		: replyList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param write_id
	 * @return
	 * Method 설명 	: 게시글별 댓글리스트조회
	 */
	@Override
	public List<Board_AnswerVo> replyList(int write_id) {
		return sqlSession.selectList("board.replyList",write_id);
	}

	/**
	 * Method 		: myReplyList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-01 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 내가 작성한 댓글 리스트
	 */
	@Override
	public List<Board_AnswerVo> myReplyList(String user_email) {
		return sqlSession.selectList("board.myReplyList",user_email);
	}



}
