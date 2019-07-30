package kr.or.ddit.users.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.notification_set.model.Notification_SetVo;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.users.model.UserVo;

public interface IUserService {
	
	/**
	 * 
	* Method : insertUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @param userVo
	* @return
	* Method 설명 : 사용자 등록
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
	Map<String, Object> userPagingList(PageVo pageVo);
	
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
	* @param user_pass
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
	* Method : encryptPassAllUser
	* 작성자 : 김경호
	* 변경이력 : 2019-07-19
	* @return
	* Method 설명 : 사용자 비밀번호 암호화 일괄 적용 배치
	 */
	int encryptPassAllUser();

	
}
