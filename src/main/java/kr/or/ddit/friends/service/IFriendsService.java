package kr.or.ddit.friends.service;

import java.util.List;

import kr.or.ddit.friends.model.ChatFriendsVo;
import kr.or.ddit.friends.model.FriendsVo;

public interface IFriendsService {

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
}
