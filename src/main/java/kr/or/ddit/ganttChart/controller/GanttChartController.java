package kr.or.ddit.ganttChart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GanttChartController {
	private static final Logger logger = LoggerFactory.getLogger(GanttChartController.class);
	
	@RequestMapping("/gantt/project/view")
	public String projectGanttChartView() {
		return "/main/gantChart/gantChart.user.tiles";
	}
	
}
