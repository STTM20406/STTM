package kr.or.ddit.vote_part.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vote_part.model.Vote_PartVo;

@Repository
public class Vote_PartDao implements IVote_PartDao{
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;

	@Override
	public Vote_PartVo checkVote(Map<String, Object> paramMap) {
		return sqlSession.selectOne("vote.checkVote", paramMap);
	}
	@Override
	public int vote(Vote_PartVo vote_PartVo) {
		return sqlSession.insert("vote.insertVotePart", vote_PartVo);
	}
	@Override
	public List<Vote_PartVo> partList(Integer vote_id) {
		return sqlSession.selectList("vote.partList", vote_id);
	}
}	
