package kr.or.ddit.ganttChart.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.filter.service.IFilterService;
import kr.or.ddit.ganttChart.dao.IGanttChartDao;
import kr.or.ddit.ganttChart.model.GanttChartVo;
import kr.or.ddit.work.model.WorkVo;

/**
 * GanttChartService.java
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
@Service
public class GanttChartService implements IGanttChartService{
	private static final Logger logger = LoggerFactory.getLogger(GanttChartService.class);
	
	@Resource(name="filterService")
	IFilterService filterService;
	
	@Resource(name="ganttChartDao")
	IGanttChartDao ganttDao;
	
	@Override
	public int update(GanttChartVo ganttVo) {
		String mode = ganttVo.getMode();
		int updateCnt = 0;
			try {
				if("resize".equals(mode))
				updateCnt = updateResize(ganttVo);
				else if("move".equals(mode))
					updateCnt = updateMove(ganttVo);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return updateCnt;
	}

	/**
	 * Method : updateResize
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-31 최초 생성
	 * @param ganttVo
	 * Method 설명 : 간트차트 데이터를 이동시켰을 때(기간만 변경) 업데이트 로직을 수행하는 메서드
	 * @throws ParseException 
	 */
	private int updateResize(GanttChartVo ganttVo) throws ParseException {
		int wrk_id = Integer.parseInt(ganttVo.getId().split("#")[1]);
		WorkVo workVo = filterService.getWork(wrk_id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start_dt = sdf.parse(ganttVo.getStart_date());
		
		Date wrk_start_dt = workVo.getWrk_start_dt();
		
		long calDate = wrk_start_dt.getTime() - start_dt.getTime();
		long calDateDays = calDate / (1000*60*60*24); 
		int dateGap = (int)calDateDays;
		
		Date wrk_end_dt = workVo.getWrk_end_dt();
		
		return 0;
	}
	
	/**
	 * Method : updateMove
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-31 최초 생성
	 * @param ganttVo
	 * Method 설명 : 간트차트 데이터를 이동시켰을 때(일시만 변경) 업데이트 로직을 수행하는 메서드
	 * @throws ParseException 
	 */
	private int updateMove(GanttChartVo ganttVo) throws ParseException {
		int wrk_id = Integer.parseInt(ganttVo.getId().split("#")[1]);
		WorkVo workVo = filterService.getWork(wrk_id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		
		Date start_dt = sdf.parse(ganttVo.getStart_date());
		
		Date wrk_start_dt = workVo.getWrk_start_dt();
		
		long calDate = start_dt.getTime() - wrk_start_dt.getTime();
		long calDateDays = calDate / (1000*60*60*24); 
		int dateGap = (int)calDateDays;
		
		logger.debug("dateGap(updateMove) : {}일 차이", dateGap);
		
		logger.debug("시간 변경 전 workVo : {}", workVo);
		
		cal.setTime(wrk_start_dt);
		cal.add(Calendar.DAY_OF_MONTH, dateGap);
		wrk_start_dt = cal.getTime();
		workVo.setWrk_start_dt(cal.getTime());
		
		Date wrk_end_dt = workVo.getWrk_end_dt();
		cal.setTime(wrk_end_dt);
		cal.add(Calendar.DAY_OF_MONTH, dateGap);
		workVo.setWrk_end_dt(cal.getTime());
		
		logger.debug("시간 변경 후 workVo : {}", workVo);
		return 0;
	}

}
