package kr.or.ddit.project_mem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.or.ddit.friends.model.FriendsVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project_mem.dao.IProject_MemDao;
import kr.or.ddit.project_mem.model.Project_MemVo;

@Service
public class Project_MemService implements IProject_MemService{
	
	@Resource(name = "project_MemDao")
	private IProject_MemDao projectMemDao;
	
	/**
	 * 
	 * Method 			: projectMemList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-03 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 프로젝트 멤버 리스트 조회
	 */
	@Override
	public List<Project_MemVo> projectMemList(Project_MemVo projectMemVo) {
		return projectMemDao.projectMemList(projectMemVo);
	}

	/**
	 * 
	 * Method 			: insertProjectMem
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-07-21 최초 생성
	 * @return
	 * Method 설명 	: 프로젝트 멤버 등록(초대)
	 */
	@Override
	public int insertProjectMem(Project_MemVo projectMemVo) {
		return projectMemDao.insertProjectMem(projectMemVo);
	}

	/**
	 * 
	 * Method 		: updatePjojectMem
	 * 작성자 		: 박서경 
	 * 변경이력 		: 2019-08-03 최초 생성
	 * @param projectMemVo
	 * @return
	 * Method 설명 	:
	 */
	@Override
	public int updateProjectMem(Project_MemVo projectMemVo) {
		return projectMemDao.updateProjectMem(projectMemVo);
	}

	
	/**
	 * 
	* Method : getMyProjectMemList
	* 작성자 : 김경호
	* 변경이력 : 2019-07-31
	* @param prj_id
	* @return
	* Method 설명 : 휴면 계정으로 전환하기 위하여 나의 프로젝트 멤버를 조회한다
	 */
	@Override
	public List<Project_MemVo> getMyProjectMemList(String user_email) {
		return projectMemDao.getMyProjectMemList(user_email);
	}
	
