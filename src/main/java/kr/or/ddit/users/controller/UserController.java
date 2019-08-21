package kr.or.ddit.users.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.encrypt.encrypt.kisa.aria.ARIAUtil;
import kr.or.ddit.friend_req.model.Friend_ReqVo;
import kr.or.ddit.friend_req.service.IFriend_ReqService;
import kr.or.ddit.friends.model.FriendsVo;
import kr.or.ddit.friends.service.IFriendsService;
import kr.or.ddit.notification_set.model.Notification_SetVo;
import kr.or.ddit.notification_set.service.INotification_SetService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.project_mem.service.IProject_MemService;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "notification_SetService")
	private INotification_SetService notification_SetService;
	
	@Resource(name = "project_MemService")
	private IProject_MemService project_MemService;
	
	@Resource(name = "friendsService")
	private IFriendsService friendsService;
	
	@Resource(name = "friend_ReqService")
	private IFriend_ReqService friend_ReqService;
	
	/**
	 * 
	* Method : setPassView
	* 작성자 : 김경호
	* 변경이력 : 2019-07-27
	* @return
	* Method 설명 : 계정 설정 요청 및 프로세스
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(path = "/setUserPass", method = RequestMethod.GET)
	public String setPassView(HttpSession session, Model model, PageVo pageVo, String transferOwership) throws InvalidKeyException, UnsupportedEncodingException {
		
		logger.debug("transferOwership : 소유권 이전 aTag {}",transferOwership);
		
		// 세션에 저장된 user 정보를 가져옴
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		
		logger.debug("userVo : 테스트다 {} 123", userVo );
		
		// 암호화된  비밀번호 가져오기
		String encodePass = userVo.getUser_pass();

		// 암호화된 비밀번호 복호화
		String decodePass = ARIAUtil.ariaDecrypt(encodePass);
		
		// ah!
		String user_email = userVo.getUser_email();
		String user_nm = userVo.getUser_nm();
		String user_hp = userVo.getUser_hp();
		
		model.addAttribute("user_email", user_email);
		model.addAttribute("user_pass", decodePass);
		
		model.addAttribute("user_nm", user_nm);
		model.addAttribute("user_hp", user_hp);

		logger.debug("user_pass : {}", userVo.getUser_pass());
		
		// ------------------------ 휴면 계정 전화 페이징 리스트 ------------------------
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("user_email", userVo.getUser_email());
		
		Map<String, Object> resultMap = project_MemService.prjMemListForInactive(map);
		
		logger.debug("resultMap : 이거 찍자 {}",resultMap);
		
		List<Project_MemVo> inactiveMemList = (List<Project_MemVo>) resultMap.get("inactiveMemList");
		logger.debug("inactiveMemList : 찍고 만다 {}",inactiveMemList);
		
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("inactiveMemList", inactiveMemList);
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		
		// ------------------------ End 휴면 계정 전화 페이징 리스트 ---------------------
		
		return "/account/accountSet.user.tiles";
	}
	
	@RequestMapping(path = "/setUserPass", method = RequestMethod.POST)
	public String setPassProcess(String user_pass, String user_nm, String user_hp, HttpSession session) throws InvalidKeyException, UnsupportedEncodingException {
		String viewName = "";
		
		UserVo getUserSession = (UserVo) session.getAttribute("USER_INFO"); 
		String user_email = getUserSession.getUser_email();
		
		logger.debug("user_nm : {}",user_nm);
		logger.debug("user_hp : {}",user_hp);
		
		if(user_nm == null) {
		
			UserVo userVo = new UserVo();
			userVo.setUser_email(user_email);
			userVo.setUser_pass(ARIAUtil.ariaEncrypt(user_pass));
			
			int updateUserPass = userService.updateUserPass(userVo);
		}else{
			
			UserVo userVo = new UserVo();
			userVo.setUser_email(user_email);
			
			userVo.setUser_nm(user_nm);
			userVo.setUser_hp(user_hp);
			
			int updateUserProfile = userService.updateUserProfile(userVo);
		}
		return "/account/accountSet.user.tiles";
		
	}
	
	/**
	 * 
	* Method : setNoticeView
	* 작성자 : 김경호
	* 변경이력 : 2019-07-30
	* @param session
	* @param model
	* @return
	* Method 설명 : 사용자 알림 설정
	 */
	@RequestMapping(path = "/setUserNotice", method = RequestMethod.GET)
	public String setNoticeView(HttpSession session, Model model, String not_set_id) {
		model.addAttribute("not_set_id", not_set_id);
		return "/account/accountSet.user.tiles";
	}
	
	@RequestMapping(path = "/setUserNotice", method = RequestMethod.POST)
	public String setNoticeProcess(HttpSession session, Model model) {
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		Notification_SetVo notificationSetVo = new Notification_SetVo(); 

		String not_cd = notificationSetVo.getNot_cd();
		
		model.addAttribute("not_cd", not_cd);
		
//		notificationSetVo.setNot_set_id(not_set_id);
//		notificationSetVo.setUser_email(user_email);
//		notificationSetVo.setNot_cd(not_cd);
//		notificationSetVo.setNot_chk_fl(not_chk_fl);t
		
		int inseertUserNotice = userService.insertUserNotice(notificationSetVo);
		return "/account/accountSet.user.tiles";
	}
	
	/**
	 * 
	* Method : setInactiveAccountView
	* 작성자 : 김경호
	* 변경이력 : 2019-07-29
	* @param session
	* @param model
	* @return
	* Method 설명 : 휴명 계정 설정 화면 요청 밒 프로세스
	 */
	@RequestMapping(path = "/setUserStatus", method = RequestMethod.GET)
	public String setInactiveAccountView(HttpSession session, Model model,
										 PageVo pageVo) {
		
//		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("page", pageVo.getPage());
//		map.put("pageSize", pageVo.getPageSize());
//		map.put("user_email", userVo.getUser_email());
//		
//		Map<String, Object> resultMap = project_MemService.prjMemListForInactive(map);
//		
//		logger.debug("resultMap : 이거 찍자 {}",resultMap);
//		
//		List<Project_MemVo> inactiveMemList = (List<Project_MemVo>) resultMap.get("inactiveMemList");
//		logger.debug("inactiveMemList : 찍고 만다 {}",inactiveMemList);
//		
//		int paginationSize = (Integer) resultMap.get("paginationSize");
//		
//		model.addAttribute("inactiveMemList", inactiveMemList);
//		model.addAttribute("paginationSize", paginationSize);
//		model.addAttribute("pageVo", pageVo);
		
		return "/account/accountSet.user.tiles";
	}
	
	
	/**
	 * 
	* Method : projectMemberListView
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param pageVo
	* @param model
	 * @param user_email 
	* @return
	* Method 설명 : 회원이 해당 프로젝트의 멤버 목록을 조회
	* Method 설명 : 회원의 친구 목록을 회원 자신의 이메일로 조회하여 페이징 리스트로 보여준다
	* 
	 */
	@RequestMapping(path = "/projectMemberList", method = RequestMethod.GET)
	public String projectMemberListView(PageVo pageVo, Model model, HttpSession session,
										String acceptEmail, String denyEmail, String prjMemView, String frdRequEmail) {
		
		logger.debug("prjMemView : ruichi {}",prjMemView);
		logger.debug("frdRequEmail : tae {}",frdRequEmail);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		Project_MemVo prjVo = new Project_MemVo();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("user_email", userVo.getUser_email());
		map.put("user_nm", userVo.getUser_nm());
		map.put("prj_id", prjVo.getPrj_id());

//		if(frd_email == null) {
		
			// 회원이 해당 프로젝트의 멤버 목록을 조회 한다.
			Map<String, Object> resultMap = project_MemService.projectMemPagingList(map);
			
			logger.debug("map : 로거를 찍어보자 {}",map);
			
			List<UserVo> projectMemList = (List<UserVo>) resultMap.get("projectMemList");
			
			logger.debug("projectMemList : 프로젝트멤버에담긴거 {}",projectMemList);
			
			int paginationSize = (Integer) resultMap.get("paginationSize");
			
			String user_email0 = userVo.getUser_email();
			logger.debug("user_email : notInGame {}",user_email0);
			
			List<FriendsVo> prjMemFriList = friendsService.friendsList(user_email0);
			logger.debug("prjMemFriList : notInGame2 {}",prjMemFriList);
			
			model.addAttribute("prjMemFriList",prjMemFriList);
			model.addAttribute("projectMemList", projectMemList);
			model.addAttribute("paginationSize", paginationSize);
			model.addAttribute("pageVo", pageVo);
			
//		}else if (frd_email != null){
		
			// 회원의 친구 목록을 회원 자신의 이메일로 조회하여 페이징 리스트로 보여준다
			logger.debug("pageVo.getPage() ::::::::::: {}", pageVo.getPage());
			logger.debug("pageVo.getPageSize() ::::::::::: {}", pageVo.getPageSize());
			logger.debug("userVo.getUser_email() ::::::::::: {}", userVo.getUser_email());
			
			Map<String, Object> rstMap = friendsService.friendPagingList(map);	
			logger.debug("map : 밥먹기 전에 {}",rstMap);
			
			List<FriendsVo> friendsList = (List<FriendsVo>) rstMap.get("userFriendsList");
			logger.debug("friendsList : 로거를 {}",friendsList);
	
			int paginationSize1 = (Integer) rstMap.get("paginationSize");
			logger.debug("paginationSize : 찍어 봅시다 {}",paginationSize1);
			
			model.addAttribute("friendsList", friendsList);
			model.addAttribute("paginationSize", paginationSize1);
			model.addAttribute("pageVo", pageVo);

//			} else {
			
			// 친구 요청 받은 리스트 
			
			String req_email = userVo.getUser_email();
			
			logger.debug("user_email : {}", req_email);
			
			List<Friend_ReqVo> friendsRequestList = friend_ReqService.friendsRequestList(req_email);
			
			logger.debug("friendsRequestList : 이거 가져오나  볼까? {}",friendsRequestList);
			
			model.addAttribute("friendsRequestList", friendsRequestList);
			
//			}
			
			logger.debug("acceptEmail : 모닝로거1 {}",acceptEmail);
			logger.debug("denyEmail : 모닝로거2 {}",denyEmail);
			
			// 친구 요청 수락
			if(acceptEmail != null) {
				
				String user_email = userVo.getUser_email();
				logger.debug("user_email : 이거 가져오나 {}", user_email);
				
				UserVo uservo1 = userService.getUser(acceptEmail);
				String frd_email = uservo1.getUser_email();	
				req_email = uservo1.getUser_email();	
				
				Friend_ReqVo friendReqVo = new Friend_ReqVo(req_email, user_email);
				
				logger.debug("friendReqVo : 업데이트 테스트 {}",friendReqVo);
				
//				friendReqVo.setUser_email(user_email); // 문제 - 셋팅을 두번 해줘서
//				friendReqVo.setReq_email(req_email);   // 문제 - 셋팅을 두번 해줘서
				
				int updateReqAccept = friend_ReqService.updateReqAccept(friendReqVo);
				
				logger.debug("updateReqAccept : 업데이트 테스트1 {}",updateReqAccept);
				
				// 친구 요청 수락하면 보낸 사람을 내 친구로 등록
				FriendsVo friendsVo = new FriendsVo(user_email, frd_email);
				int acceptRequest = friendsService.accerptFriendRequest(friendsVo);
				
				// 친구 요청 수락하면 보낸 사람에 나를 친구로 등록
				FriendsVo friendsVo1 = new FriendsVo(frd_email, user_email);
				int acceptRequest1 = friendsService.insertFriends(friendsVo1);
				
				int denyRequest = friend_ReqService.deleteFriendRequest(acceptEmail);
				
			// 친구 요청 거절	
			} else if(denyEmail != null) {
				
				UserVo userVo2 = userService.getUser(denyEmail);
				String user_email = userVo2.getUser_email();
				
				logger.debug("userVo1 : 거절 Vo {}",userVo2);
				logger.debug("user_email : 거절 이메일 {}",user_email);
				
				UserVo uservo3 = (UserVo) session.getAttribute("USER_INFO");
				String frd_email = uservo3.getUser_email();	
				
				Friend_ReqVo friendReqVo = new Friend_ReqVo(user_email, frd_email);
				
				int updateReqDeny = friend_ReqService.updateReqDeny(friendReqVo);
				
				int denyRequest = friend_ReqService.deleteFriendRequest(user_email);
			
			// 프로젝트 멤버 리스트 중에서 친구 요청하기
			} else if (frdRequEmail != null) {
				
				String user_email = userVo.getUser_email();
				
				UserVo userVo3 = userService.getUser(frdRequEmail);
				req_email = userVo3.getUser_email();

				Friend_ReqVo friendsReqVo = new Friend_ReqVo(user_email, req_email); 

				logger.debug("user_email : 보낸사람 {}",user_email);
				logger.debug("req_email : 받는사람 {}",req_email);

				int firendsRequest = friend_ReqService.firendsRequest(friendsReqVo);
			}
			
			
		return "/member/projectMember.user.tiles";
	}
	
	/**
	 * 
	* Method : projectMemView
	* 작성자 : 김경호
	* 변경이력 : 2019-08-12
	* @return
	* Method 설명 : 멤버 - 프로젝트 멤버 탭에서 사용자가 한개의 프로젝트 멤버 tr을 클릭하였을때 상세 정보를 보여줌 
	 */
	@RequestMapping(path = "/projectMemView", method = RequestMethod.GET)
	public String projectMemView(Model model, String user_email) {
		
		logger.debug("user_email : 오늘 첫 로거 {}", user_email);
		
		UserVo userVo = new UserVo(user_email);
		
		model.addAttribute("userVo", userVo);
		
		return "/member/projectMember.user.tiles";
	}
	
	/**
	 * 
	* Method : deleteFriends
	* 작성자 : 김경호
	* 변경이력 : 2019-08-08
	* @param frd_email
	* @param pageVo
	* @param model
	* @param session
	* @param prjMemPaging
	* @param friendsPaging
	* @param user_email
	* @return
	* Method 설명 : 일반 사용자가 친구 리스트에서 자신의 친구를 삭제
	 */
	@RequestMapping(path = "/deleteFriends", method = RequestMethod.GET)
	public String deleteFriends(String frd_email, HttpSession session) {
		
		logger.debug("frd_email : 또굥 {}", frd_email);

		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		// 일반 사용자가 자신의 친구를 삭제
		FriendsVo friendsVo = new FriendsVo(user_email, frd_email);
		int deleteFriends = friendsService.deleteFriends(friendsVo);
		
		// 일반 사용자가 자신의 친구를 삭제하면 동시에 상대방에 친구 목록에서 사라짐 - 친구 삭제
		FriendsVo friendsVo2 = new FriendsVo(frd_email, user_email);
		int deleteFriends2 = friendsService.deleteFriends2(friendsVo2);
		
		
		return "/member/projectMember.user.tiles";
	}
	
	/**
	 * 
	* Method : projectMemberListProcess
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param pageVo
	* @param model
	* @param session
	 * @param frd_email 
	* @param frd_email
	* @param prjMemPaging
	* @param friendsPaging
	* @param user_email
	* @return
	* Method 설명 : 회원이 자신의 친구 리스트를 이메일로 검색하여 페이징으로 보여준다.
	 */
	@RequestMapping(path = "/friendsSearchList", method = RequestMethod.GET)
	public String projectMemberListProcess
		(Model model, String keyword, String selectBoxText,HttpSession session
				,@RequestParam(name = "page", defaultValue = "1")int page
				,@RequestParam(name = "pageSize", defaultValue = "10")int pageSize) {
			
		logger.debug("frd_email : 보내주냐고? {}", keyword);
		
		PageVo pageVo = new PageVo(page,pageSize);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		FriendsVo friendsVo = new FriendsVo();
		
		Map<String, Object> frd_email = new HashMap<String, Object>();
		
		frd_email.put("user_email", keyword);
		
		frd_email.put("page", pageVo.getPage());
		frd_email.put("pageSize", pageVo.getPageSize());
		frd_email.put("user_email", userVo.getUser_email());
//		frd_email.put("frd_email", friendsVo.getFrd_email());
		frd_email.put("frd_email", keyword);
		
		Map<String, Object> resultMap1 = friendsService.friendSearchByEmail(frd_email);
		
		List<FriendsVo> friendsList = (List<FriendsVo>) resultMap1.get("userFriendsList");

		logger.debug("friendsList : 또굥이 {}",friendsList);
		
		int paginationSize1 = (Integer) resultMap1.get("paginationSize");
		
		model.addAttribute("friendsList", friendsList);
		model.addAttribute("paginationSize", paginationSize1);
		model.addAttribute("pageVo", pageVo);
		
		return "/member/projectMember.user.tiles";
	}
	
	/**
	 * 
	* Method : insertFriendView
	* 작성자 : 김경호
	* 변경이력 : 2019-08-08
	* @param model
	* @param frd_email
	* @return
	* Method 설명 : 친구 추가
	 */
	@RequestMapping(path = "/friendsInsert", method = RequestMethod.GET)
	public String insertFriendView(Model model, String frd_email) {
		
		logger.debug("frd_email : 영하가 로거 찍어보라고 함 {}", frd_email);
		model.addAttribute("frd_email", frd_email);
		return "/member/projectMember.user.tiles";
	}

	@RequestMapping(path = "/friendsInsert", method = RequestMethod.POST)
	public String insertFriendProcess(String frd_email, HttpSession session){
		
		String viewName = "";
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		logger.debug("friendsVo : 영하가 로거 찍어보라고 함1 {}", user_email);
		
		FriendsVo friendsVo = new FriendsVo(user_email, frd_email);
		
		logger.debug("friendsVo : 영하가 로거 찍어보라고 함1 {}", friendsVo);
		
		int insertFriends = friendsService.insertFriends(friendsVo);
		
		if(insertFriends != 0) {
			viewName =  "/member/projectMember.user.tiles";
		}
		
		return viewName;
	}
	
	// ------------------------------ 타이머 ------------------------------ //
