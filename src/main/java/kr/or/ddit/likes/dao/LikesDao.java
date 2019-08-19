package kr.or.ddit.likes.dao;

import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.board_write.model.Board_WriteVo;

@Repository
public class LikesDao implements ILikesDao{

	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Override
	public int likeCheck(Map<String, Object> like) {
		return sqlSession.selectOne("board.like_check",like);
	}

	@Override
	public int likeAdd(Board_WriteVo vo) {
		return sqlSession.update("board.likeAdd",vo);
	}

	@Override
	public int likeDown(Board_WriteVo vo) {
		return sqlSession.update("board.likeDown",vo);
	}

	@Override
	public int likeCnt(int write_id) {
		return sqlSession.update("board.likeCnt",write_id);
	}

	@Override
	public int whoLikeAdd(Board_WriteVo vo) {
		return sqlSession.insert("board.whoLikeAdd",vo);
	}

	@Override
	public int whoLikeDown(Board_WriteVo vo) {
		return sqlSession.delete("board.whoLikeDown",vo);
	}

	@Override
	public int likePushCheck(Board_WriteVo vo) {
		return sqlSession.selectOne("board.likePushCheck",vo);
	}

}
