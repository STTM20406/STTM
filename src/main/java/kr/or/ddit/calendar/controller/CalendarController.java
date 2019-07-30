package kr.or.ddit.calendar.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.calendar.service.ICalendarService;
import kr.or.ddit.work.model.WorkVo;


@Controller
public class CalendarController {

	private static final Logger logger = LoggerFactory.getLogger(CalendarController.class);
	@Resource(name="calendarService")
	private ICalendarService calendarService;
	
	@RequestMapping(path="/calendarGet" , method=RequestMethod.GET)
	String calendarGet(Model model) {
		logger.debug("♬♩♪   여기 calendarGet");
		
		model.addAttribute("workList", calendarService.workList());
		logger.debug("♬♩♪  calendarService.workList(): {}", calendarService.workList());
		return "/outline/calendar.user.tiles";
	}
	
	@RequestMapping(path="/calendarPost" , method=RequestMethod.POST)
	String calendarPost(Model model, String wrk_nm, String start_dt, String end_dt, 
		int wrk_lst_id, String wrk_color_cd) throws ParseException {
		
		logger.debug("♬♩♪ wrk_ nm: {}", wrk_nm);
		logger.debug("♬♩♪  wrk_start_dt: {}", start_dt);
		logger.debug("♬♩♪  wrk_end_dt: {}", end_dt);
		logger.debug("♬♩♪ wrk_ lst_id: {}", wrk_lst_id);
		logger.debug("♬♩♪  wrk_color_cd: {}", wrk_color_cd);
		
		//String 으로 받아와서 Date형식으로 바꿔줌!
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date wrk_start_dt= sdf.parse(start_dt);
		Date wrk_end_dt= sdf.parse(end_dt);
		
		String user_email = "chew@naver.com";
		
		WorkVo workVo = new WorkVo(wrk_lst_id, user_email, wrk_nm, wrk_color_cd, wrk_start_dt, wrk_end_dt);
		int insertWork = calendarService.wInsert(workVo);
		if(insertWork==1) {
			logger.debug("♬♩♪  업무 등록 완료!!");
		}
		model.addAttribute("IW", insertWork);
		model.addAttribute("workList", calendarService.workList());
		return "/outline/calendar.user.tiles";
	}
}
