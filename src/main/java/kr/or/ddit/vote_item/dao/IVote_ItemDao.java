package kr.or.ddit.vote_item.dao;

import java.util.List;

import kr.or.ddit.vote_item.model.Vote_ItemVo;

public interface IVote_ItemDao {
	int insertVoteItem(Vote_ItemVo voteItemVo);
	
	List<Vote_ItemVo> itemList(Integer vote_id);
	
}
