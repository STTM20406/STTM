package kr.or.ddit.minutes.controller;

import java.util.Arrays;
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

import kr.or.ddit.minutes.model.MinutesVo;
import kr.or.ddit.minutes.model.Minutes_MemVo;
import kr.or.ddit.minutes.service.IMinutesService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.users.model.UserVo;

@Controller
public class MinutesController {
	
	private static final Logger logger = LoggerFactory.getLogger(MinutesController.class);
	
	@Resource(name="minutesService")
	IMinutesService minutesService;
	
//	@RequestMapping(name="workService")
//	private IWorkService workService;
	
	@RequestMapping("/conferenceList")
	String conferenceList( ) {
		logger.debug("♬♩♪  conferenceList입니다!}");		
		return "/main/conference/conferenceList.user.tiles";
	}			
	
	@RequestMapping("/conferencePagination")
	String conferencePagination(Model model, PageVo pageVo, HttpSession session ) {
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("prj_id", prj_id);
		
		Map<String, Object> resultMap = minutesService.minutesPagination(map);
		List<MinutesVo> minutesList = (List<MinutesVo>) resultMap.get("minutesList");
		
		int paginationSize = (Integer) resultMap.get("paginationSize");
		logger.debug("♬♩♪  paginationSize: {}", paginationSize);
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		
		model.addAttribute("minutesList", minutesList);
		model.addAttribute("prj_id", prj_id);
		
		return "jsonView";
	}			
	
	@RequestMapping(path="/conferenceDetail", method = RequestMethod.GET)
	String conferenceDetail(Model model, int mnu_id, HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_nm = userVo.getUser_nm();
		model.addAttribute("user_nm", user_nm);
		
		MinutesVo minutesVo = minutesService.minutesDetail(mnu_id);
		model.addAttribute("minutesVo", minutesVo);
		
		//참석자 리스트를 받아오는
		List<Minutes_MemVo> minutes_memList = minutesService.attenderList(mnu_id);
		model.addAttribute("minutes_memList", minutes_memList);
		logger.debug("♬♩♪  minutes_memList: {}", minutes_memList);
		
		return "/main/conference/conferenceDetail.user.tiles";
	}
	
	//수정해야합니다!!!!!!!!!!!!!!!!!!!!!!!!!!! 검색했을때 페이지 네이션
	@RequestMapping("/searchMinutes")
	String searchMinutes(PageVo pageVo, Model model,  String user_nm, HttpSession session) {
		logger.debug("♬♩♪  name_nm이 들어오나요?: {}", user_nm);
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("user_nm", user_nm);
		map.put("prj_id", prj_id);
		
		Map<String, Object> resultMap = minutesService.searchPagination(map);
		List<MinutesVo> searchList = (List<MinutesVo>) resultMap.get("searchList");
		logger.debug("♬♩♪  searchList: {}", searchList);
		int paginationSize = (Integer) resultMap.get("paginationSize");
		logger.debug("♬♩♪  paginationSize: {}", paginationSize);

		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);

		model.addAttribute("searchList", searchList);
		model.addAttribute("user_nm", user_nm);
		model.addAttribute("prj_id", prj_id);
	
