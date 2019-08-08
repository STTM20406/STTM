package kr.or.ddit.minutes.controller;

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
import kr.or.ddit.users.model.UserVo;

@Controller
public class MinutesController {
	
	private static final Logger logger = LoggerFactory.getLogger(MinutesController.class);
	
	@Resource(name="minutesService")
	IMinutesService minutesService;
	
	@RequestMapping(path="/conferenceList", method = RequestMethod.GET)
	String conferenceList(Model model, PageVo pageVo) {
		int prj_id = 1; //session에서 꺼내자
		
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
		
		return "/main/conference/conferenceList.user.tiles";
	}			
	
	@RequestMapping(path="/conferenceDetail", method = RequestMethod.GET)
	String conferenceDetail(Model model, int mnu_id, HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_nm = userVo.getUser_nm();
		model.addAttribute("user_nm", user_nm);
		logger.debug("♬♩♪  접속중인 사람 이름은!!! user_nm: {}", user_nm);
		
		
		logger.debug("♬♩♪  conferenceDetail 입니다~: {}", mnu_id);
		MinutesVo minutesVo = minutesService.minutesDetail(mnu_id);
		model.addAttribute("minutesVo", minutesVo);
		
		//참석자 리스트를 받아오는
		List<Minutes_MemVo> minutes_memList = minutesService.attenderList(mnu_id);
		model.addAttribute("minutes_memList", minutes_memList);
		logger.debug("♬♩♪  minutes_memList: {}", minutes_memList);
		
		return "/main/conference/conferenceDetail.user.tiles";
	}
	
	
	//수정해야합니다!!!!!!!!!!!!!!!!!!!!!!!!!!! 검색했을때 페이지 네이션
	@RequestMapping(path="/searchMinutes", method = RequestMethod.GET)
	String searchMinutes(PageVo pageVo, Model model,  String subject) {
		int prj_id = 1; //session에서 꺼내자
		
		//작성자로 검색했을때 Pagination처리
		if(subject.equals("writer")) {
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
		//참석자로 검색했을때 Pagination처리
		} else if(subject.equals("attender")) {
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
		}
		return "/main/conference/conferenceList.user.tiles";
	}
	
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
	
	@RequestMapping(path="/upMinutes", method = RequestMethod.GET)
	String upMinutes(Model model, int mnu_id, PageVo pageVo, HttpSession session) {
		logger.debug("♬♩♪  upMinutes메서드의  mnu_id: {}", mnu_id);
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_nm = userVo.getUser_nm();
		model.addAttribute("user_nm", user_nm);
		logger.debug("♬♩♪  접속중인 사람 이름은!!! user_nm: {}", user_nm);
		
		
		logger.debug("♬♩♪  conferenceDetail 입니다~: {}", mnu_id);
		MinutesVo minutesVo = minutesService.minutesDetail(mnu_id);
		model.addAttribute("minutesVo", minutesVo);
		
		//참석자 리스트를 받아오는
		List<Minutes_MemVo> minutes_memList = minutesService.attenderList(mnu_id);
		model.addAttribute("minutes_memList", minutes_memList);
		logger.debug("♬♩♪  minutes_memList: {}", minutes_memList);
		
		
		return "/main/conference/conferenceModify.user.tiles";
	}
	
	
}
