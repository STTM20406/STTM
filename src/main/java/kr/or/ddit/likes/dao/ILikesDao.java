package kr.or.ddit.likes.dao;

import java.util.Map;

import kr.or.ddit.board_write.model.Board_WriteVo;

public interface ILikesDao {
	
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
	int likeAdd(Board_WriteVo vo);
	
	/**
	 * Method 		: likeDown
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @param writeVo
	 * @return
	 * Method 설명 	: 게시글 좋아요 취소
	 */
	int likeDown(Board_WriteVo vo);
	
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
	
	/**
	 * 
	 * Method 		: whoLikeAdd
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 게시글의 좋아요를 내가 눌렀다는 정보가 들어감
	 */
	int whoLikeAdd(Board_WriteVo vo);
	
	/**
	 * 
	 * Method 		: whoLikeDown
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 게시글의 좋아요를 취소하면 게시글에 내가 좋아요 했다는 정보가 사라짐
	 */
	int whoLikeDown(Board_WriteVo vo);
	
	/**
	 * 
	 * Method 		: likePushCheck
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 게시글에 내가 좋아요를 눌렀는지 확인 
	 */
	int likePushCheck(Board_WriteVo vo);
}
