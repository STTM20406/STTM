package kr.or.ddit.minutes.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.minutes.model.MinutesVo;
import kr.or.ddit.minutes.service.IMinutesService;

@Controller
public class MinutesController {
	
	private static final Logger logger = LoggerFactory.getLogger(MinutesController.class);
	
	@Resource(name="minutesService")
	IMinutesService minutesService;
	
	@RequestMapping(path="/conferenceList", method = RequestMethod.GET)
	String conferenceList(Model model) {
		logger.debug("♬♩♪  jsp 가기전에 여기 들어옵니까??");
		int prj_id = 1;
		List<MinutesVo> minutesList = minutesService.minutesList(prj_id);
		
		model.addAttribute("minutesList", minutesList);
		
		return "/main/conference/conferenceList.user.tiles";
	}			
	
	
}
