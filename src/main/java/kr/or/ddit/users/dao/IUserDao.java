package kr.or.ddit.users.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.notification_set.model.Notification_SetVo;
import kr.or.ddit.paging.model.PageVo;
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
	* Method : userSearchByEmail
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param search
	* @return
	* Method 설명 : 관리자가 회원 리스트를 회원의 이메일로 검색 후 페이징 리스트 조회
	 */
	List<UserVo> userSearchByEmail(Map<String, Object> search);
	
	/**
	 * 
	* Method : userSearchByEmailCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param search
	* @return
	* Method 설명 : 관리자가 회원 리스트를 회원의 이메일로 검색한 갯수
	 */
	int userSearchByEmailCnt(Map<String, Object> search);
	
	/**
	 * 
	* Method : userSearchByName
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param search
	* @return
	* Method 설명 : 관리자가 회원 리스트를 회원의 이름으로 검색 후 페이징 리스트 조회
	 */
	List<UserVo> userSearchByName(Map<String, Object> search);
	
	/**
	 * 
	* Method : userSearchByNameCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param search
	* @return
	* Method 설명 : 관리자가 회원 리스트를 회원의 이름으로 검색한 갯수
	 */
	int userSearchByNameCnt(Map<String, Object> search);
	
	/**
	 * 
	* Method : userSearchByHp
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param search
	* @return
	* Method 설명 : 관리자가 회원 리스트를 회원의 전화번호로 검색 후 페이징 리스트 조회
	 */
	List<UserVo> userSearchByHp(Map<String, Object> search);
	
	/**
	 * 
	* Method : userSearchByHpCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-08-05
	* @param search
	* @return
	* Method 설명 : 관리자가 회원 리스트를 회원의 전화번호로 검색한 갯수
	 */
	int userSearchByHpCnt(Map<String, Object> search);
	
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
	* Method : updateUserAdm
	* 작성자 : 김경호
	* 변경이력 : 2019-08-02
	* @param userVo
	* @return
	* Method 설명 : 관리자가 사용자 정보 업데이트
	 */
	int updateUserAdm(UserVo userVo);
	
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
	
	/**
	 * Method 		: plusCount
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-23 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 사용자 새로운 알림추가시 카운트 개수 1씩 증가
	 */
	int plusCount(String user_email);
	
	/**
	 * Method 		: resetCount
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-08-23 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 사용자가 알림목록을 읽었을 경우 0으로 초기화
	 */
	int resetCount(String user_email);
	
	
}
