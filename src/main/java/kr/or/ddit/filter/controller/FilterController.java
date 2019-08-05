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

@Controller
public class FilterController {
	private static final Logger logger = LoggerFactory.getLogger(FilterController.class);
	
	@Resource(name="filterService")
	IFilterService filterService;
	
	@RequestMapping("/overview/analysis")
	public String filterView() {
		return "/outline/work.user.tiles";
	}
	
	@RequestMapping("/project/overview")
	public String projectOverview() {
		return "/main/analysis/analysis.user.tiles";
	}
	@RequestMapping("/project/overview/ajax")
	public String projectOverviewAjax(Model model, FilterVo filterVo) {
		model.addAttribute("result", filterService.projectOverviewJSON(filterVo));
		return "jsonView";
	}
	
	@RequestMapping("/filter/ajax")
	public String filterAjax(Model model, FilterVo filterVo) {
		logger.debug("filterVo : {}", filterVo);
		Map<String, Object> resultMap = filterService.workListJSON(filterVo); 
		model.addAttribute("resultMap", resultMap);
		return "jsonView";
	}
	
	@RequestMapping("/filter/detail")
	public String workDetailAjax(Model model, int wrk_id) {
		logger.debug("wrk_id : {}", wrk_id);
		model.addAttribute("workDetail", filterService.workDetail(wrk_id));
		return "jsonView";
	}
	
	@RequestMapping("/filter/overgantt")
	@ResponseBody
	public Map<String, Object> overviewGanttChartData(FilterVo filterVo) {
		return filterService.ganttListJSON(filterVo);
	}
	
	@RequestMapping("/filter/prjgantt")
	@ResponseBody
	public Map<String, Object> projectGanttChartData(FilterVo filterVo) {
		return filterService.ganttListJSON(filterVo);
	}
}
