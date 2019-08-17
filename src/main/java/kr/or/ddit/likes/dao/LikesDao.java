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
	public int likeAdd(int write_id) {
		return sqlSession.update("board.likeAdd",write_id);
	}

	@Override
	public int likeDown(int write_id) {
		return sqlSession.update("board.likeDown",write_id);
	}

	@Override
	public int likeCnt(int write_id) {
		return sqlSession.update("board.likeCnt",write_id);
	}

}
