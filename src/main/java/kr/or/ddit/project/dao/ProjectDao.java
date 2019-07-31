package kr.or.ddit.project.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.or.ddit.project.model.ProjectVo;

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
	public int updqteProject(ProjectVo projectVo) {
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

	@Override
	public int updateAllProject(ProjectVo proejctVo) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