		return "jsonView";
	}
	
	/**
	 * Method 		: delMinutes
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-09 최초 생성
	 * @param model
	 * @param mnu_id
	 * @param pageVo
	 * @return
	 * Method 설명 	: 상태값 Y로 바꾸는 삭제 메서드
	 */
	@RequestMapping(path="/delMinutes", method = RequestMethod.GET)
	String delMinutes(Model model, int mnu_id, PageVo pageVo) {
		logger.debug("♬♩♪ delMinutes controller입니다. mnu_id: {}", mnu_id);
		int upCnt = minutesService.upMinutes(mnu_id);
		
		if(upCnt==1) {
			logger.debug("♬♩♪  삭제완료!(상태 변환완료Y)");
			int prj_id = 1; //session에서 꺼내자
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("page", pageVo.getPage());
			map.put("pageSize", pageVo.getPageSize());
			map.put("prj_id", prj_id);
			
			Map<String, Object> resultMap = minutesService.minutesPagination(map);
			List<MinutesVo> minutesList = (List<MinutesVo>) resultMap.get("minutesList");
			
			int paginationSize = (Integer) resultMap.get("paginationSize");

			model.addAttribute("paginationSize", paginationSize);
			model.addAttribute("pageVo", pageVo);

			model.addAttribute("minutesList", minutesList);
			model.addAttribute("prj_id", prj_id);
		}
		return "/main/conference/conferenceList.user.tiles";
	}
	
	/**
	 * Method 		: upMinutes
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-09 최초 생성
	 * @param model
	 * @param mnu_id
	 * @param pageVo
	 * @param session
	 * @return
	 * Method 설명 	: 수정 메서드
	 */
	@RequestMapping(path="/upMinutes", method = RequestMethod.GET)
	String upMinutesGet(Model model, int mnu_id, PageVo pageVo, HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_nm = userVo.getUser_nm();
		model.addAttribute("user_nm", user_nm);
		
		MinutesVo minutesVo = minutesService.minutesDetail(mnu_id);
		model.addAttribute("minutesVo", minutesVo);
		
		//참석자 리스트를 받아오는
		List<Minutes_MemVo> minutes_memList = minutesService.attenderList(mnu_id);
		model.addAttribute("minutes_memList", minutes_memList);
		logger.debug("♬♩♪  minutes_memList: {}", minutes_memList);
		
		return "/main/conference/conferenceModify.user.tiles";
	}
	
	@RequestMapping(path="/upMinutes", method = RequestMethod.POST)
	String upMinutesPost(Model model, int mnu_id, PageVo pageVo, HttpSession session, 
			String subject,String special ) {
		logger.debug("♬♩♪  updtae Minutes mnu_id: {}", mnu_id);
		logger.debug("♬♩♪ subject: {}", subject);
		logger.debug("♬♩♪ special: {}", special);
		
		MinutesVo minutesVo = new MinutesVo();
		minutesVo.setMnu_id(mnu_id);
		minutesVo.setSubject(subject);
		minutesVo.setSpecial(special);
		
		minutesService.updateMinutes(minutesVo);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_nm = userVo.getUser_nm();
		model.addAttribute("user_nm", user_nm);
		
		MinutesVo minutesVo1 = minutesService.minutesDetail(mnu_id);
		model.addAttribute("minutesVo", minutesVo1);
		
		//참석자 리스트를 받아오는
		List<Minutes_MemVo> minutes_memList = minutesService.attenderList(mnu_id);
		model.addAttribute("minutes_memList", minutes_memList);
		logger.debug("♬♩♪  minutes_memList: {}", minutes_memList);
		
		return "/main/conference/conferenceDetail.user.tiles";
	}
	
	/**
	 * Method 		: insertConference
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-09 최초 생성
	 * @return
	 * Method 설명 	: 회의록 등록
	 */
	@RequestMapping(path="/insertConference", method = RequestMethod.GET)
	String insertConferenceGet(HttpSession session, Model model) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		List<UserVo> userList = minutesService.memberList(user_email);
		logger.debug("♬♩♪  userList: {}", userList);
		model.addAttribute("userList", userList);
		return "/main/conference/conferenceInsert.user.tiles";
	}
	
	@RequestMapping(path="/insertConference", method = RequestMethod.POST)
	String insertConferencePost(HttpSession session, Model model, String user_email,
			String insertSubject, String insertSpecial) {
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String email = userVo.getUser_email();
		logger.debug("♬♩♪  insertSubject: {}", insertSubject);
		logger.debug("♬♩♪  insertSpecial: {}", insertSpecial);
		
		logger.debug("♬♩♪  insertConference + user_email: {}", user_email);
		//회의록 등록!!!
		MinutesVo minutesVo = new MinutesVo(prj_id, email, insertSubject, insertSpecial);
		int mCnt = minutesService.insertMinutes(minutesVo);
		if(mCnt==1) {
			logger.debug("♬♩♪  회의록 게시물 등록! ");
		}
		//가장 최근에 작성한 글 mnu_id 가져오기!
		MinutesVo minutesVo1 = minutesService.recentMinutes();
		int mnu_id = minutesVo1.getMnu_id();
		logger.debug("♬♩♪  mnu_id: {}", mnu_id);
		
		List<String> userEmailList = Arrays.asList(user_email.split(","));
		logger.debug("♬♩♪  userEmailList: {}", userEmailList);
		
		Minutes_MemVo memVo = new Minutes_MemVo();
		int cnt = 0;
		
		for (int i = 0; i < userEmailList.size(); i++) {
			memVo.setUser_email(userEmailList.get(i));
			memVo.setMnu_id(mnu_id);
			
			cnt = minutesService.insertAttender(memVo);
		}
		if(cnt==1) {
			logger.debug("♬♩♪  등록완료!!!!");
		}
		return "redirect:/conferenceList";
	}
	
	
}
