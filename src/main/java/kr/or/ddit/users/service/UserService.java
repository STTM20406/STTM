package kr.or.ddit.users.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.encrypt.encrypt.kisa.sha256.KISA_SHA256;
import kr.or.ddit.notification_set.model.Notification_SetVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.users.dao.IUserDao;
import kr.or.ddit.users.model.UserVo;

@Service
public class UserService implements IUserService{
	
	@Resource(name = "userDao")
	private IUserDao userDao;
	
	/**
	 * 
	* Method : insertUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param userVo
	* @return
	* Method 설명 : 사용자 입력
	 */
	@Override
	public int insertUser(UserVo userVo) {
		int insertCnt = 0;
		insertCnt += userDao.insertUser(userVo);
		return insertCnt;
	}
	
	/**
	 * 
	* Method : insertUserNotice
	* 작성자 : 김경호
	* 변경이력 : 2019-07-30
	* @param notificationSetVo
	* @return
	* Method 설명 : 사용자 알림 설정 
	 */
	@Override
	public int insertUserNotice(Notification_SetVo notificationSetVo) {
		int insertCnt = 0;
		insertCnt += userDao.insertUserNotice(notificationSetVo);
		return insertCnt;
	}
	
	/**
	 * 
	* Method : userList
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @return
	* Method 설명 : 전체 사용자 조회
	 */
	@Override
	public List<UserVo> userList() {
		return userDao.userList();
	}
	
	/**
	 * 
	* Method : getUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param user_email
	* @return
	* Method 설명 : 사용자 정보 조회
	 */
	@Override
	public UserVo getUser(String user_email) {
		return userDao.getUser(user_email);
	}
	
	/**
	 * 
	* Method : getMyProjectMemList
	* 작성자 : 김경호
	* 변경이력 : 2019-07-31
	* @param prj_id
	* @return
	* Method 설명 : 휴면 계정으로 전환하기 위하여 나의 프로젝트 멤버 
	* 			     리스트에서 프로젝트 소유권이 'N'인 멤버를 조회한다
	 */
	@Override
	public List<Project_MemVo> getMyProjectMemList(int prj_id) {
		return userDao.getMyProjectMemList(prj_id);
	}
	
	/**
	 * 
	* Method : userPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param pageVo
	* @return
	* Method 설명 : 사용자 페이징 리스트 조회
	 */
	@Override
	public Map<String, Object> userPagingList(PageVo pageVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("usreList", userDao.userPagingList(pageVo));
		
		int usersCnt = userDao.userCnt();
		
		int paginationSize = (int)Math.ceil((double)usersCnt/pageVo.getPageSize());
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}
	
	/**
	 * 
	* Method : updateUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param userVo
	* @return
	* Method 설명 : 사용자 정보 업데이트
	 */
	@Override
	public int updateUser(UserVo userVo) {
		return userDao.updateUser(userVo);
	}
	
	/**
	 * 
	* Method : updateUserPass
	* 작성자 : 김경호
	* 변경이력 : 2019-07-27
	* @param user_pass
	* @return
	* Method 설명 : 사용자 비밀번호 업데이트
	 */
	@Override
	public int updateUserPass(UserVo userVo) {
		return userDao.updateUserPass(userVo);
	}

	/**
	 * 
	* Method : updateUserStatus
	* 작성자 : 김경호
	* 변경이력 : 2019-07-29
	* @param userVo
	* @return
	* Method 설명 : 사용자 상태 업데이트(휴면 계정 설정)
	 */
	@Override
	public int updateUserStatus(UserVo userVo) {
		return userDao.updateUserStatus(userVo);
	}
	
	/**
	 * 
	* Method : updateUserProfile
	* 작성자 : 김경호
	* 변경이력 : 2019-07-31
	* @param userVo
	* @return
	* Method 설명 : 사용자 프로필 업데이트
	 */
	@Override
	public int updateUserProfile(UserVo userVo) {
		return userDao.updateUserProfile(userVo);
	}
	
	/**
	 * 
	* Method : deleteUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param user_email
	* @return
	* Method 설명 : 사용자 삭제
	 */
	@Override
	public int deleteUser(String user_email) {
		return userDao.deleteUser(user_email);
	}
	
	/**
	 * 
	* Method : encryptPassAllUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @return
	* Method 설명 : 사용자 비밀번호 암호화 일괄 적용 배치
	 */
	@Override
	public int encryptPassAllUser() {
		if(1==1) {
			return 0;
		}
		
		List<UserVo> userList = userDao.userListForPassEncrypt();
		
		int updateCntSum = 0;
		for(UserVo userVo:userList) {
			String encryptPass = KISA_SHA256.encrypt(userVo.getUser_pass());
			userVo.setUser_pass(encryptPass);
			
			int updateCnt = userDao.updateUserEncryptPass(userVo);
			updateCntSum += updateCnt;
		}
		return updateCntSum;
	}
		
}
