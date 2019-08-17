package kr.or.ddit.vote.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vote.model.VoteVo;

@Repository
public class VoteDao implements IVoteDao{
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;
	
	@Override
	public List<VoteVo> voteList(Map<String, Object>paramMap) {
		return sqlSession.selectList("vote.voteList", paramMap);
	}
	
	@Override
	public int insertVote(VoteVo voteVo) {
		return sqlSession.insert("vote.insertVote", voteVo);
	}
	
	@Override
	public VoteVo getVote(Integer vote_id) {
		return sqlSession.selectOne("vote.getVote", vote_id);
	}
	
	@Override
	public int deleteVote(Integer vote_id) {
		return sqlSession.update("vote.deleteVote", vote_id);
	}
	@Override
	public int getVoteCnt(Integer prj_id) {
		return sqlSession.selectOne("vote.getVoteCnt", prj_id);
	}
	@Override
	public int updateVote(VoteVo voteVo) {
		return sqlSession.update("vote.updateVote", voteVo);
	}
}
