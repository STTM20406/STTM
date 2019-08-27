package kr.or.ddit.meeting.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.meeting.model.MeetingVo;
import kr.or.ddit.meeting.service.IMeetingService;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.users.model.UserVo;
@RequestMapping("/meeting")
@Controller
public class MeetingController {
	private static final Logger logger = LoggerFactory.getLogger(MeetingController.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	
	@Resource(name="meetingService")
	IMeetingService meetingService;
	
	@RequestMapping("/view")
	public String meetingView() {
		return "/main/meeting.user.tiles";
		
	}
	
	@RequestMapping("/ajax")
	public String meetingAjax(Model model) {
		model.addAttribute("data", meetingService.meetingList());
		return "jsonView";
	}
	
	@RequestMapping("/insert")
	public String insertMeeting(Model model, String date, MeetingVo meetingVo, HttpSession session) throws ParseException {
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		
		meetingVo.setUser_email(userVo.getUser_email());
		meetingVo.setPrj_id(projectVo.getPrj_id());
		meetingVo.setMt_date(sdf.parse(date));
		
		int insertResult = meetingService.insertMeeting(meetingVo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(insertResult==1) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "failure");
		}
		
		model.addAttribute("result", resultMap);
		return "jsonView";
	}
}
