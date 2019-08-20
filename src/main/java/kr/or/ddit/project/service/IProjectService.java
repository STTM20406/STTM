package kr.or.ddit.project.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;

public interface IProjectService {
	
	/**
	 * 
	 * Method 		: projectList
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 프로젝트 전체 리스트 조회
	 */
	List<ProjectVo> projectList(String user_email);
	
	
	/**
	 * 
	 * Method 		: insertProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 등록
	 */
	int insertProject(ProjectVo projectVo);
	
	
	/**
	 * 
	 * Method 		: updqteProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 수정
	 */
	int updateProject(ProjectVo projectVo);
	
	
	/**
	 * 
	 * Method 		: deleteProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 삭제(프로젝트 삭제여부 업데이트)
	 */
	int deleteProject(ProjectVo projectVo);
	
	
	/**
	 * 
	 * Method 		: getProject
	 * 작성자 			: 박서경 
	 * 변경이력 		: 2019-07-23 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 아이디에 맞는 프로젝트 정보 조회
	 */
	ProjectVo getProject(int prj_id);
	
	
	/**
	 * 
	 * Method 		: projectStatusList
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-25 최초 생성
	 * @param prj_st
	 * @return
	 * Method 설명 	: 프로젝트 상태에 따라 프로젝트 리스트 조회
	 */
	Map<String, Object> projectStatusList(Map<String, Object> map);
	
	
	/**
	 * 
	 * Method 		: projectSearch
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-29 최초 생성
	 * @param map
	 * @return
	 * Method 설명 	: 프로젝트 검색(프로젝트명으로 검색)
	 */
	Map<String, Object> projectSearch(Map<String, Object> map);
	
	
	/**
	 * 
	 * Method 		: updateAllProject
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-31 최초 생성
	 * @param proejctVo
	 * @return
	 * Method 설명 	: 프로젝트 설정 전체 업데이트
	 */
	int updateAllProject(ProjectVo projectVo);
	
	/**
	 * 
	 * Method 		: maxProjectId
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-10 최초 생성
	 * @return
	 * Method 설명 	: 프로젝트 id max 값
	 */
	int maxProjectId();
	
	/**
	 * 
	 * Method 		: searchWorkList
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 업무리스트명으로 프로젝트 검색
	 */
	List<ProjectVo> searchWorkList(Map<String, Object> map);
	
	/**
	 * 
	 * Method 		: searchWorkNm
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-19 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 업무명으로 프로젝트 검색
	 */
	List<ProjectVo> searchWorkNm(WorkVo vo);
	
	/**
	 * 
	 * Method 		: searchProjectMem
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-08-20 최초 생성
	 * @param vo
	 * @return
	 * Method 설명 	: 프로젝트 멤버명으로 프로젝트 검색
	 */
	List<ProjectVo> searchProjectMem(UserVo vo);
}
