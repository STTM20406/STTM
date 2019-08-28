package kr.or.ddit.users.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.encrypt.encrypt.kisa.sha256.KISA_SHA256;
import kr.or.ddit.notification_set.model.Notification_SetVo;
import kr.or.ddit.paging.model.PageVo;
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
		resultMap.put("userList", userDao.userPagingList(pageVo));
		
		int usersCnt = userDao.userCnt();
		
		int paginationSize = (int)Math.ceil((double)usersCnt/pageVo.getPageSize());
		resultMap.put("paginationSize", paginationSize);
		return resultMap;
	}

	// ***********************************************************************************
	
	/**
	 * 
	* Method : userSearchByEmail
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param search
	* @return
	* Method 설명 : 관리자가 회원 리스트를 회원의 이메일로 검색 후 페이징 리스트 조회
	 */
	@Override
	public Map<String, Object> userSearchByEmail(Map<String, Object> search) {
		Map<String , Object> resultMap = new HashMap<String, Object>();
		List<UserVo> admSearchEmailList = userDao.userSearchByEmail(search);
		
		int pageSize = (int) search.get("pageSize");
		int searchCnt = userDao.userSearchByEmailCnt(search);
		
		int paginationSize = (int) Math.ceil((double)searchCnt/pageSize);
		
		resultMap.put("admSearchEmailList", admSearchEmailList);
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}
	
	/**
	 * 
	* Method : userSearchByName
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param search
	* @return
	* Method 설명 : 관리자가 회원 리스트를 회원의 이름으로 검색 후 페이징 리스트 조회
	 */
	@Override
	public Map<String, Object> userSearchByName(Map<String, Object> search) {
		Map<String , Object> resultMap = new HashMap<String, Object>();
		List<UserVo> admSearchNameList = userDao.userSearchByName(search);
		
		int pageSize = (int) search.get("pageSize");
		int searchCnt = userDao.userSearchByNameCnt(search);
		
		int paginationSize = (int) Math.ceil((double)searchCnt/pageSize);
		
		resultMap.put("admSearchNameList", admSearchNameList);
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}
	
	/**
	 * 
	* Method : userSearchByHp
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param search
	* @return
	* Method 설명 : 관리자가 회원 리스트를 회원의 전화번호로 검색 후 페이징 리스트 조회
	 */
	@Override
	public Map<String, Object> userSearchByHp(Map<String, Object> search) {
		Map<String , Object> resultMap = new HashMap<String, Object>();
		List<UserVo> admSearchHpList = userDao.userSearchByHp(search);
		
		int pageSize = (int) search.get("pageSize");
		int searchCnt = userDao.userSearchByHpCnt(search);
		
		int paginationSize = (int) Math.ceil((double)searchCnt/pageSize);
		
		resultMap.put("admSearchHpList", admSearchHpList);
		resultMap.put("paginationSize", paginationSize);
		
		return resultMap;
	}
	
	// ***********************************************************************************
	
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
	* Method : updateUserAdm
	* 작성자 : 김경호
	* 변경이력 : 2019-08-02
	* @param userVo
	* @return
	* Method 설명 : 관리자가 사용자 정보 업데이트
	 */
	@Override
	public int updateUserAdm(UserVo userVo) {
		return userDao.updateUserAdm(userVo);
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

	/**
	 * Method 		: plusCount
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-23 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 사용자 새로운 알림추가시 카운트 개수 1씩 증가
	 */
	@Override
	public int plusCount(String user_email) {
		return userDao.plusCount(user_email);
	}

	/**
	 * Method 		: resetCount
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-23 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 사용자가 알림목록을 읽었을 경우 0으로 초기화
	 */
	@Override
	public int resetCount(String user_email) {
		return userDao.resetCount(user_email);
	}

	/**
	 * Method 		: countCnt
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-28 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 사용자 알림 쌓인 개수
	 */
	@Override
	public int countCnt(String user_email) {
		return userDao.countCnt(user_email);
	}

}
