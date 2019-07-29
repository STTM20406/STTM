package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.model.BoardVo;

public interface IBoardService {
	
	/**
	 * Method 		: boardList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @return
	 * Method 설명 	: 게시판 전체리스트
	 */
	List<BoardVo> boardList();
	
	/**
	 * Method 		: boardListYes
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @return
	 * Method 설명 	: 게시판 사용가능 리스트
	 */
	List<BoardVo> boardListYes();
	
	/**
	 * Method 		: insertBoard
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param boardVo
	 * @return
	 * Method 설명 	: 게시판 생성
	 */
	int insertBoard(BoardVo boardVo);
	
	/**
	 * Method 		: updateBoard
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param boardVo
	 * @return
	 * Method 설명 	: 게시판 수정
	 */
	int updateBoard(BoardVo boardVo);
}
