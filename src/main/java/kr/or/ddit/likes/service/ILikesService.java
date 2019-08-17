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
	int likeAdd(int write_id);
	
	/**
	 * Method 		: likeDown
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @param writeVo
	 * @return
	 * Method 설명 	: 게시글 좋아요 취소
	 */
	int likeDown(int write_id);
	
	/**
	 * 
	 * Method 		: likeCnt
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-17 최초 생성
	 * @param write_id
	 * @return
	 * Method 설명 	: 게시글 좋아요 개수
	 */
	int likeCnt(int write_id);
}
