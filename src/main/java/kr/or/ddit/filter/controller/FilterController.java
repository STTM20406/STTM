package kr.or.ddit.filter.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.filter.model.FilterVo;
import kr.or.ddit.filter.service.IFilterService;

@RequestMapping("/filter")
@Controller
public class FilterController {
	private static final Logger logger = LoggerFactory.getLogger(FilterController.class);
	
	@Resource(name="filterService")
	IFilterService filterService;
	
	@RequestMapping("/view")
	public String filterView() {
		return "/outline/work.user.tiles";
	}
	
	@RequestMapping("/ajax")
	public String filterAjax(Model model, FilterVo filterVo) {
		logger.debug("filterVo : {}", filterVo);
		Map<String, Object> resultMap = filterService.workListJSON(filterVo); 
		model.addAttribute("resultMap", resultMap);
		return "jsonView";
	}
	
	@RequestMapping("/detail")
	public String workDetailAjax(Model model, int wrk_id) {
		logger.debug("wrk_id : {}", wrk_id);
		model.addAttribute("workDetail", filterService.workDetail(wrk_id));
		return "jsonView";
	}
	
	@RequestMapping("/prjgantt")
	@ResponseBody
	public Map<String, Object> overviewGanttChartData(Model model, FilterVo filterVo) {
		return filterService.ganttListJSON(filterVo);
	}
}
