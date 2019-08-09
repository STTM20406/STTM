package kr.or.ddit.friend_req.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.friend_req.model.Friend_ReqVo;
import kr.or.ddit.friend_req.service.IFriend_ReqService;
import kr.or.ddit.users.model.UserVo;

@Controller
public class Friend_ReqController {
	
	private static final Logger logger = LoggerFactory.getLogger(Friend_ReqController.class);
	
	@Resource(name = "friend_ReqService")
	private IFriend_ReqService friend_ReqService;
	
	/**
	 * 
	* Method : friendsRquestView
	* 작성자 : 김경호
	* 변경이력 : 2019-08-09
	* @param model
	* @return
	* Method 설명 : 친구 요청 
	 */
	@RequestMapping(path = "/friendsRquest", method = RequestMethod.GET)
	public String friendsRquestView(Model model, int req_id) {
		
		logger.debug("req_id : 이거 가져오나 보자 {}",req_id);
		model.addAttribute("req_id", req_id);
		
		return "/member/projectMember.user.tiles";
	}
	
	@RequestMapping(path = "/friendsRquest", method = RequestMethod.POST)
	public String friendsRquestProcess(HttpSession session, String req_email) {
		
		String viewName = "";
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		logger.debug("userVo : 이거 가져오나 보자 {}",userVo);
		
		String user_email = userVo.getUser_email();
		logger.debug("user_email : 이거 가져오나 보자 {}",user_email);
		
		Friend_ReqVo friendsReqVo = new Friend_ReqVo(user_email,req_email);
		logger.debug("friendsReqVo : 이거 가져오나 보자 {}",friendsReqVo);

		int requstFriends = friend_ReqService.firendsRequest(friendsReqVo);
		logger.debug("requstFriends : 이거 가져오나 보자 {}",requstFriends);
		
		if(requstFriends != 0) {
			viewName = "/member/projectMember.user.tiles";
		}
		
		return viewName;
	}
	
	
	
}
