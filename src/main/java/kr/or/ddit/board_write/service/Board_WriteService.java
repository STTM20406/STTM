package kr.or.ddit.board_write.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.board_write.dao.IBoard_WriteDao;
import kr.or.ddit.board_write.model.Board_WriteVo;
import kr.or.ddit.paging.model.PageVo;

@Service
public class Board_WriteService implements IBoard_WriteService{

	@Resource(name="board_WriteDao")
	IBoard_WriteDao board_wirteDao;
	
	@Override
	public int insertPost(Board_WriteVo writeVo) {
		return board_wirteDao.insertPost(writeVo);
	}

	@Override
	public int updatePost(Board_WriteVo writeVo) {
		return board_wirteDao.updatePost(writeVo);
	}

	@Override
	public int deletePost(Board_WriteVo writeVo) {
		return board_wirteDao.deletePost(writeVo);
	}

	@Override
	public Board_WriteVo postInfo(int write_id) {
		return board_wirteDao.postInfo(write_id);
	}

	@Override
	public Map<String, Object> boardPostList(PageVo pageVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("boardPostList", board_wirteDao.boardPostList(pageVo));
		
		int postCnt = board_wirteDao.postCnt();
		int paginationSize = (int) Math.ceil((double)postCnt/pageVo.getPageSize());
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}

	@Override
	public int postCnt() {
		return board_wirteDao.postCnt();
	}

}
