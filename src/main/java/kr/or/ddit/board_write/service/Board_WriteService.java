package kr.or.ddit.board_write.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.board_write.dao.Board_WriteDao;
import kr.or.ddit.board_write.dao.IBoard_WriteDao;
import kr.or.ddit.board_write.model.Board_WriteVo;
import kr.or.ddit.paging.model.PageVo;

@Service
public class Board_WriteService implements IBoard_WriteService{

	private static final Logger logger = LoggerFactory.getLogger(Board_WriteService.class);
	
	@Resource(name="board_WriteDao")
	IBoard_WriteDao board_wirteDao;
	
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
		return board_wirteDao.insertPost(writeVo);
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
		return board_wirteDao.updatePost(writeVo);
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
	public int deletePost(int write_id) {
		return board_wirteDao.deletePost(write_id);
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
		return board_wirteDao.postInfo(write_id);
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
	public Map<String, Object> boardPostList(PageVo pageVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("boardPostList", board_wirteDao.boardPostList(pageVo));
		
		int postCnt = board_wirteDao.postCnt();
		int paginationSize = (int) Math.ceil((double)postCnt/pageVo.getPageSize());
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
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
		return board_wirteDao.postCnt();
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
		return board_wirteDao.postViewCnt(write_id);
	}

	@Override
	public Map<String, Object> postReplyList(String user_email) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("postReply", board_wirteDao.postReplyList(user_email));
		
		return resultMap;
	}

	@Override
	public Map<String, Object> selectTitle(PageVo pageVo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("titleList", board_wirteDao.selectTitle(pageVo));
		logger.debug("log pageVo.getSubject() : {}",pageVo.getSubject() );
		
		int titleCnt = board_wirteDao.selectTitleCnt(pageVo.getSubject());
		int paginationSize = (int) Math.ceil((double)titleCnt/pageVo.getPageSize());
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
		
	}

	@Override
	public Map<String, Object> selectContent(PageVo pageVo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("contentList", board_wirteDao.selectContent(pageVo));
		
		int contentCnt = board_wirteDao.selectContentCnt(pageVo.getContent());
		int paginationSize = (int) Math.ceil((double)contentCnt/pageVo.getPageSize());
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}

	@Override
	public int selectTitleCnt(String title) {
		return board_wirteDao.selectTitleCnt(title);
	}

	@Override
	public int selectContentCnt(String content) {
		return board_wirteDao.selectContentCnt(content);
	}

}
