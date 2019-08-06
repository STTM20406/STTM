package kr.or.ddit.board_write.dao;

import java.util.List;

import kr.or.ddit.board_write.model.Board_WriteVo;
import kr.or.ddit.board_write.model.PostReplyVo;
import kr.or.ddit.paging.model.PageVo;

public interface IBoard_WriteDao {

	/**
	 * Method 		: insertPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param writeVo
	 * @return
	 * Method 설명 	: 게시글 추가
	 */
	int insertPost(Board_WriteVo writeVo);
	
	/**
	 * Method 		: updatePost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param writeVo
	 * @return
	 * Method 설명 	: 게시글 수정
	 */
	int updatePost(Board_WriteVo writeVo);
	
	/**
	 * Method 		: deletePost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param writeVo
	 * @return
	 * Method 설명 	: 게시글 삭제
	 */
	int deletePost(int write_id);
	
	/**
	 * Method 		: postInfo
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param write_id
	 * @return
	 * Method 설명 	: 게시글 상세조회
	 */
	Board_WriteVo postInfo(int write_id);
	
	/**
	 * Method 		: boardPostList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param pageVo
	 * @return
	 * Method 설명 	: 게시글 페이징리스트
	 */
	List<Board_WriteVo> boardPostList(PageVo pageVo);
	
	/**
	 * Method 		: postCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @return
	 * Method 설명 	: 게시글 전체 개수 조회
	 */
	int postCnt();
	
	/**
	 * Method 		: postViewCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param write_id
	 * @return
	 * Method 설명 	: 게시글 조회수
	 */
	int postViewCnt(int write_id);
	
	List<PostReplyVo> postReplyList(String user_email);
	
	/**
	 * 
	 * Method 		: selectTitle
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-05 최초 생성
	 * @param a
	 * @return
	 * Method 설명 	: 게시글 제목 검색
	 */
	List<Board_WriteVo> selectTitle(PageVo pageVo);
	
	/**
	 * 
	 * Method 		: selectContent
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-05 최초 생성
	 * @param a
	 * @return
	 * Method 설명 	: 게시글 내용 검색
	 */
	List<Board_WriteVo> selectContent(PageVo pageVo);
	
	/**
	 * 
	 * Method 		: selectTitleCnt
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-05 최초 생성
	 * @param title
	 * @return
	 * Method 설명 	: 검색한 제목의 글 개수
	 */
	int selectTitleCnt(String title);
	
	/**
	 * 
	 * Method 		: selectContentCnt
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-05 최초 생성
	 * @param content
	 * @return
	 * Method 설명 	: 검색한 내용의 글 개수
	 */
	int selectContentCnt(String content);
	
}
