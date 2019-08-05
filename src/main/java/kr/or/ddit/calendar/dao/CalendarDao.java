package kr.or.ddit.calendar.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

@Repository
public class CalendarDao implements ICalendarDao {

	@Resource(name = "sqlSession")
	private SqlSessionTemplate sqlSession;

	@Override
	public List<Work_ListVo> workList() {
		return sqlSession.selectList("calendar.workList");
	}

	@Override
	public WorkVo wDetail(int wrk_id) {
		return sqlSession.selectOne("calendar.wDetail", wrk_id);
	}

	@Override
	public int wInsert(WorkVo workVo) {
		return sqlSession.insert("calendar.wInsert", workVo);
	}

	@Override
	public List<WorkVo> wList(int prj_id) {
		return sqlSession.selectList("calendar.wList",prj_id);
	}

	@Override
	public List<Project_MemVo> projectMBList(int prj_id) {
		return sqlSession.selectList("calendar.projectMBList",prj_id);
	}

	@Override
	public int dragAndDrop(WorkVo workVo) {
		return sqlSession.update("calendar.dragAndDrop",workVo);
	}

	@Override
	public int delW(int wrk_id) {
		return sqlSession.delete("calendar.delW",wrk_id);
	}

	@Override
	public int upW(WorkVo workVo) {
		return sqlSession.update("calendar.upW",workVo);
	}
	
	@Override
	public List<WorkVo> projectWList() {
		return sqlSession.selectList("calendar.projectWList");
	}
	
	
	
	
	
	
	@Override
	public List<ProjectVo> projectList() {
		return null;
	}

}