	@Override
	public List<Project_MemVo> getMyProjectMemList(int prj_id) {
		return projectMemDao.getMyProjectMemList(prj_id);
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
	public Map<String, Object> projectMemPagingList(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("projectMemList", projectMemDao.projectMemPagingList(map));
		
		int projectMemCnt = projectMemDao.projectMemCnt(map);
		
		int paginationSize = (int)Math.ceil((double)projectMemCnt/(int)map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}
	
	/**
	 * 
	 * Method 			: projectAllMemList
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-05 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 프로젝트 멤버 추가를 위한 프로젝트 멤버 리스트 조회
	 */
	@Override
	public List<Project_MemVo> projectAllMemList(String user_email) {
		return projectMemDao.projectAllMemList(user_email);
	}

	
	/**
	 * 
	 * Method 			: deleteProjectMem
	 * 작성자 				: 박서경 
	 * 변경이력 			: 2019-08-06 최초 생성
	 * @param projectVo
	 * @return
	 * Method 설명 	: 프로젝트 멤버 삭제
	 */
	@Override
	public int deleteProjectMem(Project_MemVo projectMemVo) {
		return projectMemDao.deleteProjectMem(projectMemVo);
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
		return projectMemDao.getProjectMemInfo(projectMemVo);
	}

	@Override
	public List<Project_MemVo> headerChatFriendList(String user_email) {
		return projectMemDao.headerChatFriendList(user_email);
	}
	
	/**
	 * 
	* Method : prjMemListForInactive
	* 작성자 : 김경호
	* 변경이력 : 2019-08-21
	* @param user_email
	* @return
	* Method 설명 : 휴면 계정을 하기 위해 session에서 가져온 user_email에서 나의 프로젝트 멤버를 리스트로 조회
	 */
	@Override
	public Map<String, Object> prjMemListForInactive(Map<String, Object> map) {
		
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		List<Project_MemVo> inactiveMemList = projectMemDao.prjMemListForInactive(map);
//		
//		int pageSize = (int) map.get("pageSize");
//		int InactiveMemCnt = projectMemDao.prjMemListForInactiveCnt(map);
//		int paginationSize = (int) Math.ceil((double)InactiveMemCnt/(int)map.get("pageSize"));
//
//		resultMap.put("inactiveMemList", inactiveMemList);
//		resultMap.put("paginationSize", paginationSize);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("inactiveMemList", projectMemDao.prjMemListForInactive(map));
		
		int projectMemCnt = projectMemDao.projectMemCnt(map);
		
		int paginationSize = (int)Math.ceil((double)projectMemCnt/(int)map.get("pageSize"));
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}
	
	/**
	 * 
	 * Method : updateInactiveMember
	 * 작성자 : 김경호
	 * 변경이력 : 2019-08-22
	 * @param projectMemVo
	 * @return
	 * Method 설명 : 휴면 계정 설정하기 위해 프로젝트 소유자의 멤버 레벨(String prj_mem_lv)를 'LV0'으로 업데이트 시키고
	 * 			       프로젝트 소유 유무(String prj_own_fl)를 'N'로 업데이트 시켜 준다. 
	 */
	@Override
	public int updateInactiveMember(Project_MemVo projectMemVo) {
		return projectMemDao.updateInactiveMember(projectMemVo);
	}
	
	/**
	 * 
	 * Method : updateTransferOwnership
	 * 작성자 : 김경호
	 * 변경이력 : 2019-08-22
	 * @param projectMemVo
	 * @return
	 * Method 설명 : 휴면 계정 설정하기 위해 프로젝트 소유자를 넘겨줄 자의 멤버 레벨(String prj_mem_lv)를 'LV1'으로 업데이트 시키고
	 * 			       프로젝트 소유 유무(String prj_own_fl)를 'Y'로 업데이트 시켜 준다. 
	 */
	@Override
	public int updateTransferOwnership(Project_MemVo projectMemVo) {
		return projectMemDao.updateTransferOwnership(projectMemVo);
	}
	
	/**
	 * 
	* Method : projectMemList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-23
	* @param prj_id
	* @return
	* Method 설명 : 멤버탭에서 프로젝트 이름을 클릭하여 프로젝트 번호를 받아오고
	* 			     프로젝트 번호로 나의 프로젝트 멤버를 조회하여 페이징 리스트로 보여준다
	 */
	@Override
	public Map<String, Object> projectMemListById(Map<String, Object> prj_id) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("projectMemList", projectMemDao.projectMemListById(prj_id));
		
		int projectMemListCnt = projectMemDao.projectMemListByIdCnt(prj_id);

		int paginationSize = (int) Math.ceil((double)projectMemListCnt/(int)prj_id.get("pageSize"));
		
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}

	/**
	 * 
	* Method : projectMemYNList
	* 작성자 : melong2
	* 변경이력 :
	* @param projectMemVo
	* @return
	* Method 설명 :
	 */
	@Override
	public List<Project_MemVo> projectMemYNList(int prj_id) {
		return projectMemDao.projectMemYNList(prj_id);
	}
	
	/**
	 * 
	* Method : getprjListForInactive
	* 작성자 : 김경호
	* 변경이력 : 2019-08-29
	* @param user_email
	* @return
	* Method 설명 : 휴면 계정으로 전환 하기 위하여 prj_own_fl = 'Y' 인 리스트를 가져온다
	* 			     리스트가 null이면 휴면계정 버튼이 보이고 휴면 계정 전환 할수 있다.
	 */
	@Override
	public List<Project_MemVo> getprjListForInactive(String user_email) {
		return projectMemDao.getprjListForInactive(user_email);
	}
	
	/**
	 * 
	* Method : getFriendsBtn
	* 작성자 : 김경호
	* 변경이력 : 2019-08-29
	* @param prj_id
	* @return
	* Method 설명 : 프로젝트 멤버 리스트에서 친구가 아닌 사람만 친구 요청 버튼 생기도록 리스트를 가져옴
	 */
	@Override
	public List<Project_MemVo> getFriendsBtn(int prj_id) {
		return projectMemDao.getFriendsBtn(prj_id);
	}
	
}
