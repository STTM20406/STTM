package kr.or.ddit.register;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import kr.or.ddit.encrypt.encrypt.kisa.aria.ARIAUtil;
import kr.or.ddit.encrypt.encrypt.kisa.sha256.KISA_SHA256;
import kr.or.ddit.mail_confirm.service.IMail_ConfirmService;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;

@Controller
public class RegisterController {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "mail_ConfirmService")
	private IMail_ConfirmService mailConfirmService;
	
	@Resource(name = "javaMailSender")
	private JavaMailSender mailSender;
	
	/**
	 * 
	* Method : registerTest
	* 작성자 : 김경호
	* 변경이력 : 2019-07-25
	* @return
	* Method 설명 : 정규식 테스트용 화면
	 */
	@RequestMapping(path = "regTest", method = RequestMethod.GET)
	public String registerTest() {
		return "member/regTest";
	}
	
	/**
	 * 
	* Method : registerView
	* 작성자 : 김경호
	* 변경이력 : 2019-07-22
	* @param user_email
	* @param model
	* @return
	* Method 설명 : 회원 등록 화면 요청 및 프로세스
	 */
	@RequestMapping(path = "registerForm", method = RequestMethod.GET)
	public String registerView(UserVo userVo, String user_Email, Model model) {
		model.addAttribute("user_Email", user_Email);
		return "member/registerForm";
	}
	
	@RequestMapping(path = "registerForm", method = RequestMethod.POST)
	public String registerViewProcess(UserVo userVo,HttpSession session, String user_email, String user_nm, String user_pass, String user_hp) throws InvalidKeyException, UnsupportedEncodingException {
		logger.debug("user_email : {}", user_email);
		logger.debug("user_nm : {}", user_nm);
		logger.debug("user_pass : {}", user_pass);
		logger.debug("user_hp 이거: {}", user_hp);
		
		// 비밀번호 암호화
//		userVo.setUser_pass(KISA_SHA256.encrypt(userVo.getUser_pass()));
		userVo.setUser_pass(ARIAUtil.ariaEncrypt(userVo.getUser_pass()));
		
		int insertUser = userService.insertUser(userVo);
		return "redirect:/login";
	}
	
	/**
	 * 
	* Method : joinView
	* 작성자 : 김경호
	* 변경이력 : 2019-07-25
	* @param session
	* @return
	* Method 설명 : 회원가입을 진행하기 위해 일반 이메일 or google이메일을 선택하는 화면
	* 			     회원가입을 위해 이메일 인증을 할떄 DB에 있는 이메일이면 경고창을 띄워주고 다시 입력하게 함
	 */
	@RequestMapping(path = "/register", method = RequestMethod.GET)
	public String joinView(Model model) {
		return "member/register";
	}
	
	/**
	 * 
	* Method : sendEmail
	* 작성자 : 김경호
	* 변경이력 : 2019-07-22
	* @param user_email
	* @param model
	* @return
	* @throws MessagingException
	* Method 설명 : 회원가입을 위한 이메일 인증
	 */
	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public String sendEmail(String user_email, Model model) throws MessagingException {
		logger.debug("user_email : 이걸 가져온다고? {}", user_email);
		
		// 
		UserVo userVo = userService.getUser(user_email);
		
		sendEmailForJoin(user_email);
		model.addAttribute("user_email", user_email);
		
		return "member/sendEmail";
	}
	
	/**
	 * 
	* Method : sendEmailForJoin
	* 작성자 : 김경호
	* 변경이력 : 2019-07-22
	* @param user_email
	* @throws MessagingException
	* Method 설명 : 
	 */
	public void sendEmailForJoin(String user_email) throws MessagingException {
		
		MimeMessage message = mailSender.createMimeMessage();
		// 메일을 Multipart 형태로 보낼지 설정(false = Multipart형태로 보내지 않음)
		MimeMessageHelper msgHelper = new MimeMessageHelper(message, true);
		
		msgHelper.setFrom("notification.sttm@gmail.com"); 		// 메일 발신인 설정
		msgHelper.setTo(user_email);								// 메일 수신인 설정
		msgHelper.setSubject("[STTM] 회원 가입을 위해 이메일을 인증해주세요 .");	// 메일 제목 설정
		
		String html = "<div>" +
					  	"<span> 아래 버튼을 클릭하여 회원님의 계정을 인증해주세요. <br>" +
//					  	"<a href='http://localhost/registerForm?user_email="+ user_email +"'>" +
					  	"<a href='http://localhost/registerForm'>" +
					  	"인증하기" + "</a>" + 
					  "</div>";
					  
						// 메일 내용 작성 -> HTML코드 형태로 작성했음
		msgHelper.setText(html,true); // 메일 내용 설정. (내용, HTML 여부 true/false)
		mailSender.send(message); // 메일 발신
	}
	
	/**
	 * 
	* Method : googleRegisterView
	* 작성자 : 김경호
	* 변경이력 : 2019-07-25
	* @param googleEmail
	* @param model
	* @return
	* Method 설명 : 구글 아이디로 회원 가입
	 */
	@RequestMapping(path = "googleRegisterForm", method = RequestMethod.GET)
	public String googleRegisterView(String googleEmail, Model model) {
		model.addAttribute("googleEmail", googleEmail);
		return "member/googleRegisterForm";
	}
	
	@RequestMapping(path = "googleRegisterForm", method = RequestMethod.POST)
	public String googleRegisterViewProcess(UserVo userVo) {
		int insertGoogleEmail = userService.insertUser(userVo);
		return "member/googleRegisterForm";
	}
}
