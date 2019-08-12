package kr.or.ddit.vote.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vote.model.VoteVo;

@Repository
public class VoteDao implements IVoteDao{
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;
	
	@Override
	public List<VoteVo> voteList(Integer prj_id) {
		return sqlSession.selectList("vote.voteList", prj_id);
	}
	
	@Override
	public int insertVote(VoteVo voteVo) {
		return sqlSession.insert("vote.insertVote", voteVo);
	}
}
