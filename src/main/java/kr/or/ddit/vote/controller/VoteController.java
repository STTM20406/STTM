package kr.or.ddit.vote.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.vote.model.VoteVo;
import kr.or.ddit.vote.service.IVoteService;
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
	public Map<String, Object> voteList(Integer prj_id) {
		String voteHtmlList = voteService.getVoteList(prj_id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("voteList", voteHtmlList);
		return resultMap;
	}
	@RequestMapping(path="/newVote", method=RequestMethod.GET)
	public String newVoteView(Model model) {
		return "/vote/newVote.user.tiles";
	}
	
	@RequestMapping(path="/newVote", method=RequestMethod.POST)
	public String insertVote(VoteVo voteVo, String[] vote_item, String vote_end_dt, Model model) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("voteVo", voteVo);
		paramMap.put("vote_item", vote_item);
		int vote_id = voteService.insertVote(paramMap);
		model.addAttribute("vote_id", vote_id);
		return "/vote/voteDetail.user.tiles";
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
}
