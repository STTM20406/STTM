package kr.or.ddit.vote.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.vote.dao.IVoteDao;
import kr.or.ddit.vote.model.VoteVo;
import kr.or.ddit.vote_item.dao.IVote_ItemDao;
import kr.or.ddit.vote_item.model.Vote_ItemVo;

@Service
public class VoteService implements IVoteService{

	@Resource(name="voteDao")
	private IVoteDao voteDao;
	
	@Resource(name="vote_ItemDao")
	private IVote_ItemDao vote_ItemDao;
	
	@Override 
	public String getVoteList(Integer prj_id) {
		List<VoteVo> voteList = voteDao.voteList(prj_id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		StringBuffer sb_voteList = new StringBuffer();
		for(VoteVo vote : voteList) {
			sb_voteList.append("<div class='votes' data-voteid = "+ vote.getVote_id() +">");
			sb_voteList.append("<span>");
			sb_voteList.append(vote.getVote_subject());
			sb_voteList.append("</span>");
			if(vote.getVote_end_date()!=null) {
				sb_voteList.append("<br>");
				sb_voteList.append("<span>");
				sb_voteList.append(sdf.format(vote.getVote_end_date()) + "까지");
				sb_voteList.append("</span>");
			} else {
				sb_voteList.append("<br>");
				sb_voteList.append("<span>");
				sb_voteList.append("마감일 없음");
				sb_voteList.append("</span>");
				
			}
			sb_voteList.append("</div>");
		}
		return sb_voteList.toString();
	}
	
	@Override
	public int insertVote(Map<String, Object> paramMap) {
		String[] vote_item = (String[])paramMap.get("vote_item");
		VoteVo voteVo = (VoteVo)paramMap.get("voteVo");
		voteDao.insertVote(voteVo);
		for(String item : vote_item) {
			Vote_ItemVo voteitemVo = new Vote_ItemVo();
			voteitemVo.setVote_id(voteVo.getVote_id());
			voteitemVo.setVote_item_con(item);
			vote_ItemDao.insertVoteItem(voteitemVo);
		}
		return voteVo.getVote_id();
	}

	@Override
	public Map<String, Object> voteDetail(Integer vote_id) {
		Map<String, Object> detailMap = new HashMap<>();
		
		return null;
	}
}
