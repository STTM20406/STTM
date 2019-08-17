package kr.or.ddit.vote_item.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Override
	public List<Vote_ItemVo> itemList(Integer vote_id) {
		return sqlSession.selectList("vote.itemList", vote_id);
	}
	
	@Override
	public int deleteVoteItem(List<Integer> del_item_id) {
		Map<String, Object> del_item_map = new HashMap<String, Object>();
		del_item_map.put("del_item_id", del_item_id);
		return sqlSession.delete("vote.deleteVoteItem", del_item_map);
	}
}
