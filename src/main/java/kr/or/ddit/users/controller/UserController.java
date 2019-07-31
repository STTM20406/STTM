package kr.or.ddit.users.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.encrypt.encrypt.kisa.aria.ARIAUtil;
import kr.or.ddit.notification_set.model.Notification_SetVo;
import kr.or.ddit.notification_set.service.INotification_SetService;
import kr.or.ddit.project.service.IProjectService;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.project_mem.service.IProject_MemService;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;
import oracle.net.aso.s;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "notification_SetService")
	private INotification_SetService notification_SetService;
	
	@Resource(name = "projectService")
	private IProjectService ProjectService;
	
	@Resource(name = "project_MemService")
	private IProject_MemService project_MemService;
	
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
		
		logger.debug("userVo : {} 123", userVo );
		
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
		
//		// 비밀번호 재설정
//		int updateUserPass = userService.updateUserPass(userVo);
//		
//		if(updateUserPass != 0) {
//			 viewName = "/account/accountSet.user.tiles";
//		}
//		
//		int updateUserProfile = userService.updateUserProfile(userVo);
//		
//		logger.debug("updateUserProfile : {} 가져오거라", updateUserProfile);
//		
//		if(updateUserProfile != 0) {
//			return "/account/accountSet.user.tiles";
//		}
//		
//		return viewName;
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
	public String setInactiveAccountView(HttpSession session, Model model, int prj_id) {
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		logger.debug("userVo : {} 가져오나!", userVo);
		
		String user_email = userVo.getUser_email();
		String user_st = userVo.getUser_st();
		logger.debug("user_email : {} 가져오나?", user_email);
		logger.debug("user_st : {} 가져오니?", user_st);
		
		model.addAttribute("user_email", user_email);
		model.addAttribute("user_st", user_st);
		
		List<Project_MemVo> getMyPrjMemList = userService.getMyProjectMemList(prj_id);
		logger.debug("getMyPrjMemList : {} 가져오너라",getMyPrjMemList);
		
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
	* Method : setUserProfile
	* 작성자 : 김경호
	* 변경이력 : 2019-07-30
	* @return
	* Method 설명 : 일반 사용자 계정 설정
	 */
	@RequestMapping(path = "/setUserProfile", method = RequestMethod.GET)
	public String setUserProfile(HttpSession session, Model model) {
		
//		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
//		String user_nm = userVo.getUser_nm();
//		String user_hp = userVo.getUser_hp();
//		
//		model.addAttribute("user_nm", user_nm);
//		model.addAttribute("user_hp", user_hp);
		
		return "/account/accountSet.user.tiles";
	}
							
	@RequestMapping(path = "/setUserProfile", method = RequestMethod.POST)
	public String setUserProfileProcess(HttpSession session) {
		
//		String viewName = "";
//		
//		UserVo getUserSession = (UserVo) session.getAttribute("USER_INFO");
//		String user_email = getUserSession.getUser_email();
//		
//		String user_nm = getUserSession.getUser_nm();
//		String user_hp = getUserSession.getUser_hp();
//		
//		logger.debug("user_nm : {} 아까는 가져왔으나", user_nm);
//		logger.debug("user_hp : {} 지금은 가져오지 않는다", user_hp);
//		
//		UserVo userVo = new UserVo();
//		userVo.setUser_nm(user_nm);
//		userVo.setUser_hp(user_hp);
//
//		int updateUserProfile = userService.updateUserProfile(userVo);
//		
//		if(updateUserProfile != 0) {
			return "/account/accountSet.user.tiles";
//		}
//		return viewName;
	}
	
}
