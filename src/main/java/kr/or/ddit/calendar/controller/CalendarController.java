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

import kr.or.ddit.calendar.service.ICalendarService;
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
		logger.debug("♬♩♪   여기 calendarGet");
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email =  userVo.getUser_email();
		
		model.addAttribute("mList", calendarService.allProjectMBList(user_email));
		logger.debug("♬♩♪  해당 프로젝트에 속해 있는 멤버 리스트 긔긔: {}", calendarService.allProjectMBList(user_email));
		
		//내가 속한 프로젝트 리스트
		model.addAttribute("projectList", calendarService.myProject(user_email));
		logger.debug("♬♩♪  projectList: {}", calendarService.myProject(user_email));
		
		int prj_id = 1;
		//프로젝트 리스트
		model.addAttribute("workList", calendarService.workList(prj_id));
		logger.debug("♬♩♪  calendarService.workList(): {}", calendarService.workList(prj_id));
		return "/outline/calendar.user.tiles";
	}
	
//	@RequestMapping(path="/calendarPrac" , method=RequestMethod.GET)
//	String calendarPrac(Model model) {
//		logger.debug("♬♩♪   여기 calendarGet");
//		
//		model.addAttribute("workList", calendarService.workList());
//		logger.debug("♬♩♪  calendarService.workList(): {}", calendarService.workList());
//		return "/outline/calendarPractice.user.tiles";
//	}
	
	/**
	 * Method 		: calendarPost
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-01 최초 생성
	 * @param model
	 * @param wrk_nm
	 * @param start_dt
	 * @param end_dt
	 * @param wrk_lst_id
	 * @param wrk_color_cd
	 * @return
	 * @throws ParseException
	 * Method 설명 	: 일정 등록 
	 */
	@RequestMapping(path="/calendarPost" , method=RequestMethod.POST)
	String calendarPost(Model model, String wrk_nm, String start_dt, String end_dt, 
		int wrk_lst_id, String wrk_color_cd, HttpSession session) throws ParseException {
		
		logger.debug("♬♩♪  여기는 post");
		logger.debug("♬♩♪ wrk_ nm: {}", wrk_nm);
		logger.debug("♬♩♪  wrk_start_dt: {}", start_dt);
		logger.debug("♬♩♪  wrk_end_dt: {}", end_dt);
		logger.debug("♬♩♪ wrk_ lst_id: {}", wrk_lst_id);
		logger.debug("♬♩♪  wrk_color_cd: {}", wrk_color_cd);
		
		//String 으로 받아와서 Date형식으로 바꿔줌!
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date wrk_start_dt= sdf.parse(start_dt);
		Date wrk_end_dt= sdf.parse(end_dt);
		
		logger.debug("♬♩♪  wrk_start_dt: {}", wrk_start_dt);
		logger.debug("♬♩♪  wrk_end_dt: {}", wrk_end_dt);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email =  userVo.getUser_email();
		
		WorkVo workVo = new WorkVo(wrk_lst_id, user_email, wrk_nm, wrk_color_cd, wrk_start_dt, wrk_end_dt);
		int insertWork = calendarService.wInsert(workVo);
		if(insertWork==1) {
			logger.debug("♬♩♪  업무 등록 완료!!");
		}
//		int prj_id = 1;
		
//		model.addAttribute("mList", calendarService.projectMBList(prj_id));
		model.addAttribute("IW", insertWork);
//		model.addAttribute("pList", calendarService.projectList());
//		model.addAttribute("workList", calendarService.workList());
		return "redirect:/calendarGet";
	}
	
//	// 특정 업무의 정보를 받아오는 controller (ajax)
//	@RequestMapping(path="/calendarAjax", method=RequestMethod.GET)
//	@ResponseBody
//	String calendarAjax(Model model) {
//		int wrk_id =1;
//		logger.debug("♬♩♪  workVo: {}", calendarService.wDetail(wrk_id));
//		model.addAttribute("data", calendarService.wDetail(wrk_id));
//		return "jsonView";
//	}
	
	//해당 프로젝트에 시작일과 종료 일이 설정되어있는 업무들을 받아와서 calendar에 뿌려주는!! (ajax)
	@RequestMapping(path="/wListAjax", method=RequestMethod.GET)
	String wListAjax(Model model, HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email =  userVo.getUser_email();
		
		logger.debug("♬♩♪  모든 프로젝트의 업무들을 가져오는 ajax");
		String data = calendarService.myProjectWork(user_email);
		logger.debug("♬♩♪  data: {}", data);
		model.addAttribute("response", data);
		
		return "jsonView";
	}
	
	//Drag and Drop 
	@RequestMapping(path="/dragAndDrop",method=RequestMethod.POST)
	String dragAndDrop(Model model, int wrk_id, long wrk_start_dt, long wrk_end_dt) throws ParseException {
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
	@RequestMapping(path="/addEvent" , method=RequestMethod.POST)
	String addEvent(Model model, @RequestBody Map<String, Object> map, HttpSession session) throws ParseException {
		logger.debug("♬♩♪  등록 컨트롤러: {}", map);
		
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
		return "redirect:/calendarGet";
	}
	
	@RequestMapping(path="/upW" , method=RequestMethod.POST)
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
			logger.debug("♬♩♪  업데이트 완료!!");
		}
		return "jsonView";
	}
	
	@RequestMapping(path="/changeWorkList" , method=RequestMethod.POST)
	String changeWorkList(Model model, int prj_id) {
		logger.debug("♬♩♪  프로젝트 선택시 해당 업무리스트 이름 뿌림!");
		
		List<Work_ListVo> workList = calendarService.workList(prj_id);
		
		logger.debug("♬♩♪  workList: {}", workList);
		
		model.addAttribute("data", workList);
			
		return "jsonView";
		
	}
	
	
}
