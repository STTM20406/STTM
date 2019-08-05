package kr.or.ddit.likes.service;

import java.util.Map;

import kr.or.ddit.board_write.model.Board_WriteVo;

public interface ILikesService {
	/**
	 * Method 		: likeCheck
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @param like
	 * @return
	 * Method 설명 	: 게시글 좋아요 중복 검사
	 */
	int likeCheck(Map<String, Object> like);
	
	/**
	 * Method 		: likeAdd
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @param writeVo
	 * @return
	 * Method 설명 	: 게시글 좋아요 추가
	 */
	int likeAdd(Board_WriteVo writeVo);
	
	/**
	 * Method 		: likeDown
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @param writeVo
	 * @return
	 * Method 설명 	: 게시글 좋아요 취소
	 */
	int likeDown(Board_WriteVo writeVo);
}
