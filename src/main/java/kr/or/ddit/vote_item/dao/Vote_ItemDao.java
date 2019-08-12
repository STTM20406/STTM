package kr.or.ddit.vote_item.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vote_item.model.Vote_ItemVo;

@Repository
public class Vote_ItemDao implements IVote_ItemDao{
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;
	
	@Override
	public int insertVoteItem(Vote_ItemVo voteItemVo) {
		return sqlSession.insert("vote.insertVoteItem", voteItemVo);
	}
}
