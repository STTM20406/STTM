package kr.or.ddit.calendar.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.calendar.service.ICalendarService;
import kr.or.ddit.filter.model.FilterVo;
import kr.or.ddit.filter.service.IFilterService;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

/**
 * CalendarController.java
 *
 * @author 손영하
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 박서경   최초 생성 2019-08-02
 *
 * </pre>
 */
@Controller
public class CalendarController {
	
	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
	@Resource(name="calendarService")
	private ICalendarService calendarService;
	
	@Resource(name="filterService")
	IFilterService filterService;
	
	/**
	 * Method 		: calendarGet
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-01 최초 생성
	 * @param model
	 * @return
	 * Method 설명 	: 캘린더 소환!
	 */
	@RequestMapping(path="/calendarGet" , method=RequestMethod.GET)
	String calendarGet(Model model, HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email =  userVo.getUser_email();
		model.addAttribute("user_email", user_email);
		logger.debug("♬♩♪  user_email: {}", user_email);
		return "/outline/calendar.user.tiles";
	}	
	
	
	/**
	 * Method 		: wListAjax
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-07 최초 생성
	 * @param model
	 * @param session
	 * @return
	 * Method 설명 	:해당 프로젝트에 시작일과 종료 일이 설정되어있는 업무들을 받아와서 calendar에 뿌려주는!! (ajax)
	 */
	@RequestMapping(path="/wListAjax", method=RequestMethod.GET)
	String wListAjax(Model model, HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email =  userVo.getUser_email();
		logger.debug("♬♩♪  모든 프로젝트의 업무들을 가져오는 ajax");
		String data = calendarService.myProjectAllWorkList(user_email);
		logger.debug("♬♩♪  data: {}", data);
		model.addAttribute("response", data);
		return "jsonView";
	}
	
	@RequestMapping(path="/allNMine", method=RequestMethod.POST)
	String allNMine(Model model,String value, HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email =  userVo.getUser_email();
		
		if(value.equals("all")) {
			String data = calendarService.myProjectAllWorkList(user_email);
			model.addAttribute("response", data);
			logger.debug("♬♩♪  all + data: {}", data);
		}else if(value.equals("mine")) {
			String data = calendarService.myProjectWList(user_email);
			model.addAttribute("response", data);
			logger.debug("♬♩♪  mine + data: {}", data);
		}
		return "jsonView";
	}
	
	
	//Drag and Drop 
	@RequestMapping("/dragAndDrop")
	String dragAndDrop(Model model, Integer wrk_id, Long wrk_start_dt, Long wrk_end_dt) throws ParseException {
		logger.debug("♬♩♪  dragAndDrop: {}", wrk_id);
		
		logger.debug("♬♩♪  start: {}", wrk_start_dt);
		logger.debug("♬♩♪  end: {}", wrk_end_dt);
		
		String pattern = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String date1 = sdf.format(new Timestamp(wrk_start_dt));
		String date2 = sdf.format(new Timestamp(wrk_end_dt));
		
		Date start_dt = sdf.parse(date1);
		Date end_dt = sdf.parse(date2);
		logger.debug("♬♩♪  date1: {}", date1);
		logger.debug("♬♩♪  date2: {}", date2);
		
		WorkVo workVo = new WorkVo(wrk_id, start_dt, end_dt);
		int update = calendarService.dragAndDrop(workVo);
		
		if(update==1) {
			logger.debug("♬♩♪  update 완료!");
		}
		return "jsonView";
	}
	
	/**
	 * Method 		: addEvent
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-02 최초 생성
	 * @param model
	 * @param calendarVo
	 * @return
	 * Method 설명 	: ajax로 등록 처리 하려했지만 잘안되서 후퇴..............!!
	 * @throws ParseException 
	 */
	@RequestMapping("/addEvent" )
	String addEvent(Model model, @RequestBody Map<String, Object> map, HttpSession session) throws ParseException {
		String wrk = (String) map.get("type");
		int wrk_lst_id = Integer.parseInt(wrk);
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		
		String user_email =  userVo.getUser_email();
		String wrk_nm = (String) map.get("title");
		String wrk_color_cd = (String) map.get("backgroundColor");
		String start = (String) map.get("start");
		String end = (String) map.get("end");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date wrk_start_dt= sdf.parse(start);
		Date wrk_end_dt= sdf.parse(end);
		
		WorkVo workVo = new WorkVo(wrk_lst_id, user_email, wrk_nm,wrk_color_cd, wrk_start_dt, wrk_end_dt);
		int cnt = calendarService.wInsert(workVo);
		
		if(cnt ==1){
			logger.debug("♬♩♪  잘 들어가유!!!!!!!!!!!!!!!!!!!");
		}
		
		return "jsonView";
	}
	
