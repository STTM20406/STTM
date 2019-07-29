package kr.or.ddit.board.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.board.model.BoardVo;

@Repository
public class BoardDao implements IBoardDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;

	/**
	 * Method 		: boardList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @return
	 * Method 설명 	: 게시판 전체리스트
	 */
	@Override
	public List<BoardVo> boardList() {
		return sqlSession.selectList("board.boardList");
	}

	/**
	 * Method 		: boardListYes
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @return
	 * Method 설명 	: 게시판 사용가능 리스트
	 */
	@Override
	public List<BoardVo> boardListYes() {
		return sqlSession.selectList("board.boardListYes");
	}

	/**
	 * Method 		: insertBoard
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param boardVo
	 * @return
	 * Method 설명 	: 게시판 생성
	 */
	@Override
	public int insertBoard(BoardVo boardVo) {
		return sqlSession.insert("board.insertBoard",boardVo);
	}

	/**
	 * Method 		: updateBoard
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param boardVo
	 * @return
	 * Method 설명 	: 게시판 수정
	 */
	@Override
	public int updateBoard(BoardVo boardVo) {
		return sqlSession.update("board.updateBoard",boardVo);
	}
	
}
