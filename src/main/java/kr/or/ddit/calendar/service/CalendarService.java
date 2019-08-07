package kr.or.ddit.calendar.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.calendar.dao.ICalendarDao;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

@Service
public class CalendarService implements ICalendarService {
	
	private static final Logger logger = LoggerFactory.getLogger(CalendarService.class);
	
	@Resource(name = "calendarDao")
	private ICalendarDao calendarDao;


	@Override
	public WorkVo wDetail(int wrk_id) {
		return calendarDao.wDetail(wrk_id);
	}

	@Override
	public int wInsert(WorkVo workVo) {
		return calendarDao.wInsert(workVo);
	}
	
	@Override
	   public String selectProjectWList(int prj_id) {
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	      List<WorkVo> list = calendarDao.selectProjectWList(prj_id);
	      String jsonData = "[";
	      for(WorkVo workVo : list) {
	    	  jsonData += "{\"_id\"" + ":"+ workVo.getWrk_id()  + ","
	                  + "\"title\"" + ":" + "\"" + workVo.getWrk_nm() + "\"" + "," 
	                  + "\"description\"" + ":" + "\"" + workVo.getPrj_nm() +"  ♬♪♩  "+ workVo.getWrk_lst_nm() + "\"" + "," 
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

	@Override
	public List<Project_MemVo> myProjectMBList(String user_email) {
		return calendarDao.myProjectMBList(user_email);
	}

	@Override
	public int dragAndDrop(WorkVo workVo) {
		return calendarDao.dragAndDrop(workVo);
	}

	@Override
	public int delW(int wrk_id) {
		return calendarDao.delW(wrk_id);
	}

	@Override
	public int upW(WorkVo workVo) {
		return calendarDao.upW(workVo);
	}
	
	@Override
	public String myProjectAllWorkList(String user_email) {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	      List<WorkVo> list = calendarDao.myProjectAllWorkList(user_email);
	      String jsonData = "[";
	      for(WorkVo workVo : list) {
	    	  jsonData += "{\"_id\"" + ":"+ workVo.getWrk_id()  + ","
	                  + "\"title\"" + ":" + "\"" + workVo.getWrk_nm() + "\"" + "," 
	                  + "\"description\"" + ":" + "\"" + workVo.getPrj_nm() +"  ♬♪♩  "+ workVo.getWrk_lst_nm() + "\"" + "," 
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
	
	/**
	 * Method : myProject 작성자 : 손영하 변경이력 : 2019-08-05 최초 생성
	 * 
	 * @param user_email
	 * @return Method 설명 : 내가 속한 프로젝트 리스트를 받아오는 메서드
	 */
	@Override
	public List<ProjectVo> myProject(String user_email) {
		return calendarDao.myProject(user_email);
	}
	
	/**
	 * Method 		: myProjectWork
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-06 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 내가 속한 프로젝트 업무들을 받아오는 메서드
	 */
	@Override
	public String myProjectWList(String user_email) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	      List<WorkVo> list = calendarDao.myProjectWList(user_email);
	      String jsonData = "[";
	      for(WorkVo workVo : list) {
	    	  jsonData += "{\"_id\"" + ":"+ workVo.getWrk_id()  + ","
	                  + "\"title\"" + ":" + "\"" + workVo.getWrk_nm() + "\"" + "," 
	                  + "\"description\"" + ":" + "\"" + workVo.getPrj_nm() +"  ♬♪♩  "+ workVo.getWrk_lst_nm() + "\"" + "," 
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
	
	/**
	* Method : workList
	* 작성자 : melong2
	* 변경이력 :
	* @param prj_id
	* @return
	* Method 설명 : 특정 프로젝트에 대한 업무들을 받아오는 
	*/
	@Override
	public List<Work_ListVo> workList(int prj_id) {
		return calendarDao.workList(prj_id);
	}

	
}
