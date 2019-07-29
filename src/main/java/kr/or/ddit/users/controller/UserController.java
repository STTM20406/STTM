package kr.or.ddit.users.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.encrypt.encrypt.kisa.aria.ARIAUtil;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource(name = "userService")
	private IUserService userService;
	
	/**
	 * 
	* Method : setAccountView
	* 작성자 : 김경호
	* 변경이력 : 2019-07-27
	* @return
	* Method 설명 : 계정 설정 요청 및 프로세스
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(path = "/accountSet", method = RequestMethod.GET)
	public String setPassView(HttpSession session, Model model) throws InvalidKeyException, UnsupportedEncodingException {
		
		// 
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		
		logger.debug("userVo : {} 123", userVo );
		
		// 암호화된  비밀번호 가져오기
		String encodePass = userVo.getUser_pass();
		
		// 암호화된 비밀번호 복호화
		String decodePass = ARIAUtil.ariaDecrypt(encodePass);
		
		// ah!
		String user_email = userVo.getUser_email();
		
//		model.addAttribute("user_pass", userVo.getUser_pass());
		model.addAttribute("user_email", user_email);
		model.addAttribute("user_pass", decodePass);

		logger.debug("user_pass : {}", userVo.getUser_pass());
		return "/account/accountSet.user.tiles";
	}
	
	@RequestMapping(path = "/accountSet", method = RequestMethod.POST)
	public String setPassProcess(String user_pass, HttpSession session) throws InvalidKeyException, UnsupportedEncodingException {
		
		String viewName = "";
		
		UserVo userVo2 = (UserVo) session.getAttribute("USER_INFO"); 
		String user_email = userVo2.getUser_email();
		
		UserVo userVo = new UserVo();
		userVo.setUser_email(user_email);
//		userVo.setUser_pass(user_pass);
		userVo.setUser_pass(ARIAUtil.ariaEncrypt(user_pass));
		
		int updateUserPass = userService.updateUserPass(userVo);
		
		if(updateUserPass != 0) {
			 viewName = "/account/accountSet.user.tiles";
		}
		
		return viewName;
	}
	
	/**m
	 * 
	* Method : setInactiveAccountView
	* 작성자 : 김경호
	* 변경이력 : 2019-07-29
	* @param session
	* @param model
	* @return
	* Method 설명 : 휴명 계정 설정 화면 요청 밒 프로세스
	 */
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String setInactiveAccountView(HttpSession session, Model model) {
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		String user_st = userVo.getUser_st();
		
		model.addAttribute("user_email", user_email);
		model.addAttribute("user_st", user_email);
		
		return "/account/accountSet.user.tiles";
	}
	
	@RequestMapping(path = "/", method = RequestMethod.POST)
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
	
}
