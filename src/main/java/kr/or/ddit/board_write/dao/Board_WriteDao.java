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

	@Override
	public int insertPost(Board_WriteVo writeVo) {
		return sqlSession.insert("board.insertPost",writeVo);
	}

	@Override
	public int updatePost(Board_WriteVo writeVo) {
		return sqlSession.update("board.updatePost",writeVo);
	}

	@Override
	public int deletePost(Board_WriteVo writeVo) {
		return sqlSession.update("board.deletePost",writeVo);
	}

	@Override
	public Board_WriteVo postInfo(int write_id) {
		return sqlSession.selectOne("board.postInfo",write_id);
	}

	@Override
	public List<Board_WriteVo> boardPostList(PageVo pageVo) {
		return sqlSession.selectList("board.boardPostList",pageVo);
	}

	@Override
	public int postCnt() {
		return sqlSession.selectOne("board.postCnt");
	}

}
