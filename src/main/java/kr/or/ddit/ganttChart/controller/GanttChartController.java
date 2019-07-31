package kr.or.ddit.ganttChart.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.filter.service.IFilterService;
import kr.or.ddit.work.model.WorkVo;

@RequestMapping("/gantt")
@Controller
public class GanttChartController {
	private static final Logger logger = LoggerFactory.getLogger(GanttChartController.class);
	
	@Resource(name="filterService")
	IFilterService filterService;
	
	@RequestMapping("/project")
	public String projectGanttChartView() {
		return "/main/gantChart/gantChart.user.tiles";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public void updateGantt(Date wrk_end_dt, Date wrk_start_dt, String wrk_id, Model model) {
		logger.debug("wrk_id : {}, wrk_start_dt : {}, wrk_end_dt : {}", wrk_id, wrk_start_dt, wrk_end_dt);
		logger.debug("{}, {}", wrk_id.split("#")[0], wrk_id.split("#")[1]);
		String wrk_id_str = wrk_id.split("#")[1];
		int work_id = Integer.parseInt(wrk_id_str);
		WorkVo workVo = filterService.getWork(work_id);
		logger.debug("workVo : {}", workVo);
		
		Date workVo_start_dt = workVo.getWrk_start_dt();
		long calDate = wrk_start_dt.getTime() - workVo_start_dt.getTime();
		
		long calDateDays = calDate / (1000*60*60*24);
		
		logger.debug("며칠 차이? : {}일", calDateDays);
		
		logger.debug("변경 전 workVo : start : {}, end : {}",workVo.getWrk_start_dt(), workVo.getWrk_end_dt());
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(workVo.getWrk_end_dt());
		logger.debug("변경 전 getTime() : {}", cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, (int)calDateDays);
		logger.debug("변경 후 getTime() : {}", cal.getTime());
		workVo.setWrk_end_dt(cal.getTime());
		cal.setTime(workVo.getWrk_start_dt());
		cal.add(Calendar.DAY_OF_MONTH, (int)calDateDays);
		workVo.setWrk_start_dt(cal.getTime());
		
		logger.debug("변경 후 workVo : start : {}, end : {}",workVo.getWrk_start_dt(), workVo.getWrk_end_dt());
	}
}
