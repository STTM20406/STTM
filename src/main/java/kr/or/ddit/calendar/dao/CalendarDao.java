package kr.or.ddit.calendar.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.calendar.model.CalendarVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

@Repository
public class CalendarDao implements ICalendarDao {

	@Resource(name = "sqlSession")
	private SqlSessionTemplate sqlSession;

	@Override
	public WorkVo wDetail(int wrk_id) {
		return sqlSession.selectOne("calendar.wDetail", wrk_id);
	}

	@Override
	public int wInsert(WorkVo workVo) {
		return sqlSession.insert("calendar.wInsert", workVo);
	}

	/**
	* Method : selectProjectWList
	* 작성자 : melong2
	* 변경이력 :
	* @param prj_id
	* @return
	* Method 설명 : 특정 프로젝트에 업무에 대한걸 다 받아오는 메서드
	*/
	@Override
	public List<WorkVo> selectProjectWList(int prj_id) {
		return sqlSession.selectList("calendar.selectProjectWList",prj_id);
	}

	/**
	* Method : projectMBList
	* 작성자 : melong2
	* 변경이력 :
	* @param user_email
	* @return
	* Method 설명 : 로그인 한 사용자가 속해 있는 프로젝트 모든 멤버 목록
	*/
	@Override
	public List<Project_MemVo> myProjectMBList(String user_email) {
		return sqlSession.selectList("calendar.myProjectMBList",user_email);
	}

	@Override
	public int dragAndDrop(WorkVo workVo) {
		return sqlSession.update("calendar.dragAndDrop",workVo);
	}

	@Override
	public int delW(int wrk_id) {
		return sqlSession.update("calendar.delW",wrk_id);
	}

	@Override
	public int upW(WorkVo workVo) {
		return sqlSession.update("calendar.upW",workVo);
	}
	
	/**
	* Method : projectWList
	* 작성자 : melong2
	* 변경이력 :
	* @param user_email
	* @return
	* Method 설명 : 로그인 한 사용자가 속해 있는 프로젝트와 내 업무에 대한 정보를 다 가져오는
	*/
	@Override
	public List<WorkVo> myProjectWList(String user_email) {
		return sqlSession.selectList("calendar.myProjectWList",user_email);
	}
	
	/**
	 * Method : myProject 작성자 : 손영하 변경이력 : 2019-08-05 최초 생성
	 * 
	 * @param user_email
	 * @return Method 설명 : 내가 속한 프로젝트 리스트를 받아오는 메서드
	 */
	@Override
	public List<ProjectVo> myProject(String user_email) {
		return sqlSession.selectList("calendar.myProject",user_email);
	}
	
	/**
	 * Method 		: myProjectWork
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-06 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 내가 속한 프로젝트 전체 업무들을 받아오는 메서드
	 */
	@Override
	public List<WorkVo> myProjectAllWorkList(String user_email) {
		return sqlSession.selectList("calendar.myProjectAllWorkList",user_email);
	}
	
	/**
	* Method : workList
	* 작성자 : melong2
	* 변경이력 :
	* @param prj_id
	* @return
	* Method 설명 : 해당 프로젝트에 업무 리스트를 받아오는
	*/
	@Override
	public List<Work_ListVo> workList(int prj_id) {
		return sqlSession.selectList("calendar.workList",prj_id);
	}

	@Override
	public CalendarVo searchWorkInfomation(int wrk_id) {
		return sqlSession.selectOne("calendar.searchWorkInfomation",wrk_id);
	}

	@Override
	public WorkVo search_userEmail(int wrk_id) {
		return sqlSession.selectOne("calendar.search_userEmail",wrk_id);
	}

}
