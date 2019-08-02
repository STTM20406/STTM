package kr.or.ddit.ganttChart.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.filter.service.IFilterService;
import kr.or.ddit.ganttChart.model.GanttChartVo;
import kr.or.ddit.ganttChart.service.IGanttChartService;

/**
 * GanttChartController.java
 *
 * @author 유승진
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * 유승진 2019-07-31 최초 생성
 *
 * </pre>
 */
@RequestMapping("/gantt")
@Controller
public class GanttChartController {
	private static final Logger logger = LoggerFactory.getLogger(GanttChartController.class);
	
	@Resource(name="ganttChartService")
	IGanttChartService ganttService;
	
	@Resource(name="filterService")
	IFilterService filterService;
	
	@RequestMapping("/overview")
	public String overviewGanttChartView() {
		return "/main/gantChart/gantChart.user.tiles";
	}
	
	@RequestMapping("/project")
	public String projectGanttChartView() {
		return "/outline/gantChart.user.tiles";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public void updateGantt(GanttChartVo ganttVo) {
		logger.debug("ganttVo : {}",ganttVo);
		
		int updateCnt = ganttService.update(ganttVo);
		logger.debug("updateCnt : {}",updateCnt);
	}
}
