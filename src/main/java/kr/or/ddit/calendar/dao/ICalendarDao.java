package kr.or.ddit.calendar.dao;

import java.util.List;

import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

public interface ICalendarDao {

	/**
	 * Method : wList 작성자 : 손영하 변경이력 : 2019-08-05 최초 생성
	 * 
	 * @param prj_id
	 * @return Method 설명 : 특정 프로젝트 내에 있는 업무들 체크박스로 선택!
	 */
	List<WorkVo> selectProjectWList(int prj_id);
	
	// 해당 엄무의 정보를 가져오는 메서드
	WorkVo wDetail(int wrk_id);

	// 업무 생성하는 메서드
	int wInsert(WorkVo workVo);

	// drag and drop 후 날짜에 맞게 DB에 update시키기!
	int dragAndDrop(WorkVo workVo);

	// 해당 업무 삭제 하는 메서드
	int delW(int wrk_id);

	// 해당 업무 업데이트 하는 메서드
	int upW(WorkVo workVo);

	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Method 		: projectWList
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-07 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 내가 속한 프로젝트 전체업무 들에 대한 정보
	 */
	List<WorkVo> myProjectAllWorkList(String user_email);
	/**
	 * Method : allProjectMBList 작성자 : 손영하 변경이력 : 2019-08-05 최초 생성
	 * 
	 * @return Method 설명 : 내가 속해있는 프로젝트 멤버 리스트를 받아오는 메서드
	 */
	List<Project_MemVo> myProjectMBList(String user_email);
	
	/**
	 * Method : myProject 작성자 : 손영하 변경이력 : 2019-08-05 최초 생성
	 * 
	 * @param user_email
	 * @return Method 설명 : 내가 속한 프로젝트 리스트를 받아오는 메서드
	 */
	List<ProjectVo> myProject(String user_email);
	
	/**
	 * Method 		: myProjectWork
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-06 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 내가 속한 프로젝트 내 업무들을 받아오는 메서드
	 */
	List<WorkVo> myProjectWList(String user_email);
	
	/**
	* Method : workList
	* 작성자 : melong2
	* 변경이력 :
	* @param prj_id
	* @return
	* Method 설명 : 일정 등록할떄 사용!해당 프로젝트에 업무 리스트를 받아오는
	*/
	// 해당 프로젝트 업무 리스트 받아오는 메서드
	List<Work_ListVo> workList(int prj_id);

}