//	@RequestMapping(path = "/timer", method = RequestMethod.GET)
//	public String timer() {
//
//		return "/timer/timer.user.tiles";
//	}
	
	// ------------------------------ 관리자 부분 ------------------------------ //
	
	/**
	 * 
	* Method : admUserList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-01
	* @param pageVo
	* @param model
	* @return
	* Method 설명 : 관리자가 전체 회원의 리스트를 조회
	 */
	@RequestMapping(path = "/admUserList", method = RequestMethod.GET)
	public String admUserList(PageVo pageVo, Model model) {
		
		logger.debug("pageVo",pageVo);
		
		Map<String, Object> resultMap = userService.userPagingList(pageVo);
		
		List<UserVo> userList = (List<UserVo>) resultMap.get("userList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("userList", userList);
		
		logger.debug("userList : {}",userList);
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		
		return "/member/memberList.adm.tiles";
	}
	
	/**
	 * 
	* Method : insertUser
	* 작성자 : 김경호
	* 변경이력 : 2019-08-01
	* @param user_email
	* @param model
	* @return
	* Method 설명 : 관리자가 회원을 등록
	 */
	@RequestMapping(path = "admInsertUser", method = RequestMethod.GET)
	public String admInsertUser(String user_email, Model model) {
		model.addAttribute("user_email", user_email);
		logger.debug("user_email",user_email);
		return "/member/memberForm.adm.tiles";
	}
	
	@RequestMapping(path = "admInsertUser", method = RequestMethod.POST)
	public String admInsertUserProcess(UserVo userVo,String user_email, 
			String user_pass, Model model) throws InvalidKeyException, UnsupportedEncodingException {
		
		userVo.setUser_pass(ARIAUtil.ariaEncrypt(userVo.getUser_pass()));

		logger.debug("userVo : {}", userVo);
		logger.debug("user_email : {}", user_email);
		logger.debug("user_pass : {}", user_pass);
		
		int admInsertUser = userService.insertUser(userVo);

		// return "redirect:/login";
		return "/member/memberForm.adm.tiles";
	}
	
	/**
	 * 
	* Method : admUserView
	* 작성자 : 김경호
	* 변경이력 : 2019-08-02
	* @param session
	* @param model
	* @return
	* Method 설명 : 관리자가 회원의 리스트에서 회원 정보를 상세하게 조회
	 */
	@RequestMapping(path = "admUserView", method = RequestMethod.GET)
	public String admUserView(HttpSession session, Model model, String getMemInfo) {
		logger.debug("getMemInfo : {}", getMemInfo);

		UserVo userVo = userService.getUser(getMemInfo);
		model.addAttribute("userVo",userVo);
		
		logger.debug("userVo : {}", userVo);
		
		return "/member/memberView.adm.tiles";
	}
	
	/**
	 * 
	* Method : admUpdateUser
	* 작성자 : 김경호
	* 변경이력 : 2019-08-02
	* @return
	* Method 설명 : 관리자가 회원의 정보를 수정
	 */
	@RequestMapping(path = "/admUpdateUser", method = RequestMethod.GET)
	public String admUpdateUser(HttpSession session, Model model, String admUpdate) {
		
		logger.debug("admUpdate 지난주 테스트 : {}",admUpdate);
		logger.debug("----------------------------------------------");
		
		UserVo userVo = userService.getUser(admUpdate);
		logger.debug("userVo 지난주 테스트 : {}",userVo);
		
		model.addAttribute("userVo",userVo);
		return "/member/memberUpdate.adm.tiles";
	}
	
	@RequestMapping(path = "/admUpdateUser", method = RequestMethod.POST)
	public String admUpdateUserProcess(String admUpdate,String user_email, String user_nm, 
										String user_hp, String user_st) {
		
		logger.debug("admUpdate : {}",admUpdate);
		
		UserVo userVo = new UserVo();
		userVo.setUser_email(user_email);
		userVo.setUser_nm(user_nm);
		userVo.setUser_hp(user_hp);
		userVo.setUser_st(user_st);
		
		logger.debug("user_email 아침 테스트 : {}",user_email);
		logger.debug("user_nm 아침 테스트 : {}",user_nm);
		logger.debug("user_hp 아침 테스트 : {}",user_hp);
		logger.debug("user_st 아침 테스트 : {}",user_st);
		
		int admUpdateUser = userService.updateUserAdm(userVo);
		
		return "/member/memberUpdate.adm.tiles";
	}
	
	/**
	 * 
	* Method : admSearchUserInfo
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param model
	* @param searchText
	* @param selectBoxText
	* @param session
	* @param page
	* @param pageSize
	* @return
	* Method 설명 : 관리자가 이메일,이름,전화번호로 회원을 검색하여 페이징 리스트로 보여줌
	 */
	@RequestMapping(path = "/admUserInfoSearch",method = RequestMethod.GET)
	public String admSearchUserInfo
					(Model model, String keyword, String selectBoxText,HttpSession session
					,@RequestParam(name = "page", defaultValue = "1")int page
					,@RequestParam(name = "pageSize", defaultValue = "10")int pageSize) {
		
		PageVo pageVo = new PageVo(page, pageSize);
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
//		String user_email = userVo.getUser_email();
//		String user_email = keyword;
		
		Map<String, Object> search = new HashMap<String, Object>();
		
		search.put("user_email", keyword);
		
		search.put("page", page);
		search.put("pageSize", pageSize);
		
//		search.put("user_email", userVo.getUser_email());
//		search.put("user_email", user_email);
		
		logger.debug("searchText : {}",keyword);
		logger.debug("page : {}",page);
		logger.debug("pageSize : {}",pageSize);
//		logger.debug("user_email : {}",user_email);
		
		logger.debug("selectBoxText : {}",selectBoxText);
		
		if(selectBoxText.equals("userEmail")) {
			Map<String, Object> resultMap = userService.userSearchByEmail(search);
			
			logger.debug("resultMap 오후 로거 테스트 : {}",resultMap);
			
			List<UserVo> userEmailSearchList = (List<UserVo>) resultMap.get("admSearchEmailList");
			
			logger.debug("userEmailSearchList 오후 로거 테스트 : {}",userEmailSearchList);
			
			int paginationSize = (Integer) resultMap.get("paginationSize");
			
			logger.debug("paginationSize 오후 로거 테스트 : {}",paginationSize);
			
			model.addAttribute("userList" , userEmailSearchList);
			model.addAttribute("paginationSize", paginationSize);
			model.addAttribute("pageVo", pageVo);
			
		}
//		else if(selectBoxText.equals("userNm")) {
//			Map<String, Object> resultMap = userService.userSearchByEmail(search);
//			List<UserVo> userNmSearchList = (List<UserVo>) resultMap.get("admSearchNameList");
//			int paginationSize = (Integer) resultMap.get("paginationSize");
//			
//			model.addAttribute("userNmSearchList", userNmSearchList);
//			model.addAttribute("paginationSize", paginationSize);
//			model.addAttribute("pageVo", pageVo);
//		}else if(selectBoxText.equals("userHp")) {
//			Map<String, Object> resultMap = userService.userSearchByEmail(search);
//			List<UserVo> userHpSearchList = (List<UserVo>) resultMap.get("admSearchHpList");
//			int paginationSize = (Integer) resultMap.get("paginationSize");
//			
//			model.addAttribute("userHpSearchList", userHpSearchList);
//			model.addAttribute("paginationSize", paginationSize);
//			model.addAttribute("pageVo", pageVo);
//		}
		return "/member/memberList.adm.tiles";
	}

}
