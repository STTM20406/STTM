package kr.or.ddit.project.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

@Repository
public class ProjectDao implements IProjectDao{
	
	@Resource(name = "sqlSession")
	private SqlSessionTemplate sqlSession;

	/**
	 * 
	 * Method 		: projectList
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 프로젝트 전체 리스트 조회
	 */
	@Override
	public List<ProjectVo> projectList(String user_email) {
		return sqlSession.selectList("project.projectList", user_email);
	}

	/**
	 * 
	 * Method 		: insertProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 등록
	 */
	@Override
	public int insertProject(ProjectVo projectVo) {
		return sqlSession.insert("project.insertProject",projectVo);
	}

	/**
	 * 
	 * Method 		: updqteProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 수정
	 */
	@Override
	public int updateProject(ProjectVo projectVo) {
		return sqlSession.update("project.updateProject", projectVo);
	}

	
	/**
	 * 
	 * Method 		: deleteProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 삭제(삭제여부 업데이트)
	 */
	@Override
	public int deleteProject(ProjectVo projectVo) {
		return sqlSession.delete("project.deleteProject", projectVo);
	}

	
	/**
	 * 
	 * Method 		: getProject
	 * 작성자 			: 박서경 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 아이디에 맞는 프로젝트 정보 조회
	 */
	@Override
	public ProjectVo getProject(int prj_id) {
		return sqlSession.selectOne("project.getProject", prj_id);
	}


	/**
	 * 
	 * Method 		: projectStatusList
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param prj_st
	 * @return
	 * Method 설명 	: 프로젝트 상태에 따라 프로젝트 리스트 조회
	 */
	@Override
	public List<ProjectVo> projectStatusList(Map<String, Object> map) {
		return sqlSession.selectList("project.projectStatusList", map);
	}

	
	/**
	 * 
	 * Method 		: projectSearch
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-29 최초 생성
	 * @param map
	 * @return
	 * Method 설명 	: 프로젝트 검색(프로젝트명으로 검색)
	 */
	@Override
	public List<ProjectVo> projectSearch(Map<String, Object> map) {
		return sqlSession.selectList("project.projectSearch", map);
	}

	/**
	 * 
	 * Method 		: updateAllProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @param proejctVo
	 * @return
	 * Method 설명 	: 프로젝트 설정 전체 업데이트
	 */
	@Override
	public int updateAllProject(ProjectVo projectVo) {
		return sqlSession.update("project.updateAllProject", projectVo);
	}

	@Override
	public int maxProjectId() {
		return sqlSession.selectOne("project.maxProjectId");
	}

	@Override
	public List<ProjectVo> searchWorkList(Map<String, Object> map) {
		return sqlSession.selectList("project.searchWorkList",map);
	}

	@Override
	public List<ProjectVo> searchWorkNm(WorkVo vo) {
		return sqlSession.selectList("project.searchWorkNm",vo);
	}

	@Override
	public List<ProjectVo> searchProjectMem(UserVo vo) {
		return sqlSession.selectList("project.searchProjectMem",vo);
	}
	
	@Override
	public ProjectVo getPrjByWrk(int wrk_id) {
		return sqlSession.selectOne("project.getPrjByWrk", wrk_id);
	}

	@Override
	public List<Project_MemVo> searchName(Project_MemVo project_MemVo) {
		return sqlSession.selectList("project.searchName",project_MemVo);
	}

	@Override
	public List<Project_MemVo> searchPL(Project_MemVo project_MemVo) {
		return sqlSession.selectList("project.searchPL",project_MemVo);
	}
}
