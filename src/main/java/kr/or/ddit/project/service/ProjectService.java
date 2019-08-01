package kr.or.ddit.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.project.dao.IProjectDao;
import kr.or.ddit.project.model.ProjectVo;

@Service
public class ProjectService implements IProjectService{
	
	
	@Resource(name = "projectDao")
	private IProjectDao projectDao;
	
	/**
	 * 
	 * Method 		: projectList
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @return
	 * Method 설명 	: 프로젝트 전체 리스트 조회
	 */
	@Override
	public List<ProjectVo> projectList(String user_email) {
		return projectDao.projectList(user_email);
	}

	/**
	 * 
	 * Method 		: insertProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 등록
	 */
	@Override
	public int insertProject(ProjectVo projectVo) {
		return projectDao.insertProject(projectVo);
	}

	/**
	 * 
	 * Method 		: updqteProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 수정
	 */
	@Override
	public int updateProject(ProjectVo projectVo) {
		return projectDao.updateProject(projectVo);
	}

	/**
	 * 
	 * Method 		: deleteProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-22 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 삭제(프로젝트 삭제여부 업데이트)
	 */
	@Override
	public int deleteProject(ProjectVo projectVo) {
		return projectDao.deleteProject(projectVo);
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
		return projectDao.getProject(prj_id);
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
	public Map<String, Object> projectStatusList(Map<String, Object> map) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("projectList", projectDao.projectStatusList(map));
		
		return resultMap;
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
	public Map<String, Object> projectSearch(Map<String, Object> map) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("projectList", projectDao.projectSearch(map));
		
		return resultMap;
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
		return projectDao.updateAllProject(projectVo);
	}

}
