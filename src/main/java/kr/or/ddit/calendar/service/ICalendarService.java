package kr.or.ddit.calendar.service;

import java.util.List;

import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

public interface ICalendarService {

	// 업무 리스트 받아오는 메서드
	List<Work_ListVo> workList();

	// 해당 엄무의 정보를 가져오는 메서드
	WorkVo wDetail(int wrk_id);

	// 업무 생성하는 메서드
	int wInsert(WorkVo workVo);

	// 시작일과 종료 일이 설정되어있는 업무들을 받아와서 calendar에 뿌려주는!!
	String wList(int prj_id);

	// 해당 프로젝트에 멤버들의 정보를 가져오는!
	List<Project_MemVo> projectMBList(int prj_id);

	// drag and drop 후 날짜에 맞게 DB에 update시키기!
	int dragAndDrop(WorkVo workVo);

	// 해당 업무 삭제 하는 메서드
	int delW(int wrk_id);

	// 해당 업무 업데이트 하는 메서드
	int upW(WorkVo workVo);

	// 프로젝트 리스트 받아오는!!
	List<ProjectVo> projectList();

}
