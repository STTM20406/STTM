package kr.or.ddit.users.dao;

import java.util.List;

import kr.or.ddit.notification_set.model.Notification_SetVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.users.model.UserVo;

public interface IUserDao {
	
	/**
	 * 
	* Method : insertUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param userVo
	* @return
	* Method 설명 : 사용자 입력
	 */
	int insertUser(UserVo userVo);
	
	/**
	 * 
	* Method : insertUserNotice
	* 작성자 : 김경호
	* 변경이력 : 2019-07-30
	* @param notificationSetVo
	* @return
	* Method 설명 : 사용자 알림 설정 
	 */
	int insertUserNotice(Notification_SetVo notificationSetVo);
	
	/**
	 * 
	* Method : userList
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @return
	* Method 설명 : 전체 사용자 조회
	 */
	List<UserVo> userList();
	
	/**
	 * 
	* Method : getUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param user_email
	* @return
	* Method 설명 : 사용자 정보 조회
	 */
	UserVo getUser(String user_email);
	
	/**
	 * 
	* Method : userPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param pageVo
	* @return
	* Method 설명 : 사용자 페이징 리스트 조회
	 */
	List<UserVo> userPagingList(PageVo pageVo);
	
	/**
	 * 
	* Method : userCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @return
	* Method 설명 : 사용자 전체수 조회
	 */
	int userCnt();
	
	/**
	 * 
	* Method : updateUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param userVo
	* @return
	* Method 설명 : 사용자 정보 업데이트
	 */
	int updateUser(UserVo userVo);
	
	/**
	 * 
	* Method : updateUserPass
	* 작성자 : 김경호
	* 변경이력 : 2019-07-27
	* @param userVo
	* @return
	* Method 설명 : 사용자 비밀번호 업데이트
	 */
	int updateUserPass(UserVo userVo);
	
	/**
	 * 
	* Method : updateUserStatus
	* 작성자 : 김경호
	* 변경이력 : 2019-07-29
	* @param userVo
	* @return
	* Method 설명 : 사용자 상태 업데이트(휴면 계정 설정)
	 */
	int updateUserStatus(UserVo userVo);
	
	/**
	 * 
	* Method : updateUserProfile
	* 작성자 : 김경호
	* 변경이력 : 2019-07-31
	* @param userVo
	* @return
	* Method 설명 : 사용자 프로필 업데이트
	 */
	int updateUserProfile(UserVo userVo);
	
	/**
	 * 
	* Method : deleteUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param user_email
	* @return
	* Method 설명 : 사용자 삭제
	 */
	int deleteUser(String user_email);
	
	/**
	 * 
	* Method : userListForPassEncrypt
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @return
	* Method 설명 : 비밀번호 암호화 적용대상 사용자 전체 조회
	 */
	List<UserVo> userListForPassEncrypt();
	
	/**
	 * 
	* Method : updateUserEncryptPass
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param userVo
	* @return
	* Method 설명 : 사용자 비밀번호 암호화 적용
	 */
	int updateUserEncryptPass(UserVo userVo);
}
