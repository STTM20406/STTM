package kr.or.ddit.vote.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.vote.dao.IVoteDao;
import kr.or.ddit.vote.model.VoteVo;
import kr.or.ddit.vote_item.dao.IVote_ItemDao;
import kr.or.ddit.vote_item.model.Vote_ItemVo;
import kr.or.ddit.vote_part.dao.IVote_PartDao;
import kr.or.ddit.vote_part.model.Vote_PartVo;

@Service
public class VoteService implements IVoteService{

	@Resource(name="voteDao")
	private IVoteDao voteDao;
	
	@Resource(name="vote_ItemDao")
	private IVote_ItemDao vote_ItemDao;
	
	@Resource(name="vote_PartDao")
	private IVote_PartDao vote_PartDao;
	
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
	public Map<String, Object> voteDetail(Map<String, Object> paramMap) {
		Map<String, Object> detailMap = new HashMap<>();
		List<Vote_ItemVo> itemList = vote_ItemDao.itemList((Integer)paramMap.get("vote_id"));
		
		Vote_PartVo vote_PartVo = vote_PartDao.checkVote(paramMap); 
		
		boolean isVoted = vote_PartVo == null ? false : true;
		
		StringBuffer sb_detail = new StringBuffer();
		for(Vote_ItemVo item : itemList) {
			sb_detail.append("<div class='item'>");
			sb_detail.append("<span>");
			sb_detail.append(item.getVote_item_con());
			sb_detail.append("</span>");
			sb_detail.append("<input type='radio' name='vote_item_id' value='"+ item.getVote_item_id()+"'>");
			sb_detail.append("</div>");
		}
		
		StringBuffer sb_detail_voted = new StringBuffer();
		for(Vote_ItemVo item : itemList) {
			if(isVoted) {
				if(vote_PartVo.getVote_item_id() == item.getVote_item_id()) {
					sb_detail_voted.append("<div class='item voted selected'>");
					sb_detail_voted.append("<span>");
					sb_detail_voted.append(item.getVote_item_con());
					sb_detail_voted.append("</span>");
					sb_detail_voted.append("</div>");
				} else {
					sb_detail_voted.append("<div class='item voted'>");
					sb_detail_voted.append("<span>");
					sb_detail_voted.append(item.getVote_item_con());
					sb_detail_voted.append("</span>");
					sb_detail_voted.append("</div>");
				}
			}
		}
		detailMap.put("html", sb_detail.toString());
		detailMap.put("htmlVoted", sb_detail_voted.toString());
		detailMap.put("isVoted", isVoted);
		return detailMap;
	}
	
	@RequestMapping(path="/vote/check", method=RequestMethod.POST)
	public void vote(Vote_PartVo vote_partVo) {
		
	}
}
