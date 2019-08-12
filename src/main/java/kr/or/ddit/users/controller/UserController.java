package kr.or.ddit.users.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
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
	public String setPassView(HttpSession session, Model model) throws InvalidKeyException, UnsupportedEncodingException {
		
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
		return "/account/accountSet.user.tiles";
	}
	
	@RequestMapping(path = "/setUserPass", method = RequestMethod.POST)
	public String setPassProcess(String user_pass, String user_nm, String user_hp, HttpSession session) throws InvalidKeyException, UnsupportedEncodingException {
		String viewName = "";
		
		UserVo getUserSession = (UserVo) session.getAttribute("USER_INFO"); 
		String user_email = getUserSession.getUser_email();
//		String user_nm = getUserSession.getUser_nm();
//		String user_hp = getUserSession.getUser_hp();
		
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
	public String setInactiveAccountView(HttpSession session, Model model) {
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		logger.debug("userVo : {} 가져오나!", userVo);
		
		String user_email = userVo.getUser_email();
		String user_st = userVo.getUser_st();
		logger.debug("user_email : {} 가져오나?", user_email);
		logger.debug("user_st : {} 가져오니?", user_st);
		
		Project_MemVo prjMemVo = new Project_MemVo();
		int prj_id = prjMemVo.getPrj_id();
		model.addAttribute("prj_id", prj_id);
		logger.debug("prj_id : {} 이제 찍히나요?",prj_id);
		
		List<Project_MemVo> getMyPrjMemList = project_MemService.getMyProjectMemList(prj_id);
		logger.debug("getMyPrjMemList : {} 이거 가져오나요?",getMyPrjMemList);

		model.addAttribute("user_email", user_email);
		model.addAttribute("user_st", user_st);
		model.addAttribute("getMyPrjMemList", getMyPrjMemList);
		
		return "/account/accountSet.user.tiles";
	}
	
	@RequestMapping(path = "/setUserStatus", method = RequestMethod.POST)
	public String setInactiveAccountProcess(HttpSession session, String user_st) {
		
		String viewName = "";
		
		UserVo userSession = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userSession.getUser_email();
		
		UserVo userVo = new UserVo();
		userVo.setUser_email(user_email);
		userVo.setUser_st(user_st);
		
		int updateUserStatus = userService.updateUserStatus(userVo);
		
		if(updateUserStatus != 0) {
			viewName = "/account/accountSet.user.tiles"; 
		}
		
		return viewName;
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
	public String projectMemberListView(PageVo pageVo, Model model, HttpSession session) {
		
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
		List<UserVo> projectMemList = (List<UserVo>) resultMap.get("projectMemList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
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
			
//		}
		
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
	public String deleteFriends(String frd_email,PageVo pageVo, Model model, 
			HttpSession session
			,String prjMemPaging, String friendsPaging, Map<String, Object> user_email) {
		
		logger.debug("frd_email : 또굥 {}", frd_email);

		int deleteFriends = friendsService.deleteFriends(frd_email);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		Project_MemVo prjVo = new Project_MemVo();
		
		logger.debug("userVo : 점심쯤 로거 확인1 {} ",userVo);
		
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
	
	// ------------------------------ 관리자 부분 ------------------------------ //
	
	/**
	 * 
	* Method : UserPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-01
	* @param pageVo
	* @param model
	* @return
	* Method 설명 : 관리자가 전체 회원의 리스트를 조회
	 */
	@RequestMapping("/admUserList")
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
