package kr.or.ddit.ganttChart.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.work.model.WorkVo;

@Controller
public class GanttChartController {
	private static final Logger logger = LoggerFactory.getLogger(GanttChartController.class);
	
	@RequestMapping("/project/gantt")
	public String projectGanttChartView() {
		return "/main/gantChart/gantChart.user.tiles";
	}
	
	@RequestMapping("/project/gantt/updateGantt")
	public String updateGantt(Date wrk_end_dt, Date wrk_start_dt, String wrk_id, Model model) {
		WorkVo workVo = new WorkVo();
		workVo.setWrk_start_dt(wrk_start_dt);
		workVo.setWrk_end_dt(wrk_end_dt);
		workVo.setWrk_id(Integer.parseInt(wrk_id.split("#")[1]));
		
		logger.debug("workVo : {}", workVo);
		return "jsonView";
	}
}
