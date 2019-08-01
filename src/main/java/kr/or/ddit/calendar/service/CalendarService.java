package kr.or.ddit.calendar.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.calendar.dao.ICalendarDao;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

@Service
public class CalendarService implements ICalendarService {
	
	private static final Logger logger = LoggerFactory.getLogger(CalendarService.class);
	
	@Resource(name = "calendarDao")
	private ICalendarDao calendarDao;

	@Override
	public List<Work_ListVo> workList() {
		return calendarDao.workList();
	}

	@Override
	public WorkVo wDetail(int wrk_id) {
		return calendarDao.wDetail(wrk_id);
	}

	@Override
	public int wInsert(WorkVo workVo) {
		return calendarDao.wInsert(workVo);
	}

//	@Override
//	public List<WorkVo> wList(int prj_id) {
//		return calendarDao.wList(prj_id);
//	}

	@Override
	   public String wList(int prj_id) {
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm");
	      List<WorkVo> list = calendarDao.wList(prj_id);
	      String jsonData = "[";
	      for(WorkVo workVo : list) {
	    	  jsonData += "{\"_id\"" + ":"+ workVo.getWrk_id()  + ","
	                  + "\"title\"" + ":" + "\"" + workVo.getWrk_nm() + "\"" + "," 
	                  + "\"description\"" + ":" + "\"" + "헿" + "\"" + "," 
	                  + "\"start\"" + ":" + "\"" + sdf.format(workVo.getWrk_start_dt()) + "\"" + ","
	                  + "\"end\"" + ":" + "\"" + sdf.format(workVo.getWrk_end_dt()) + "\"" + ","
	                  + "\"username\"" + ":" + "\"" + workVo.getUser_nm()+ "\"" + ","
	                  + "\"type\"" + ":" + "\"" + workVo.getWrk_lst_nm() + "\"" + ","
	                  + "\"textColor\"" + ":" + "\"" + "#ffffff" + "\"" + ","
	                  + "\"backgroundColor\"" + ":" + "\"" + workVo.getWrk_color_cd() + "\"" + ","
	                 + "\"allDay\"" + ":" + "false" + "},";
	      }
	      jsonData = jsonData.substring(0, jsonData.lastIndexOf(","));
	      jsonData += "]";
	      
	      logger.debug("jsonData : {}", jsonData);
	      
	      return jsonData;
	   }
	
	
}
