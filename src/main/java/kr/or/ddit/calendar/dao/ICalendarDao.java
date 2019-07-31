package kr.or.ddit.calendar.dao;

import java.util.List;

import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

public interface ICalendarDao {

	// 업무 리스트 받아오는 메서드
	List<Work_ListVo> workList();

	// 해당 엄무의 정보를 가져오는 메서드
	WorkVo wDetail(int wrk_id);

	// 업무 생성하는 메서드
	int wInsert(WorkVo workVo);

}
