package kr.or.ddit.calendar.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.calendar.dao.ICalendarDao;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

@Service
public class CalendarService implements ICalendarService{

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

	
}
