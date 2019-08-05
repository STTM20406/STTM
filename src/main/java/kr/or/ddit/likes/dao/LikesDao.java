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
	public int likeAdd(Board_WriteVo writeVo) {
		return sqlSession.insert("board.likeAdd",writeVo);
	}

	@Override
	public int likeDown(Board_WriteVo writeVo) {
		return sqlSession.insert("board.likeDown",writeVo);
	}

}
