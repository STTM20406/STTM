package kr.or.ddit.friends.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.friends.service.IFriendsService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;

@Controller
public class FriendsController {

	private static final Logger logger = LoggerFactory.getLogger(FriendsController.class);

//	@Resource(name = "userService")
//	private IUserService userService;
	
//	@Resource(name = "friendsService")
//	private IFriendsService friendsService;

//	/**
//	 * 
//	* Method : userFriendsList
//	* 작성자 : 김경호
//	* 변경이력 : 2019-08-07
//	* @param pageVo
//	* @param model
//	* @param session
//	* @return
//	* Method 설명 : 회원의 친구 목록을 회원 자신의 이메일로 조회하여 페이징 리스트로 보여준다
//	 */
////	@RequestMapping(path = "/userFriendsList", method = RequestMethod.GET)
//	public String userFriendsList(PageVo pageVo, Model model, HttpSession session) {
//		
//		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		map.put("page", pageVo.getPage());
//		map.put("pageSize", pageVo.getPageSize());
//		map.put("user_email", userVo.getUser_email());
//		logger.debug("map 오전 열시쯤 로거 확인 : {}",map);
//		
//		return "/member/projectMember.user.tiles";
//	}
	
	
//	@RequestMapping(path = "/setUserNotice", method = RequestMethod.GET)
	public String setNoticeView(HttpSession session, Model model, String not_set_id) {

		return "";
	}
	
	
	
	
}
