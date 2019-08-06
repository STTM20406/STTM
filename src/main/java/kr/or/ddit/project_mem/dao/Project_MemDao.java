package kr.or.ddit.project_mem.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project_mem.model.Project_MemVo;

@Repository
public class Project_MemDao implements IProject_MemDao{
	
	@Resource(name = "sqlSession")
	private SqlSessionTemplate sqlSession;
	
	
	/**
	 * 
	 * Method 		: projectMemList
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 프로젝트 멤버 리스트 조회
	 */
	@Override
	public List<Project_MemVo> projectMemList(Project_MemVo projectMemVo) {
		return sqlSession.selectList("project.projectMemList", projectMemVo);
	}

	
	/**
	 * 
	 * Method 		: insertProjectMem
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 프로젝트 멤버 등록(초대)
	 */
	@Override
	public int insertProjectMem(Project_MemVo projectMemVo) {
		return sqlSession.insert("project.insertProjectMem", projectMemVo);
	}

	
	/**
	 * 
	 * Method 		: updatePjojectMem
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-07-21 최초 생성
	 * @param projectMemVo
	 * @return
	 * Method 설명 	: 프로젝트 멤버 업데이트(멤버레벨, 프로젝트 소유 유무, 프로젝트 멤버 닉네임)
	 */
	@Override
	public int updateProjectMem(Project_MemVo projectMemVo) {
		return sqlSession.update("project.updateProjectMem", projectMemVo);
	}

	/**
	 * 
	* Method : getMyProjectMemList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-01
	* @param prj_id
	* @return
	* Method 설명 : 휴면 계정으로 전환하기 위하여 나의 프로젝트 멤버를 조회한다
	 */
	@Override
	public List<Project_MemVo> getMyProjectMemList(int prj_id) {
		return sqlSession.selectList("project.getMyProjectMemList",prj_id);
	}


	/**
	 * 
	 * Method 		: projectAllMemList
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-08-05 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 프로젝트 멤버 추가를 위한 프로젝트 멤버 리스트 조회
	 */
	@Override
	public List<Project_MemVo> projectAllMemList(String user_email) {
		return sqlSession.selectList("project.projectAllMemList", user_email);
	}


	/**
	 * 
	 * Method 		: deleteProjectMem
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-08-06 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 멤버 삭제
	 */
	@Override
	public int deleteProjectMem(Project_MemVo projectMemVo) {
		return sqlSession.delete("project.deleteProjectMem", projectMemVo);
	}

}
