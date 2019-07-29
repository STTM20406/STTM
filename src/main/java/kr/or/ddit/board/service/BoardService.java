package kr.or.ddit.board.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.board.model.BoardVo;

@Service
public class BoardService implements IBoardService{
	@Resource(name = "boardDao")
	private IBoardDao boardDao;

	/**
	 * Method 		: boardList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @return
	 * Method 설명 	: 게시판 전체리스트
	 */
	@Override
	public List<BoardVo> boardList() {
		return boardDao.boardList();
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
		return boardDao.boardListYes();
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
		return boardDao.insertBoard(boardVo);
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
		return boardDao.updateBoard(boardVo);
	}

}
