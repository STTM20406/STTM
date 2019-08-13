package kr.or.ddit.vote_item.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vote_item.model.Vote_ItemVo;
import kr.or.ddit.vote_part.model.Vote_PartVo;

@Repository
public class Vote_ItemDao implements IVote_ItemDao{
	@Resource(name="sqlSession")
	SqlSessionTemplate sqlSession;
	
	@Override
	public int insertVoteItem(Vote_ItemVo voteItemVo) {
		return sqlSession.insert("vote.insertVoteItem", voteItemVo);
	}
	
	@Override
	public List<Vote_ItemVo> itemList(Integer vote_id) {
		return sqlSession.selectList("vote.itemList", vote_id);
	}
}
