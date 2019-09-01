package kr.or.ddit.login;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.board.model.BoardVo;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.chat_room.model.Chat_RoomVo;
import kr.or.ddit.chat_room.service.IChat_RoomService;
import kr.or.ddit.encrypt.encrypt.kisa.aria.ARIAUtil;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project.service.IProjectService;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.project_mem.service.IProject_MemService;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "projectService")
	private IProjectService projectService;
	
	@Resource(name= "project_MemService")
	private IProject_MemService projectMemService;
	
	@Resource(name = "boardService")
	private IBoardService boardService;
	
	@Resource(name = "chat_RoomService")
	private IChat_RoomService chatroomService;
	
	/**
	 * 
	* Method : loginView
	* 작성자 : 김경호
	* 변경이력 :
	* @return
	* Method 설명 : 사용자 로그인 화면 요청
	 */
	@RequestMapping(path="/login", method=RequestMethod.GET)
	public String loginView(HttpSession session, UserVo userVo, Model model) {
		userVo = (UserVo) session.getAttribute("USER_INFO");
		if(userVo!=null) {
			
			// 추후 수정
			if("A".equals(userVo.getUser_right())) {
				return "/main/main.adm.tiles";
			}else {
				
				//세션에 저장된 user 정보를 가져옴
				UserVo user = (UserVo) session.getAttribute("USER_INFO");  
				String user_email = user.getUser_email();
				
				//프로젝트 리스트를 불러옴
				model.addAttribute("projectList", projectService.projectList(user_email));
				
				return "/projectList/projectList.user.tiles";
			}
			
		}else {
			return "member/login";
		}
	}
	
	/**
	 * 
	* Method : loginProcess
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param user_email
	* @param user_pass
	* @param response
	* @param session
	* @return
	* Method 설명 : 사용자 로그인 요청 처리
	 * @throws UnsupportedEncodingException 
	 * @throws InvalidKeyException 
	 */
	@RequestMapping(path = "login", method = RequestMethod.POST)
	public String loginProcess(String user_email, String user_pass, HttpServletRequest request,HttpServletResponse response, HttpSession session, Model model) throws InvalidKeyException, UnsupportedEncodingException {
		
		//비밀번호 암호화
		String encryptPassword = ARIAUtil.ariaEncrypt(user_pass);
		UserVo userVo = userService.getUser(user_email);
		String user_st = userVo.getUser_st();
		
		logger.debug("user_st : 계정 상태 {}", user_st);
		
		// 회원이 속한 프로젝트리스트 조회
		List<ProjectVo> projectList = projectService.projectList(user_email);
		
		//userVo에 값이 있고 userVo에 저장된 패스워드가 생성된 패스워드와 같다면
		if(userVo != null && encryptPassword.equals(userVo.getUser_pass())) {
//		if(userVo != null && encryptPassword.equals(userVo.getUser_pass()) && "N".equals(user_st)) {
			
			//세션 생성 userVo를 USER_INFO에 담음
			session.setAttribute("USER_INFO", userVo);
			
			session.setAttribute("projectList", projectList);
			
			//user의 권한이 'A' = 관리자이면
			if("A".equals(userVo.getUser_right())) {
				
				// 게시판 리스트
				List<BoardVo> admBoardListY = boardService.boardListYes();
				ServletContext sc = request.getServletContext();
				sc.setAttribute("admBoardListY", admBoardListY);
				
				return "/main/main.adm.tiles";
			}else{
				
				//세션에 저장된 user 정보를 가져옴
				UserVo user = (UserVo) session.getAttribute("USER_INFO");    
				user_email = user.getUser_email();
				String user_nm = user.getUser_nm();
				
				//프로젝트 리스트를 불러옴
				model.addAttribute("projectList", projectService.projectList(user_email));
				model.addAttribute("user_nm", user_nm);
				
				ServletContext sc = request.getServletContext();
				
				//left에 게시판 리스트
				List<BoardVo> userBoardListY = boardService.boardListYes();
				sc.setAttribute("userBoardListY", userBoardListY); 
				
				//header에 로그인한 사람의 채팅방 리스트
				sc.setAttribute("headerProjectList", projectList);
				return "/projectList/projectList.user.tiles";
			}
 
		}else{
			return "member/login";
		}
	}
	
	/**
	 * 
	 * Method : logoutProcess
	 * 작성자 : 김경호
	 * 변경이력 : 2019-07-21
	 * @param response
	 * @param session
	 * @return
	 * Method 설명 : 사용자 로그아웃 요청 처리
	 */
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logoutProcess(HttpServletResponse response,HttpSession session) {
		session.invalidate(); // 세션 정보 날리기
		return "redirect:/login";
	}
	
}
