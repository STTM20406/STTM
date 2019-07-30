package kr.or.ddit.board_write.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.board_write.model.Board_WriteVo;
import kr.or.ddit.paging.model.PageVo;

@Repository
public class Board_WriteDao implements IBoard_WriteDao{
	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;

	/**
	 * Method 		: insertPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param writeVo
	 * @return
	 * Method 설명 	: 게시글 추가
	 */
	@Override
	public int insertPost(Board_WriteVo writeVo) {
		return sqlSession.insert("board.insertPost",writeVo);
	}

	/**
	 * Method 		: updatePost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param writeVo
	 * @return
	 * Method 설명 	: 게시글 수정
	 */
	@Override
	public int updatePost(Board_WriteVo writeVo) {
		return sqlSession.update("board.updatePost",writeVo);
	}

	/**
	 * Method 		: deletePost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param writeVo
	 * @return
	 * Method 설명 	: 게시글 삭제
	 */
	@Override
	public int deletePost(Board_WriteVo writeVo) {
		return sqlSession.update("board.deletePost",writeVo);
	}
	
	/**
	 * Method 		: postInfo
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param write_id
	 * @return
	 * Method 설명 	: 게시글 상세조회
	 */
	@Override
	public Board_WriteVo postInfo(int write_id) {
		return sqlSession.selectOne("board.postInfo",write_id);
	}
	
	/**
	 * Method 		: boardPostList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @param pageVo
	 * @return
	 * Method 설명 	: 게시글 페이징리스트
	 */
	@Override
	public List<Board_WriteVo> boardPostList(PageVo pageVo) {
		return sqlSession.selectList("board.boardPostList",pageVo);
	}
	
	/**
	 * Method 		: postCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-26 최초 생성
	 * @return
	 * Method 설명 	: 게시글 전체 개수 조회
	 */
	@Override
	public int postCnt() {
		return sqlSession.selectOne("board.postCnt");
	}
	
	/**
	 * Method 		: postViewCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param write_id
	 * @return
	 * Method 설명 	: 게시글 조회수
	 */
	@Override
	public int postViewCnt(int write_id) {
		return sqlSession.update("board.postViewCnt",write_id);
	}

}
