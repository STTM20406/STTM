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
	public String getVoteList(Map<String, Object> paramMap) {
		String user_email = (String)paramMap.get("user_email");
		List<VoteVo> voteList = voteDao.voteList(paramMap);
		Integer page = (Integer) paramMap.get("page");
		Integer pageSize = (Integer) paramMap.get("pageSize");
		Integer prj_id = (Integer) paramMap.get("prj_id");
		int voteCnt = voteDao.getVoteCnt(prj_id);
		int paginationSize = (int)Math.ceil((double) voteCnt / pageSize);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		StringBuffer sb_voteList = new StringBuffer();
		sb_voteList.append("<table id='voteListTbl'>");
		sb_voteList.append("<thead>");
		sb_voteList.append("<tr class='th1'>");
		sb_voteList.append("<th><span class='vote_subject_head'>");
		sb_voteList.append("투표 제목");
		sb_voteList.append("</span></th>");
		sb_voteList.append("<th class='th2'><span class='vote_part'>");
		sb_voteList.append("인원(참가/전체)");
		sb_voteList.append("</span></th>");
		sb_voteList.append("<th class='th3'><span class='vote_end_dt'>");
		sb_voteList.append("기간");
		sb_voteList.append("</span></th>");
		sb_voteList.append("<th class='th4'><span class='vote_user_nm'>");
		sb_voteList.append("작성자");
		sb_voteList.append("</span></th>");
		sb_voteList.append("<th class='th5'><span class='vote_part_yn'>");
		sb_voteList.append("참가여부");
		sb_voteList.append("</span></th>");
		sb_voteList.append("<th class='th6'><span class='vote_config'>");
		sb_voteList.append("설정");
		sb_voteList.append("</span></th>");
		sb_voteList.append("<th class='vote_config_menu'>");
		sb_voteList.append("</th>");
		sb_voteList.append("</tr>");
		
		sb_voteList.append("</thead>");
		sb_voteList.append("<tbody>");
		for(VoteVo vote : voteList) {
			String subj = vote.getVote_subject();
			subj.replaceAll("<", "&lt;");
			subj.replaceAll(">", "&gt;");
			vote.setVote_subject(subj);
			sb_voteList.append("<tr class='vote' data-voteid='"+ vote.getVote_id() +"'>");
			sb_voteList.append("<td>"); // 투표 제목
			sb_voteList.append("<span class='vote_subject'>" + vote.getVote_subject() + "</span>");
			sb_voteList.append("</td>");
			sb_voteList.append("<td>"); // 참가 인원
			sb_voteList.append("<span class='vote_part'>" + vote.getPart() + " / " + vote.getMax() + "명"+ "</span>");
			sb_voteList.append("</td>");
			sb_voteList.append("<td>"); // 마감 일시
			if(vote.getVote_end_date()!=null) {
				sb_voteList.append("<span class='vote_end_dt'>" + sdf.format(vote.getVote_start_date()) + " - " + sdf.format(vote.getVote_end_date()) + "</span>");
			} else {
				sb_voteList.append("<span class='vote_no_deadline'>" + sdf.format(vote.getVote_start_date()) + " - " + "</span>");
			}
			sb_voteList.append("</td>");
			sb_voteList.append("<td>");	// 작성자
			sb_voteList.append("<span class='vote_user_nm'>" + vote.getUser_nm() + "</span>");
			sb_voteList.append("</td>");
			sb_voteList.append("<td>");	// 참가여부
			switch(vote.getVote_fl()) {
				case 1:
					sb_voteList.append("<span class='vote_part_y'>V</span>");
					break;
				default:
					sb_voteList.append("<span class='vote_part_n'>X</span>");
					break;
			}
			sb_voteList.append("</td>");
			sb_voteList.append("<td>");	// 설정
			
			if(vote.getVote_email().equals(user_email)) 
				sb_voteList.append("<span class='vote_config'>…</span>");
			
				sb_voteList.append("</td>");
				sb_voteList.append("<td class='vote_config_menu'>");	
			
				if(vote.getVote_email().equals(user_email)) {
					sb_voteList.append("<div class='vote_set_list'>");
					sb_voteList.append("<dl>");
					sb_voteList.append("<dt><input type='hidden' value='"+ vote.getVote_id()+"'></dt>");
					sb_voteList.append("<dd><span class='vote_mdf'>수정</span></dd>");
					sb_voteList.append("<dd><span class='vote_del'>삭제</span></dd>");
					sb_voteList.append("</dl>");
					sb_voteList.append("</div>");
				} 
			sb_voteList.append("</td>");
			sb_voteList.append("</tr>");
		}
		sb_voteList.append("</tbody>");
		sb_voteList.append("</table>");
		
		sb_voteList.append("</div>");
		sb_voteList.append("<div class='pagination'>");
			if(page==1) {
				sb_voteList.append("<a class='btn_first'></a>");
			} else {
				sb_voteList.append("<a href='void(0);' onclick='voteList(1);'>«</a>");
			}
			for(int i = 1; i <= paginationSize; i++) {
				if(i == page) {
					sb_voteList.append("<span>"+ i + "</span>");
				} else {
					sb_voteList.append("<a href='javascript:void(0);' onclick='voteList("+i+");'>"+i+"</a>");
				}
			}
			if(page==paginationSize) {
				sb_voteList.append("<a class='btn_last'></a>");
			} else {
				sb_voteList.append("<a href='javascript:void(0);' onclick='voteList("+paginationSize+");'>»</a>");
			}
		sb_voteList.append("</div>");
		return sb_voteList.toString();
	}
	
	@Override
	public int insertVote(Map<String, Object> paramMap) {
		String[] vote_item = (String[])paramMap.get("vote_item");
		VoteVo voteVo = (VoteVo)paramMap.get("voteVo");
		String subj = voteVo.getVote_subject();
		subj.replaceAll("&lt;", "<");
		subj.replaceAll("&gt;", ">");
		voteVo.setVote_subject(subj);
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
		VoteVo voteVo = voteDao.getVote((Integer)paramMap.get("vote_id"));
		List<Vote_ItemVo> itemList = vote_ItemDao.itemList((Integer)paramMap.get("vote_id"));
		List<Vote_PartVo> partList = vote_PartDao.partList((Integer)paramMap.get("vote_id"));
		String subj = voteVo.getVote_subject();
		subj.replaceAll("<", "&lt;");
		subj.replaceAll(">", "&gt;");
		voteVo.setVote_subject(subj);
		
		Vote_PartVo vote_PartVo = vote_PartDao.checkVote(paramMap); 
		boolean isVoted = vote_PartVo == null ? false : true;
		
		String vote_content = "<h2>" +voteVo.getVote_con() + "</h2>";
		
		StringBuffer sb_detail = new StringBuffer(vote_content);
		for(Vote_ItemVo item : itemList) {
			
			sb_detail.append("<div class='item'>");
			sb_detail.append("<div class='item_con'>");
			sb_detail.append("<span>");
			sb_detail.append(item.getVote_item_con());
			sb_detail.append("</span>");
			sb_detail.append("</div>");
			sb_detail.append("<div>");
			sb_detail.append("<span><input type='radio' name='vote_item_id' value='"+ item.getVote_item_id()+"'/></span>");
			sb_detail.append("</div>");
			sb_detail.append("</div>");
		}
		
		StringBuffer sb_detail_voted = new StringBuffer(vote_content);
		for(Vote_ItemVo item : itemList) {
			if(isVoted) {
				if(vote_PartVo.getVote_item_id() == item.getVote_item_id()) {
					sb_detail_voted.append("<div class='item voted selected'>");
					sb_detail_voted.append("<div class='item_con'>");
					sb_detail_voted.append("<span>");
					sb_detail_voted.append(item.getVote_item_con());
					sb_detail_voted.append("</span>");
					sb_detail_voted.append("</div>");
						int cnt = 0;
						for(Vote_PartVo part : partList) {
							if(item.getVote_item_id() == part.getVote_item_id()) {
								cnt++;
							}
						}
						sb_detail_voted.append("<div class='item_cnt'>");
						sb_detail_voted.append("<span>");
						sb_detail_voted.append(cnt+"명");
						sb_detail_voted.append("</span>");
						sb_detail_voted.append("</div>");
					sb_detail_voted.append("</div>");
				} else {
					sb_detail_voted.append("<div class='item voted'>");
					sb_detail_voted.append("<div class='item_con'>");
					sb_detail_voted.append("<span>");
					sb_detail_voted.append(item.getVote_item_con());
					sb_detail_voted.append("</span>");
					sb_detail_voted.append("</div>");
						int cnt = 0;
						for(Vote_PartVo part : partList) {
							if(item.getVote_item_id() == part.getVote_item_id()) {
								cnt++;
							}
						}
						sb_detail_voted.append("<div class='item_cnt'>");
						sb_detail_voted.append("<span>");
						sb_detail_voted.append(cnt+"명");
						sb_detail_voted.append("</span>");
						sb_detail_voted.append("</div>");
					sb_detail_voted.append("</div>");
				}
			}
		}
		detailMap.put("html", sb_detail.toString());
		detailMap.put("htmlVoted", sb_detail_voted.toString());
		detailMap.put("isVoted", isVoted);
		detailMap.put("voteVo", voteVo);
		return detailMap;
	}
	
	@Override
	public int deleteVote(Integer vote_id) {
		return voteDao.deleteVote(vote_id);
	}	
}
