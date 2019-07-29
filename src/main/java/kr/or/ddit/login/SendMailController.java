package kr.or.ddit.login;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.encrypt.encrypt.kisa.aria.ARIAUtil;
import kr.or.ddit.mail_confirm.service.IMail_ConfirmService;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;

@Controller
public class SendMailController {
	
	private static final Logger logger = LoggerFactory.getLogger(SendMailController.class);
	
	@Resource(name = "userService")
	public IUserService userService;
	
	@Resource(name = "mail_ConfirmService")
	private IMail_ConfirmService mailConfirmService;
	
	@Resource(name = "javaMailSender")
	private JavaMailSender mailSender;
	
	/**
	 * 
	* Method : resetPw
	* 작성자 : 김경호
	* 변경이력 : 2019-07-26
	* @return
	* Method 설명 : 비밀번호 재설정을 하기 위해 이메일을 보내는 화면 요청
	 */
	@RequestMapping(path = "resetPassword", method = RequestMethod.GET)
	public String resetPw() {
		return "member/resetPassword";
	}
	
	/**
	 * 
	* Method : resetPw
	* 작성자 : 김경호
	* 변경이력 : 2019-07-26
	* @param userEamil
	* @param model
	* @return
	* @throws MessagingException
	* Method 설명 : 비밀번호 찾기를 위한 이메일 인증
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(path = "resetPassword", method = RequestMethod.POST)
	private String resetPw(UserVo userVo, String user_email, String user_nm, String user_pass, Model model) throws MessagingException, InvalidKeyException, UnsupportedEncodingException {
		
		UserVo resetPass = userService.getUser(user_email);
		
		logger.debug("user_email : {} 가져오나",user_email);
		logger.debug("user_nm : {} ", user_nm);
		logger.debug("user_pass : {} ", user_pass);
		logger.debug("resetPass : {} ", resetPass);
		
		sendEmailForResetPw(userVo, user_email, userVo.getUser_pass(), model);
		model.addAttribute("user_email", user_email);
		return "member/sendEmail";
	
	}
	/**
	 * 
	* Method : sendEmailForResetPw
	* 작성자 : 김경호
	* 변경이력 : 2019-07-26
	* @param user_email
	* @throws MessagingException
	* Method 설명 :
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 */
	public void sendEmailForResetPw(UserVo userVo, String user_email, String user_pass, Model model) throws MessagingException, InvalidKeyException, UnsupportedEncodingException {
		
		logger.debug("user_email : {}", user_email);
		
		UserVo getPass = userService.getUser(user_email);
		
		logger.debug("getPass : {}", getPass);
		
		String use_pass = getPass.getUser_pass();
		
		// 암호문을 복호화 시킨다.
		String decodePass = ARIAUtil.ariaDecrypt(use_pass);
		
		MimeMessage message = mailSender.createMimeMessage();
		// 메일을 Multipart 형태로 보낼지 설정(false = Multipart형태로 보내지 않음)
		MimeMessageHelper msgHelper = new MimeMessageHelper(message, true);
		
		msgHelper.setFrom("notification.sttm@gmail.com"); 		// 메일 발신인 설정
		msgHelper.setTo(user_email);								// 메일 수신인 설정
		msgHelper.setSubject("[STTM] 비밀번호 찾기 .");	// 메일 제목 설정
		
		String html = "<div>" +
					  	"<span> "+ user_email +"회원님의 비밀번호는 " +
					  	decodePass +
					  	"입니다." +
					  	"로그인 후 비밀번호를 재 설정해주세요." +
//					  	"<a href='http://localhost/registerForm?user_email="+ user_email +"'>" +
					  	"<a href='http://localhost/login'>" +
					  	"STTM 바로가기" + "</a>" + 
					  "</div>";
					  
						// 메일 내용 작성 -> HTML코드 형태로 작성했음
		msgHelper.setText(html,true); // 메일 내용 설정. (내용, HTML 여부 true/false)
		mailSender.send(message); // 메일 발신
		
	}
	
}
