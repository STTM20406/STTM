package kr.or.ddit.vote.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.vote.model.VoteVo;
import kr.or.ddit.vote.service.IVoteService;
import kr.or.ddit.vote_item.model.Vote_ItemVo;
import kr.or.ddit.vote_part.model.Vote_PartVo;
import kr.or.ddit.vote_part.service.IVote_PartService;

/**
 * VoteController.java
 *
 * @author 유승진
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 유승진 2019-08-12 최초 생성
 *
 * </pre>
 */
@Controller
public class VoteController {
	private static final Logger logger = LoggerFactory.getLogger(VoteController.class);
	@Resource(name="voteService")
	IVoteService voteService;
	
	@Resource(name="vote_PartService")
	IVote_PartService vote_PartService;
	
	@RequestMapping(path="/vote", method=RequestMethod.GET)
	public String voteListView() {
		return "/vote/vote.user.tiles";
	}
	@RequestMapping(path="/vote", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> voteList(@RequestParam(name = "votelist_page")Integer page ,Integer prj_id, String user_email) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		logger.debug("voteList() prj_id : {}, user_email : {}, page : {}", prj_id, user_email, page);
		paramMap.put("prj_id", prj_id);
		paramMap.put("user_email", user_email);
		page = page == null ? 1 : page;
		paramMap.put("page", page);
		Integer pageSize = 5;
		paramMap.put("pageSize", pageSize);
		String voteHtmlList = voteService.getVoteList(paramMap);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("voteList", voteHtmlList);
		return resultMap;
	}
	@RequestMapping(path="/newVote", method=RequestMethod.GET)
	public String newVoteView(Model model) {
		return "/vote/newVote.user.tiles";
	}
	
	@RequestMapping(path="/newVote", method=RequestMethod.POST)
	@ResponseBody
	public String insertVote(VoteVo voteVo, String[] vote_item, Model model) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("voteVo", voteVo);
		paramMap.put("vote_item", vote_item);
		int vote_id = voteService.insertVote(paramMap);
		String result = "";
		if(vote_id != 0)
			result = "OK";
		else
			result = "ERROR";
		return result;
	}
	
	@RequestMapping(path="/voteDetail", method=RequestMethod.GET)
	public String voteDetailView(Model model, Integer vote_id) {
		model.addAttribute("vote_id", vote_id);
		return "/vote/voteDetail.user.tiles";
	}
	
	@RequestMapping(path="/voteDetail", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> voteDetail(Integer vote_id, String user_email) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vote_id", vote_id);
		paramMap.put("user_email", user_email);
		
		Map<String, Object> resultMap = voteService.voteDetail(paramMap);
		return resultMap;
	}
	
	@RequestMapping(path="/vote/check", method=RequestMethod.POST)
	@ResponseBody
	public void vote(Vote_PartVo vote_PartVo) {
		vote_PartService.vote(vote_PartVo);
	}
	
	@RequestMapping(path="/vote/del", method=RequestMethod.POST)
	@ResponseBody
	public String delVote(Integer vote_id) {
		int delCnt = voteService.deleteVote(vote_id);
		String result = "";
		switch(delCnt) {
		case 1:
			result = "OK";
			break;
		default:
			result = "ERROR";
			break;
		}
		return result;
	}
	
	@RequestMapping(path="/voteDetailMdf", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> voteDetailMdf(Integer vote_id) {
		Map<String, Object> resultMap = voteService.voteDetailMdf(vote_id);
		return resultMap;
	}
	
	@RequestMapping(path="/voteMdf", method=RequestMethod.POST)
	@ResponseBody
	public void voteModify(VoteVo voteVo) {
		logger.debug("voteVo : {}", voteVo);
		voteService.voteModify(voteVo);
	}
	
	@RequestMapping(path="/voteMdfItems_del", method=RequestMethod.POST)
	@ResponseBody
	public void voteDeleteItems(@RequestParam() List<String> del_item_id) {
		logger.debug("item_del : {}", del_item_id);
		voteService.deleteItems(del_item_id);
	}
	
	@RequestMapping(path="/voteMdfItems", method=RequestMethod.POST)
	@ResponseBody
	public void voteModifyItems(@RequestBody List<Vote_ItemVo> vote_item) {
		logger.debug("vote_item : {}", vote_item);
		voteService.insertItems(vote_item);
	}
	
	@RequestMapping(path="/checkDtMdf", method=RequestMethod.POST)
	@ResponseBody
	public boolean voteMdfCheckDt(Integer vote_id, String vote_end_date) {
		logger.debug("vote_id : {}", vote_id);
		logger.debug("vote_end_date : {}", vote_end_date);
		
		if(vote_end_date.equals("")) {
			return true;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("vote_id", vote_id);
		paramMap.put("vote_end_date", vote_end_date);
		boolean result = voteService.checkDt(paramMap);
		logger.debug("result : {}", result);
		return result;
	}
	
	@RequestMapping(path="/cmpVote", method=RequestMethod.POST)
	@ResponseBody
	public void cmpVote(VoteVo voteVo, Integer vote_id_int) {
		Integer vote_id = voteVo.getVote_id();
		logger.debug("vote_id : {}", vote_id);
		logger.debug("vote_id_int : {}", vote_id_int);
		voteService.cmpVote(vote_id);
	}
}
