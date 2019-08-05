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
import kr.or.ddit.work.dao.IWorkDao;
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
	
	@Resource(name="workDao")
	IWorkDao workDao;
	
	@Resource(name="ganttChartDao")
	IGanttChartDao ganttDao;
	
	@Override
	public int update(GanttChartVo ganttVo) {
		int updateCnt = 0;
				updateCnt = updateGantt(ganttVo);
		return updateCnt;
	}

	/**
	 * Method : updateGantt
	 * 작성자 : 유승진
	 * 변경이력 : 2019-07-31 최초 생성
	 * @param ganttVo
	 * Method 설명 : 간트차트 데이터를 이동시켰을 때 업데이트 로직을 수행하는 메서드
	 * @throws ParseException 
	 */
	private int updateGantt(GanttChartVo ganttVo) {
		int wrk_id = Integer.parseInt(ganttVo.getId().split("#")[1]); // wrk#1
		WorkVo workVo = filterService.getWork(wrk_id);
		logger.debug("update대상 workVo : {}", workVo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		
		Date start_dt = null;
		Date end_dt = null;
		try {
			start_dt = sdf.parse(ganttVo.getStart_date()); // 2019-07-31 -> Date 형태
			end_dt = sdf.parse(ganttVo.getEnd_date()); 	// 2019-08-02 -> Date 형태
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		logger.debug("변경 전 시작 일시 : {}, 마감 일시 : {}",workVo.getWrk_start_dt(), workVo.getWrk_end_dt());
		
		cal.setTime(start_dt); // 2019년 7월 31일 0시 0분 0초
		int day_start = cal.get(Calendar.DAY_OF_MONTH); //일 31
		int month_start = cal.get(Calendar.MONTH); // 월 7
		int year_start = cal.get(Calendar.YEAR); // 년 2019
		
		Date wrk_start_dt = workVo.getWrk_start_dt(); //2019년 7월 30일 10시 32분 ...
		cal.setTime(wrk_start_dt);
		cal.set(Calendar.DAY_OF_MONTH, day_start);	// 날짜의 일을 30에서 31로 변경
		cal.set(Calendar.MONTH, month_start);	// 날짜의 일을 7에서 7로 변경
		cal.set(Calendar.YEAR, year_start);		// 날짜의 년을 2019에서 2019로 변경
		workVo.setWrk_start_dt(cal.getTime()); 
		
		cal.setTime(end_dt);
		Date wrk_end_dt = workVo.getWrk_end_dt(); // 2019년 8월 2일 0시 0분 0초
		int day_end = cal.get(Calendar.DAY_OF_MONTH) - 1;	//  일 2 - 1 = 1
		int month_end = cal.get(Calendar.MONTH);	// 월 8
		int year_end = cal.get(Calendar.YEAR);	// 년 2019
		
		cal.setTime(wrk_end_dt);
		cal.set(Calendar.DAY_OF_MONTH, day_end); // 일 3에서 1로
		cal.set(Calendar.MONTH, month_end);	// 월 8에서 8로
		cal.set(Calendar.YEAR, year_end); // 년 2019에서 2019로
		workVo.setWrk_end_dt(cal.getTime()); // 
		
		logger.debug("변경 후 시작 일시 : {}, 마감 일시 : {}",workVo.getWrk_start_dt(), workVo.getWrk_end_dt());
		int updateCnt = workDao.updateWork(workVo);
		return updateCnt;  
	}

}