	@RequestMapping(path="/delW" , method=RequestMethod.POST)
	String delW(String wrk_id) {
		logger.debug("♬♩♪  여기는 !!! 해당 업무를 삭제하는 controller입니다.!!");
		int w_id = Integer.parseInt(wrk_id);
		logger.debug("♬♩♪  w_id: {}", w_id);
		int del = calendarService.delW(w_id);
		
		if(del==1) {
			logger.debug("♬♩♪  삭제완료!");
		}
		return "jsonView";
	}
	
	@RequestMapping("/upW")
	String upW(Model model, String wrk_id, String wrk_nm, String wrk_start_dt,
			String wrk_end_dt, String wrk_lst_id, String wrk_color_cd) throws ParseException  {
		
		logger.debug("♬♩♪  해당 프로젝트 업데이트 하는 곳입니다.");
		logger.debug("♬♩♪  wrk_id: {}", wrk_id);
		logger.debug("♬♩♪  wrk_nm: {}", wrk_nm);
		logger.debug("♬♩♪  wrk_start_dt: {}", wrk_start_dt);
		logger.debug("♬♩♪  wrk_end_dt: {}", wrk_end_dt);
		logger.debug("♬♩♪  wrk_lst_id: {}", wrk_lst_id);
		logger.debug("♬♩♪  wrk_color_cd: {}", wrk_color_cd);
	
		String pattern = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		Date start_dt = sdf.parse(wrk_start_dt);
		Date end_dt = sdf.parse(wrk_end_dt);
		WorkVo workVo = new WorkVo(Integer.parseInt(wrk_lst_id), wrk_nm, wrk_color_cd, start_dt, end_dt,Integer.parseInt(wrk_id));
		
		int update = calendarService.upW(workVo);
		
		if(update ==1) {
			logger.debug("♬♩♪  업데이트 완료!");
		}
		return "jsonView";
	}
	
	
	/**
	 * Method 		: changeWorkList
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-07 최초 생성
	 * @param model
	 * @param prj_id
	 * @return
	 * Method 설명 	: 프로젝트 선택시 해당 업무리스트 이름 뿌림!
	 */
	@RequestMapping(path="/changeWorkList" , method=RequestMethod.POST)
	String changeWorkList(Model model, int prj_id) {
		logger.debug("♬♩♪  프로젝트 선택시 해당 업무리스트 이름 뿌림!");
		
		List<Work_ListVo> workList = calendarService.workList(prj_id);
		
		logger.debug("♬♩♪  workList: {}", workList);
		
		model.addAttribute("data", workList);
			
		return "jsonView";
		
	}
	
	@RequestMapping("/calendarTest")
	@ResponseBody
	public Map<String, Object> calendarTestJSON(FilterVo filterVo, Model model, HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email =  userVo.getUser_email();
		
		model.addAttribute("user_email", user_email);
		logger.debug("♬♩♪  calendarTest: {}", user_email);
		return filterService.calendarTemplateJSON(filterVo); 
	}
	
	/**
	 * Method 		: searchWorkInfomation
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-23 최초 생성
	 * @param model
	 * @param wrk_id
	 * @return
	 * Method 설명 	: calendar에서 해당 업무 눌렀을 때 어떤 프로젝트의 어떤 업무리스트인지 조회!
	 */
	@RequestMapping("/searchWorkInfomation")
	String searchWorkInfomation(Model model, int wrk_id) {
		model.addAttribute("CalendarVo", calendarService.searchWorkInfomation(wrk_id));
		
		return "jsonView";
	}
	
	@RequestMapping("/search_userEmail")
	String search_userEmail(Model model, int wrk_id) {
		model.addAttribute("user_email", calendarService.search_userEmail(wrk_id));
		logger.debug("♬♩♪  search_userEmail user_email: {}", calendarService.search_userEmail(wrk_id));
		return "jsonView";
	}
	
}
