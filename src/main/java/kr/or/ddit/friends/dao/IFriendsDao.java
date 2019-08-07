package kr.or.ddit.friends.dao;

import java.util.List;
import java.util.Map;

import kr.or.ddit.friends.model.ChatFriendsVo;
import kr.or.ddit.friends.model.FriendsVo;

public interface IFriendsDao {
	
	/**
	 * 
	 * Method 		: friendList
	 * 작성자 			: 유다연
	 * 변경이력 		: 2019-07-28 최초 생성
	 * @param user_email
	 * @return
	 * Method 설명 	: 친구 리스트
	 */
	public List<ChatFriendsVo> friendList(String user_email);
		
	
	/**
	 * 
	* Method : friendPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param searcj
	* @return
	* Method 설명 : 일반 사용자가 자신의 친구 목록을  자신의 이메일로 페이징 리스트 조회
	 */
	List<FriendsVo> friendPagingList(Map<String, Object> user_email);
	
	/**
	 * 
	* Method : friendPagingCnt
	* 작성자 : 김경호
	* 변경이력 : 2019-08-07
	* @param search
	* @return
	* Method 설명 : 일반 사용자가 자신의 친구 목록을 자신의 이메일로 검색한 갯수
	 */
	int friendPagingCnt(Map<String, Object> user_email);
	
}
