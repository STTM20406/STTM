package kr.or.ddit.project_mem.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.paging.model.PageVo;
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
	public List<Project_MemVo> getMyProjectMemList(String user_email) {
		return sqlSession.selectList("project.getMyProjectMemList",user_email);
	}
	
	@Override
	public List<Project_MemVo> getMyProjectMemList(int prj_id) {
		return sqlSession.selectList("project.getMyProjectMemList",prj_id);
	}
	
	/**
	 * 
	* Method : projectMemPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-06
	* @param prj_id
	* @return
	* Method 설명 : 사용자가 멤버 탭에서 자신과 같은 프로젝트를 진행하는 멤버의 리스트를 페이징으로 조회한다.
	 */
	@Override
	public List<Project_MemVo> projectMemPagingList(Map<String, Object> map) {
		return sqlSession.selectList("project.projectMemPagingList", map);
	}
	
	/**
	 * 
	* Method : projectMemCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-08-06
	* @return
	* Method 설명 : 프로젝트 멤버 전체수 조회
	 */
	@Override
	public int projectMemCnt(Map<String, Object> map) {
		return sqlSession.selectOne("project.projectMemCnt",map);
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


	/**
	 * 
	 * Method 			: getProjectMemInfo
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-07 최초 생성
	 * @param projectMemVo
	 * @return
	 * Method 설명 	: 현재 접속한 사용자의 프로젝트 멤버 정보 조회
	 */
	@Override
	public Project_MemVo getProjectMemInfo(Project_MemVo projectMemVo) {
		return sqlSession.selectOne("project.getProjectMemInfo", projectMemVo);
	}


	@Override
	public List<Project_MemVo> headerChatFriendList(String user_email) {
		return sqlSession.selectList("project.headerChatFriendList",user_email);
	}

	/**
	 * 
	* Method : prjMemListForInactive
	* 작성자 : 김경호
	* 변경이력 : 2019-08-21
	* @param map
	* @return
	* Method 설명 : 휴면 계정을 하기 위해 session에서 가져온 user_email에서 나의 프로젝트 멤버를 리스트로 조회
	 */
	@Override
	public List<Project_MemVo> prjMemListForInactive(Map<String, Object> map) {
		return sqlSession.selectList("project.prjMemListForInactive",map);
	}

	/**
	 * 
	* Method : prjMemListForInactiveCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-08-21
	* @param map
	* @return
	* Method 설명 : 휴면 계정을 하기 위해 session에서 가져온 user_email에서 나의 프로젝트 멤버를 리스트로 조회한 갯수
	 */
	@Override
	public int prjMemListForInactiveCnt(Map<String, Object> map) {
		return sqlSession.selectOne("project.prjMemListForInactiveCnt",map);
	}

}
